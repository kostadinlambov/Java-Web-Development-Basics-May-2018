package org.softuni.broccolina.util;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarFileUnzipService {
    private void deleteFolder(File folder) {
        for (File file : folder.listFiles()) {
            if(file.isDirectory()) {
                deleteFolder(file);
            }

            file.delete();
        }
    }

    public void unzipJar(File jarFile) throws IOException {
        String rootCannonicalPath = jarFile.getCanonicalPath();

        JarFile fileAsJarArchive = new JarFile(rootCannonicalPath);

        Enumeration<JarEntry> jarEntries = fileAsJarArchive.entries();

        File jarFolder = new File(rootCannonicalPath.replace(".jar", ""));

        if(jarFolder.exists() && jarFolder.isDirectory()) {
            deleteFolder(jarFolder);
	    return;
        }

        jarFolder.mkdir();

        while(jarEntries.hasMoreElements()) {
            JarEntry currentEntry = jarEntries.nextElement();

            String currentEntryCannonicalPath = jarFolder.getCanonicalPath() + File.separator + currentEntry.getRealName();
            File currentEntryAsFile = new File(currentEntryCannonicalPath);

            if(currentEntry.isDirectory()) {
                currentEntryAsFile.mkdir();

                continue;
            }

            InputStream currentEntryInputStream = fileAsJarArchive.getInputStream(currentEntry);
            OutputStream currentEntryOutputStream = new FileOutputStream(currentEntryAsFile.getCanonicalPath());

            while(currentEntryInputStream.available() > 0) {
                currentEntryOutputStream.write(currentEntryInputStream.read());
            }

            currentEntryInputStream.close();
            currentEntryOutputStream.close();
        }

        fileAsJarArchive.close();
    }
}
