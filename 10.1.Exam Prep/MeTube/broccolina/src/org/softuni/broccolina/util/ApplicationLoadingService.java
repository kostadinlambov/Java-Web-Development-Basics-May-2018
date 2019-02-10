package org.softuni.broccolina.util;

import org.softuni.broccolina.solet.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

//FROM THE PREVIOUS DEVELOPER
//THERE IS A BUG IN THIS FILE
//I CANNOT LOCATE IT THOUGH
//IT HAS SOMETHING TO DO WITH THE LOADING OF THE SOLETS
public class ApplicationLoadingService {
    private String applicationFolderPath;

    private Map<String, HttpSolet> loadedApplications;

    private JarFileUnzipService jarFileUnzipService;

    private List<URL> libraryClassUrls;

    public ApplicationLoadingService() {
        this.jarFileUnzipService = new JarFileUnzipService();
    }

    private boolean isJarFile(File file) {
        return file.isFile() && file.getName().endsWith(".jar");
    }

    private void loadSolet(Class soletClass, String applicationName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (soletClass == null
                && (soletClass.getSuperclass() == null)
                && (!soletClass
                .getSuperclass()
                .getName()
                .equals(BaseHttpSolet.class.getName()))) {
            return;
        }

        Object soletObject = soletClass
                .getDeclaredConstructor()
                .newInstance();

        Object soletAnnotation = Arrays.stream(soletClass
                .getAnnotations())
                .filter(x -> x.annotationType()
                        .getSimpleName()
                        .equals(WebSolet.class.getSimpleName()))
                .findFirst()
                .orElse(null);

        String soletRoute = soletAnnotation
                .getClass()
                .getMethod("route")
                .invoke(soletAnnotation)
                .toString();

        if (applicationName.equals("ROOT")) {
            soletRoute = "/" + applicationName + soletRoute;
        }
//
        //DO NOT BREAK THIS
        HttpSolet httpSoletProxy = (HttpSolet) Proxy.newProxyInstance(
                HttpSolet.class.getClassLoader(),
                new Class[]{HttpSolet.class},
                (proxy, method, args) -> {
                    Method extractedMethod = Arrays.stream(soletClass.getMethods())
                            .filter(x -> x.getName().equals(method.getName()))
                            .findFirst()
                            .orElse(null);

                    if(extractedMethod.getName().equals("service")) {
                        Class<?>[] requestedParameters = extractedMethod.getParameterTypes();

                        Object proxyRequest = Proxy.newProxyInstance(
                                requestedParameters[0].getClassLoader(),
                                new Class[]{requestedParameters[0]},
                                (requestProxy, requestMethod, requestArgs) -> {
                                    Method extractedRequestMethod = Arrays.stream(args[0]
                                            .getClass()
                                            .getMethods())
                                            .filter(x -> x.getName().equals(requestMethod.getName()))
                                            .findFirst()
                                            .orElse(null);

                                    return extractedRequestMethod.invoke(args[0], requestArgs);
                                }
                        );

                        Object proxyResponse = Proxy.newProxyInstance(
                                requestedParameters[1].getClassLoader(),
                                new Class[]{requestedParameters[1]},
                                (responseProxy, responseMethod, responseArgs) -> {
                                    Method extractedResponseMethod = Arrays.stream(args[1]
                                            .getClass()
                                            .getMethods())
                                            .filter(x -> x.getName().equals(responseMethod.getName()))
                                            .findFirst()
                                            .orElse(null);

                                    return extractedResponseMethod.invoke(args[1], responseArgs);
                                }
                        );

                        return extractedMethod.invoke(soletObject, proxyRequest, proxyResponse);
                    } else if(extractedMethod.getName().equals("init")) {
                        SoletConfig soletConfigObj = new SoletConfigImpl();

                        soletConfigObj.setAttribute("application-folder", this.applicationFolderPath);

                        return extractedMethod.invoke(soletObject, soletConfigObj);
                    }

                    return extractedMethod.invoke(soletObject);
                }
        );

        httpSoletProxy.init(null);

        this.loadedApplications.put(soletRoute, httpSoletProxy);
    }

