package repositories;

import repositories.util.RepositoryActionInvoker;
import repositories.util.RepositoryActionResult;
import repositories.util.RepositoryActionResultImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public abstract class BaseRepository {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("metube_db");

    private EntityTransaction transaction;

    protected EntityManager entityManager;

    protected BaseRepository() {
    }

    private void initEntityManager() {
        this.entityManager = emf.createEntityManager();
    }

    private void initTransaction() {
        if(this.transaction != null && this.transaction.isActive()) {
            throw new IllegalArgumentException("Transaction is active!");
        }

        this.transaction = this.entityManager.getTransaction();
        this.transaction.begin();
    }

    private void commitTransaction() {
        if(this.transaction == null || !this.transaction.isActive()) {
            throw new IllegalArgumentException("Transaction is null or inactive!");
        }

        this.transaction.commit();
    }

    private void dispose() {
        this.entityManager.close();
    }

    protected RepositoryActionResult execute(RepositoryActionInvoker invoker) {
        RepositoryActionResult actionResult = new RepositoryActionResultImpl();

        try {
            this.initEntityManager();
            this.initTransaction();
            invoker.invoke(actionResult);
            this.commitTransaction();
            this.dispose();
        } catch (Exception e) {
            if(this.transaction != null) {
                this.transaction.rollback();
            }

            e.printStackTrace();
        }

        return actionResult;
    }
}
