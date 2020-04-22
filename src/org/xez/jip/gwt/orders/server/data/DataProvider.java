package org.xez.jip.gwt.orders.server.data;

import org.xez.jip.gwt.orders.shared.Order;

import java.util.List;

/**
 * Interface for all Data Providers
 */
public interface DataProvider {
    List<Order> getOrdersByUserId(String userId);
}
