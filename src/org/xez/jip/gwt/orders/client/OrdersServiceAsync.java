package org.xez.jip.gwt.orders.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.xez.jip.gwt.orders.shared.Order;

import java.util.List;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface OrdersServiceAsync {
    void greetServer(String input, AsyncCallback<List<Order>> callback)
            throws IllegalArgumentException;
}
