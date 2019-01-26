package org.softuni;

import org.softuni.casebook.CasebookApplication;
import org.softuni.database.entities.User;
import org.softuni.database.repositories.BaseRepository;
import org.softuni.database.repositories.UserRepository;
import org.softuni.javache.Server;
import org.softuni.javache.WebConstants;
import org.softuni.javache.api.RequestHandler;
import org.softuni.javache.http.HttpSessionStorage;
import org.softuni.javache.http.HttpSessionStorageImpl;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StartUp {
    public static void main(String[] args) {
        start(args);

        //Create Users
//        User user = new User();
////        user.setUserName("Stamat");
////        user.setPassword("678");
////
////
////        UserRepository userRepository = new UserRepository();
////
////        userRepository.saveUser(user);
////
////        UserRepository.close();

//        // Add friends relations
//        UserRepository userRepository = new UserRepository();
//        List<User> users = userRepository.findAll();
//        userRepository.addFriend(users.get(0), users.get(1));
//        UserRepository.close();

        // Repo findAll


        // Test saveUser()
//
//        UserRepository userRepository = new UserRepository();
//
//        User prakash = new User();
//        prakash.setUserName("Prakash");
//        prakash.setPassword("123");
//
//        User stamat = new User();
//        stamat.setUserName("Stamat");
//        stamat.setPassword("678");
//
//        userRepository.saveUser(prakash);
//        userRepository.saveUser(stamat);


//        List<User> users = userRepository.findAll();
//
//        users.forEach(x -> System.out.println(x.getUserName()));
//        BaseRepository.close();


        // Test saveUser()
//        UserRepository userRepository = new UserRepository();
//        List<User> users = userRepository.findAll();
//        userRepository.addFriend(users.get(0), users.get(1));
//        BaseRepository.close();
    }

    private static HttpSessionStorage getSessionStorage() {
        return new HttpSessionStorageImpl();
    }

    private static Set<RequestHandler> initializeApplications() {

        return new HashSet<RequestHandler>() {{
            add(new CasebookApplication(getSessionStorage()));
        }};
    }

    private static void start(String[] args) {
        int port = WebConstants.DEFAULT_SERVER_PORT;

        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }


        Server server = new Server(port, initializeApplications());

        try {
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
