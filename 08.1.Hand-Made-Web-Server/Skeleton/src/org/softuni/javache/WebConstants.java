package org.softuni.javache;

public final class WebConstants {
    public static final Integer DEFAULT_SERVER_PORT = 8000;

    public static final String SERVER_HTTP_VERSION = "HTTP/1.1";

    public static final String RESOURCE_FOLDER_PATH =
            System.getProperty("user.dir")
            + "\\src\\org\\softuni\\resources\\";

    public static final String ASSETS_FOLDER_PATH =
            RESOURCE_FOLDER_PATH + "assets\\";

    public static final String PAGES_FOLDER_PATH =
            RESOURCE_FOLDER_PATH + "pages\\";

    private WebConstants() { }
}
