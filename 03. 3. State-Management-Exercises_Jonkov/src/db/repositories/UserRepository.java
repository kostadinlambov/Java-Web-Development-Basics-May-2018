//package db.repositories;
//
//import org.softuni.database.entities.User;
//
//import java.util.List;
//
//
//public class UserRepository extends BaseRepository {
//
//
//    public UserRepository() {
//
//    }
//
//
//    public List<User> findAll() {
//        return (List<User>) executeAction(repositoryActionResult -> {
//            repositoryActionResult.setResult(
//                    this.entityManager
//                            .createNativeQuery("SELECT * FROM users as u", User.class)
//                            .getResultList());
//        }).getResult();
//    }
//
//
//    public void saveUser(User user) {
//        executeAction(repositoryActionResult -> {
//            this.entityManager.persist(user);
//        });
//    }
//
//    public void addFriend(User user, User friend) {
//        executeAction(repositoryActionResult -> {
//            user.addFriend(friend);
//            friend.addFriend(user);
//
//            this.entityManager.merge(user);
//            this.entityManager.merge(friend);
//        });
//    }
//}