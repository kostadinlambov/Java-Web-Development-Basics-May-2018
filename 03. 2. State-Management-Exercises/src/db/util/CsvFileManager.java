package db.util;


import javache.WebConstants;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public final class CsvFileManager {

    private CsvFileManager() {
    }

    public static List<String> csvFileReader(String path) throws IOException {
//        List<String> input = Files.readAllLines(Paths.get(WebConstants.DATABASE_COLLECTION_FOLDER_PATH + "users.csv"));
        List<String> input = Files.readAllLines(Paths.get(path));

//        Files.write(
//                Paths.get("DATABASE_COLLECTION_FOLDER_PATH" + "users.csv"),
//                input,
//                StandardOpenOption.APPEND);

//        return String.join("", input);
        return input;

    }

    public static void writeBytes(byte[] byteData, OutputStream outputStream) throws IOException {
        DataOutputStream buffer = new DataOutputStream(outputStream);

        buffer.write(byteData);
    }

    public static void writeLines(List<String> content, String path) throws IOException {
//    public static void writeLines(String content, String path) throws IOException {

//       Path takePath = Paths.get("DATABASE_COLLECTION_FOLDER_PATH" + "users.csv");
        Path takePath = Paths.get(path);

        Files.write(
                takePath,
                content,
                StandardOpenOption.APPEND);
    }

//    * <p> <b>Usage example</b>: By default the method creates a new file or
//     * overwrites an existing file. Suppose you instead want to append bytes
//     * to an existing file:
//            * <pre>
//            *     Path path = ...
//            *     byte[] bytes = ...
//            *     Files.write(path, bytes, StandardOpenOption.APPEND);
//     * </pre>


//    public static Path write(Path path,
//                             Iterable<? extends CharSequence> lines,
//                             OpenOption... options)
}


