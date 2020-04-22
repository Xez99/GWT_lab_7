package org.xez.jip.gwt.orders.server;

import org.xez.jip.gwt.orders.client.OrdersService;
import org.xez.jip.gwt.orders.server.data.DataConfig;
import org.xez.jip.gwt.orders.server.data.DataProvider;
import org.xez.jip.gwt.orders.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.xez.jip.gwt.orders.shared.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class OrdersServiceImpl extends RemoteServiceServlet implements OrdersService {

    public List<Order> greetServer(String input) throws IllegalArgumentException {
        // Verify that the input is valid.
        if (!FieldVerifier.isValidName(input)) {
            // If the input is not valid, throw an IllegalArgumentException back to
            // the client.
            throw new IllegalArgumentException("ID must be 8 characters long");
        }

        input = escapeHtml(input);

        DataProvider data = DataConfig.getDataProvider();
        return data.getOrdersByUserId(input);
    }

    /**
     * Escape an html string. Escaping data received from the client helps to
     * prevent cross-site script vulnerabilities.
     *
     * @param html the html string to escape
     * @return the escaped string
     */
    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
                ">", "&gt;");
    }
}
