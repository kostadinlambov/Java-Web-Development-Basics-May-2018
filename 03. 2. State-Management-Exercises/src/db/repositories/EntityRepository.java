package db.repositories;

import db.util.CsvFileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntityRepository<T> {

    public EntityRepository() {
    }


    public void save(T t, String path) throws IOException {
        List<String> objectsToSaveList = new ArrayList<>();
        objectsToSaveList.add(t.toString());
        CsvFileManager.writeLines(objectsToSaveList, path);
    }

    public String findByUsername(String username, String path) throws IOException {

        List<String> entities = CsvFileManager.csvFileReader(path);

        for (String entity : entities) {
            String currentUsername = entity.split(",")[1];
            if (currentUsername.equals(username)) {
                return entity;
            }
        }

        return null;
    }

    public List<String> findAll(String path) throws IOException {
        return CsvFileManager.csvFileReader(path);
    }
}
