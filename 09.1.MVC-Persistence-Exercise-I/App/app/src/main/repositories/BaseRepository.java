package repositories;

import org.hibernate.TransactionException;
import repositories.util.RepositoryActionInvoker;
import repositories.util.RepositoryActionResult;
import repositories.util.RepositoryActionResultImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public abstract class BaseRepository {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("articles");

    private EntityTransaction entityTransaction;

    protected EntityManager entityManager;

    protected BaseRepository() { }

    private void initializeEntityManager() {
        this.entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    private void initializeEntityTransaction() {
        this.entityTransaction = this.entityManager.getTransaction();
    }

    private void dismiss() {
        this.entityManager.close();
    }

    protected RepositoryActionResult executeAction(RepositoryActionInvoker invoker) {
        RepositoryActionResult actionResult = new RepositoryActionResultImpl();

        this.initializeEntityManager();
        this.initializeEntityTransaction();

        try {
            this.entityTransaction.begin();

            invoker.invoke(actionResult);

            this.entityTransaction.commit();
        } catch (TransactionException e) {
            if (this.entityTransaction != null
                    && this.entityTransaction.isActive()) {
                this.entityTransaction.rollback();
            }

            return null;
        }

        this.dismiss();

        return actionResult;
    }

    public static void close() {
        ENTITY_MANAGER_FACTORY.close();
    }
}
