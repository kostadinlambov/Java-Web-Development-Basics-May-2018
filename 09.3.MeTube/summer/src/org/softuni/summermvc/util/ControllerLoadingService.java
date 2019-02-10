package org.softuni.summermvc.util;

import org.softuni.summermvc.api.Controller;
import org.softuni.summermvc.api.GetMapping;
import org.softuni.summermvc.api.PostMapping;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ControllerLoadingService {
    private Map<String, Map<String, ControllerActionPair>> controllerActionsByRouteAndRequestMethod;

    private void loadController(Class controllerClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (controllerClass == null
                || (Arrays.stream(controllerClass.getAnnotations())
                .noneMatch(x -> x.annotationType()
                        .getSimpleName()
                        .equals(Controller.class.getSimpleName())))) {
            return;
        }

        Object controllerObject = controllerClass
                .getDeclaredConstructor()
                .newInstance();

        Arrays.stream(controllerClass
                .getDeclaredMethods())
                .filter(x -> x.isAnnotationPresent(GetMapping.class)
                || x.isAnnotationPresent(PostMapping.class))
                .forEach(x -> {
                    if(x.isAnnotationPresent(GetMapping.class)) {
                        this.controllerActionsByRouteAndRequestMethod
                                .get("GET")
                                .put(PathFormatter.formatPath(x.getAnnotation(GetMapping.class).route()),
                                        new ControllerActionPair(controllerObject, x));
                    } else if(x.isAnnotationPresent(PostMapping.class)) {
                        this.controllerActionsByRouteAndRequestMethod
                                .get("POST")
                                .put(PathFormatter.formatPath(x.getAnnotation(PostMapping.class).route()),
                                        new ControllerActionPair(controllerObject, x));
                    }
                });
    }

    private void loadClass(File currentFile, URLClassLoader classLoader, String packageName) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (currentFile.isDirectory()) {
            for (File childFile : currentFile.listFiles()) {
                this.loadClass(childFile, classLoader,
                        packageName
                                + currentFile.getName()
                                + ".");
            }
        } else {
            if (!currentFile.getName().endsWith(".class")) return;

            String className = (packageName.replace("classes.", "")) + (currentFile
                    .getName()
                    .replace(".class", "")
                    .replace("/", "."));

            Class currentClassFile = classLoader.loadClass(className);

            this.loadController(currentClassFile);
        }
    }

    private void loadApplicationClasses(String classesRootFolderPath) throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        File classesRootDirectory = new File(classesRootFolderPath);

        if (!classesRootDirectory.exists() || !classesRootDirectory.isDirectory()) {
            return;
        }

        URL[] urls = new URL[]{new URL("file:/" + classesRootDirectory.getCanonicalPath() + File.separator)};

        URLClassLoader ucl = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());

        Thread.currentThread().setContextClassLoader(ucl);

        this.loadClass(classesRootDirectory, ucl, "");
    }

    private void initMap() {
        this.controllerActionsByRouteAndRequestMethod = new HashMap<>(){{
            put("GET", new HashMap<>());
            put("POST", new HashMap<>());
        }};
    }

    public Map<String, Map<String, ControllerActionPair>> getLoadedControllersAndActions() {
        return this.controllerActionsByRouteAndRequestMethod;
    }

    public void loadControllerActionHandlers(String applicationClassesFolderPath) throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        this.initMap();
        this.loadApplicationClasses(applicationClassesFolderPath);
    }
}
