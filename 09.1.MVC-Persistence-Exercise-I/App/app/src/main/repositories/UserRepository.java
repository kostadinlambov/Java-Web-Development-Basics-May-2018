package repositories;

import entities.User;

import javax.persistence.NoResultException;
import java.util.List;

public class UserRepository extends BaseRepository {
    public UserRepository() {
    }

    public List<User> findAll() {
        List<User> result = (List<User>) executeAction(repositoryActionResult -> {
            repositoryActionResult.setResult(
                    this.entityManager.createNativeQuery(
                            "SELECT * FROM users", User.class)
                            .getResultList());
        }).getResult();

        return result;
    }

    public User findById(String userId) {
        User result = (User) executeAction(repositoryActionResult -> {
            User userFromDatabase;

            try {
                userFromDatabase = (User) this.entityManager.createNativeQuery(

                        "SELECT * FROM users WHERE id = '"
                                + userId
                                + "'", User.class)
                        .getSingleResult();
            } catch (NoResultException ignored) {
                userFromDatabase = null;
            }

            repositoryActionResult.setResult(userFromDatabase);
        }).getResult();

        return result;
    }

    public User findByUsername(String username) {
        User result = (User) executeAction(repositoryActionResult -> {
            User userFromDatabase;

            try {
                userFromDatabase = (User) this.entityManager.createNativeQuery(

                        "SELECT * FROM users WHERE username = '"
                                + username
                                + "'", User.class)
                        .getSingleResult();
            } catch (NoResultException ignored) {
                userFromDatabase = null;
            }

            repositoryActionResult.setResult(userFromDatabase);
        }).getResult();

        return result;
    }

    public void saveUser(User user) {
        executeAction(repositoryActionResult -> {
            this.entityManager.persist(user);
        });
    }
}
