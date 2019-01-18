package javache;

public final class WebConstants {

    private WebConstants() {
    }

    public static final int DEFAULT_SERVER_PORT = 8000;

    public static final String PROTOCOL_VERSION = "HTTP/1.1";

    public static final String RESOURCE_FOLDER_PATH = System.getProperty("user.dir") + "\\src\\resources";

    public static final int SOCKET_TIMEOUT_MILLISECONDS = 5000;

    public static final String LISTENING_MESSAGE = "Listening on port: ";

    public static final String ERROR_PAGE_PATH = RESOURCE_FOLDER_PATH + "\\pages\\error.html";

    public static final int CONNECTION_KILL_LIMIT = 5000;
}