    private void loadClass(File currentFile, URLClassLoader classLoader, String packageName, String applicationName) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (currentFile.isDirectory()) {
            for (File childFile : currentFile.listFiles()) {
                this.loadClass(childFile, classLoader, (packageName
                        + currentFile.getName()
                        + "."), applicationName);
            }
        } else {
            if (!currentFile.getName().endsWith(".class")) return;

            String className = (packageName.replace("classes.", "")) + currentFile
                    .getName()
                    .replace(".class", "")
                    .replace("/", ".");

            Class currentClassFile = classLoader.loadClass(className);

            this.loadSolet(currentClassFile, applicationName);
        }
    }

    private void loadApplicationClasses(String classesRootFolderPath, String applicationName) throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        File classesRootDirectory = new File(classesRootFolderPath);

        if (!classesRootDirectory.exists() || !classesRootDirectory.isDirectory()) {
            return;
        }

        this.libraryClassUrls.add(new URL("file:/" + classesRootDirectory.getCanonicalPath() + File.separator));

        URL[] urls = this.libraryClassUrls.toArray(new URL[this.libraryClassUrls.size()]);

        URLClassLoader ucl = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());

        Thread.currentThread().setContextClassLoader(ucl);

        this.loadClass(classesRootDirectory, ucl, "", applicationName);
    }

    private void loadLibraryFile(JarFile library, String cannonicalPath, String applicationName) throws MalformedURLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Enumeration<JarEntry> jarFileEntries = library.entries();

        while (jarFileEntries.hasMoreElements()) {
            JarEntry currentEntry = jarFileEntries.nextElement();

            if (!currentEntry.isDirectory() && currentEntry.getRealName().endsWith(".class")) {
                URL[] urls = new URL[]{new URL("jar:file:" + cannonicalPath + "!/")};
                URLClassLoader ucl = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
                Thread.currentThread().setContextClassLoader(ucl);

                String className = currentEntry
                        .getRealName()
                        .replace(".class", "")
                        .replace("/", ".");

                Class currentClassFile = ucl.loadClass(className);

                this.loadSolet(currentClassFile, applicationName);
                this.libraryClassUrls.add(urls[0]);
            }
        }
    }

    private void loadApplicationLibraries(String librariesRootFolderPath, String applicationName) throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        this.libraryClassUrls = new ArrayList<>();

        File libraryFolder = new File(librariesRootFolderPath);

        if (!libraryFolder.exists() || !libraryFolder.isDirectory()) {
            throw new IllegalArgumentException("Library Folder does not exist or is not a folder!");
        }

        List<File> allJarFiles = Arrays.stream(libraryFolder.listFiles())
                .filter(x -> this.isJarFile(x))
                .collect(Collectors.toList());

        for (File jarFile : allJarFiles) {
            if (jarFile != null) {
                JarFile fileAsJar = new JarFile(jarFile.getCanonicalPath());
                this.loadLibraryFile(fileAsJar, jarFile.getCanonicalPath(), applicationName);
            }
        }
    }

    private void loadApplicationFromFolder(String applicationRootFolderPath, String applicationName) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.applicationFolderPath = applicationRootFolderPath;

        String classesRootFolderPath = applicationRootFolderPath + "classes" + File.separator;
        String librariesRootFolderPath = applicationRootFolderPath + "lib" + File.separator;

        this.loadApplicationLibraries(librariesRootFolderPath, applicationName);
        this.loadApplicationClasses(classesRootFolderPath, applicationName);
    }

    public Map<String, HttpSolet> loadApplications(String applicationsFolderPath) throws IOException {
        this.loadedApplications = new HashMap<>();

        try {
            File applicationsFolder = new File(applicationsFolderPath);

            if (applicationsFolder.exists() && applicationsFolder.isDirectory()) {
                List<File> allJarFiles = Arrays.stream(applicationsFolder.listFiles())
                        .filter(x -> this.isJarFile(x))
                        .collect(Collectors.toList());

                for (File applicationJarFile : allJarFiles) {
                    this.jarFileUnzipService.unzipJar(applicationJarFile);

                    this.loadApplicationFromFolder(applicationJarFile.getCanonicalPath().replace(".jar", File.separator), applicationJarFile.getName().replace(".jar", ""));
                }
            }
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return this.loadedApplications;
    }
}
