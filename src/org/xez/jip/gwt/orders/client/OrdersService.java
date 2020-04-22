package org.xez.jip.gwt.orders.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.xez.jip.gwt.orders.shared.Order;

import java.util.List;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("orders")
public interface OrdersService extends RemoteService {
    List<Order> greetServer(String name) throws IllegalArgumentException;
}
