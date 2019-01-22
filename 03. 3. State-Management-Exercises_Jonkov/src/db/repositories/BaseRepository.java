//package db.repositories;
//
////import org.hibernate.TransactionException;
////import org.softuni.database.util.RepositoryActionInvoker;
////import org.softuni.database.util.RepositoryActionResult;
////import org.softuni.database.util.RepositoryActionResultImpl;
////
////import javax.persistence.EntityManager;
////import javax.persistence.EntityManagerFactory;
////import javax.persistence.EntityTransaction;
////import javax.persistence.Persistence;
//
//public abstract class BaseRepository {
//
//
//    private String collectionName;
//
//    protected BaseRepository(String collectionName) {
//        this.setCollectionName(collectionName);
//    }
//
//    public String getCollectionName() {
//        return this.collectionName;
//    }
//
//    public void setCollectionName(String collectionName) {
//        this.collectionName = collectionName;
//    }
//
//    public boolean save(Object t){
////       return this.csvFileWriter.write(t.toString());
//       return false;
//    }
//
////    public Object
//
//    //
////    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
////            Persistence.createEntityManagerFactory("casebook");
////
////
////    private EntityTransaction entityTransaction;
////
////    protected EntityManager entityManager;
////
////    protected BaseRepository() {
////
////    }
////
////    private void initializeEntityManager() {
////        this.entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
////    }
////
////    private void initializeEntityTransaction() {
////        entityTransaction = this.entityManager.getTransaction();
////    }
////
////    private void dismiss() {
////        this.entityManager.close();
////    }
////
////    public static void close() {
////        ENTITY_MANAGER_FACTORY.close();
////    }
////
////
////    protected RepositoryActionResult executeAction(RepositoryActionInvoker invoker) {
////
////        RepositoryActionResult actionResult = new RepositoryActionResultImpl();
////        this.initializeEntityManager();
////        this.initializeEntityTransaction();
////
////        try {
////            this.entityTransaction.begin();
////
////            invoker.invoke(actionResult);
////
////            this.entityTransaction.commit();
////
////        } catch (TransactionException e) {
////            if (this.entityTransaction != null && this.entityTransaction.isActive()) {
////                this.entityTransaction.rollback();
////            }
////        }
////
////        this.dismiss();
////        return actionResult;
////    }
//
//}
