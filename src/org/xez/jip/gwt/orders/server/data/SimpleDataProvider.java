package org.xez.jip.gwt.orders.server.data;

import org.xez.jip.gwt.orders.shared.Order;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simple DataProvider realisation. Returns only few Hardcoded examples.
 */
public class SimpleDataProvider implements DataProvider {

    /** Data */
    List<Order> data = new LinkedList<>();

    /**
     * Data provider storage initialization
     */
    void initStorage() {
        List<Order.Product> products_0 = Arrays.asList(
                new Order.Product("423090", 149900, 1),
                new Order.Product("432446", 29900, 2)
        );
        List<Order.Product> products_1 = Arrays.asList(
                new Order.Product("423099", 69900, 2),
                new Order.Product("432446", 19900, 2)
        );

        List<Order> orderList = Arrays.asList(
                new Order("00000000", "Вася ноНэйм", "1_030_203_023_000", products_0),
                new Order("00000001", "Иванов Иван Иванович", "1_030_203_023_001", products_1)
        );
        data = orderList;
    }

    SimpleDataProvider(){
        initStorage();
    }


    /**
     * @param userId 8-length string with User ID
     * @return List of Orders by User ID
     */
    @Override
    public List<Order> getOrdersByUserId(String userId) {
        return data.stream()
                .filter((order) -> order.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
