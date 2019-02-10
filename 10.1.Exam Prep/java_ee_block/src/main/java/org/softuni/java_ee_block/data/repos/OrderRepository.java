package org.softuni.java_ee_block.data.repos;

import org.softuni.java_ee_block.data.models.Order;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderRepository {
    private Set<Order> orders;

    public OrderRepository() {
        this.orders = new HashSet<>();
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public Set<Order> getAllOrders() {
        return this.orders;
    }

    public List<Order> sortOrdersByDate() {
        return this.orders.stream()
                .sorted(Comparator.comparing(Order::getMadeOn).reversed())
                .collect(Collectors.toList());
    }
}
