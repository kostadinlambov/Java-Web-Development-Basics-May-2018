package main.repositories;

import main.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository extends BaseRepository {
    public void createUser(User user) {
        this.execute(actionResult -> {
            this.entityManager.persist(user);
        });
    }

    public User findById(String id) {
        User result = (User) this.execute(actionResult -> {
            actionResult.setResult(
                    this.entityManager
                            .createNativeQuery(
                                    "SELECT * FROM users AS u WHERE u.id = \'" + id + "\'", User.class)
                            .getResultList()
                            .stream()
                            .findFirst()
                            .orElse(null));
        }).getResult();

        return result;
    }

    public User findByUsername(String username) {
        User result = (User) this.execute(actionResult -> {
            actionResult.setResult(
                    this.entityManager
                    .createNativeQuery(
                            "SELECT * FROM users AS u WHERE u.username = \'" + username + "\' OR u.email = \'" + username + "\'", User.class)
                    .getResultList()
                    .stream()
                    .findFirst()
                    .orElse(null));
        }).getResult();

        return result;
    }

    public List<User> findAll() {
        List<User> result = new ArrayList<User>();

        this.execute(actionResult -> {
            result.addAll(this.entityManager.createNativeQuery(
                    "SELECT * FROM users", User.class
            ).getResultList());
        });

        return result;
    }
}
