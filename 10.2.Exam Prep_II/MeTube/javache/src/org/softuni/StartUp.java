package org.softuni;

import org.softuni.javache.Server;
import org.softuni.javache.WebConstants;

import java.io.IOException;

public class StartUp {
    public static void main(String[] args) {
        start(args);
    }

    private static void start(String[] args) {
        int port = WebConstants.DEFAULT_SERVER_PORT;

        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }

        Server server = new Server(port);

        try {
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

