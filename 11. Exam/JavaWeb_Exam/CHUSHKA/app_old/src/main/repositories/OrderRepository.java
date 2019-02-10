package main.repositories;



import main.entities.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository extends BaseRepository {
    public void createOrder(Order order) {
        this.execute(actionResult -> {
            this.entityManager.persist(order);
        });
    }

    public List<Order> findAll() {
        List<Order> result = new ArrayList<Order>();

        this.execute(actionResult -> {
            result.addAll(this.entityManager.createNativeQuery(
                    "SELECT * FROM orders", Order.class
            ).getResultList());
        });

        return result;
    }
}
