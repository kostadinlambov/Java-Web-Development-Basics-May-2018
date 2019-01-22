package javache;

import db.util.CsvFileManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StartUp {
    public static void main(String[] args) throws IOException {

//        File file = new File(WebConstants.DATABASE_COLLECTION_FOLDER_PATH + "users.csv");
//
//        System.out.println(file.exists());
//
//
//        List<String> users = new ArrayList<>();
//        for (int i = 1; i <= 10; i++) {
//            String id = UUID.randomUUID().toString();
//            String name = "Kiro" + i;
//            String password = "Password" + i + i;
//            users.add(String.format("%s,%s,%s", id, name, password));
//
//        }
//
//        CsvFileManager.writeLines(users,WebConstants.DATABASE_COLLECTION_FOLDER_PATH + "users.csv");

//        "f68ec375-d461-4c66-acb1-1382c23fa58a\tStamat1\tPassword11"



//        System.out.println(file.createNewFile());

//        List<String> fileReader = CsvFileManager.csvFileReader(WebConstants.DATABASE_COLLECTION_FOLDER_PATH + "users.csv");
//
//        for (String line : fileReader) {
//            System.out.println(line);
//        }





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
