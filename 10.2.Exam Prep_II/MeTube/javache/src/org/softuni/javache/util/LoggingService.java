package org.softuni.javache.util;

public class LoggingService {
    public void info(String content) {
        System.out.println("\u001B[36m" + "[INFO] " + content);
    }

    public void warning(String content) {
        System.out.println("\u001B[33m" + "[WARNING] " + content);
    }

    public void error(String content) {
        System.out.println("\u001B[31m" + "[ERROR] " + content);
    }
}
