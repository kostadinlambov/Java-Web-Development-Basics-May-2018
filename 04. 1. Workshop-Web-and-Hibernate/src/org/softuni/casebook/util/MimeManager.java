package org.softuni.casebook.util;

import java.io.File;

public final class MimeManager {
    public MimeManager() { }

    public static String getMimeType(String fileName) {
//        String fileName = file.getName();
        if (fileName.endsWith("css")) {
            return "text/css";
        } else if (fileName.endsWith("html")) {
            return "text/html";
        } else if (fileName.endsWith("jpg") || fileName.endsWith("jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith("png")) {
            return "image/png";
        }

        return "text/plain";
    }

}
