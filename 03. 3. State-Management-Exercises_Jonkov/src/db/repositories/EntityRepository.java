package db.repositories;

import db.util.CsvFileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntityRepository<T> {


//    private String collectionName;

//    protected EntityRepository(String collectionName) {
//        this.setCollectionName(collectionName);
//    }

//    public String getCollectionName() {
//        return this.collectionName;
//    }
//
//    public void setCollectionName(String collectionName) {
//        this.collectionName = collectionName;
//    }


    public EntityRepository() {
    }


    public void save(T t, String path) throws IOException {
        List<String> objectsToSaveList = new ArrayList<>();
        objectsToSaveList.add(t.toString());
        CsvFileManager.writeLines(objectsToSaveList, path);
//        return false ;
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

//    public void saveUser(User user) {
//        executeAction(repositoryActionResult -> {
//            this.entityManager.persist(user);
//        });
//    }

    //Video 01:04:00
    public List<String> findAll(String path) throws IOException {
        return CsvFileManager.csvFileReader(path);
        //abstract
        //reflection
        // this.csvFileReader.readFile(this.collectionName + ".csv")
//        return  (T[]) (new Object[0]);
    }


}
