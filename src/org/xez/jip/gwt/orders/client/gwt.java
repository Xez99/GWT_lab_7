package org.xez.jip.gwt.orders.client;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import org.xez.jip.gwt.orders.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.xez.jip.gwt.orders.shared.Order;

import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class gwt implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network "
            + "connection and try again.";

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final OrdersServiceAsync greetingService = GWT.create(OrdersService.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button sendButton = new Button("Выбрать");
        final TextBox userIdField = new TextBox();
        userIdField.setText("00000000");
        final Label errorLabel = new Label();

        // We can add style names to widgets
        sendButton.addStyleName("sendButton");

        // Add the userIdField and sendButton to the RootPanel
        // Use RootPanel.get() to get the entire body element
        RootPanel.get("userIdContainer").add(userIdField);
        RootPanel.get("sendButtonContainer").add(sendButton);
        RootPanel.get("errorLabelContainer").add(errorLabel);

        // Focus the cursor on the name field when the app loads
        userIdField.setFocus(true);
        userIdField.selectAll();

        // Create the popup dialog box
        final DialogBox dialogBox = new DialogBox();
        final CellTable<Order> table = createCellTable();
        dialogBox.setText("Загрузка...");
        dialogBox.setAnimationEnabled(true);
        final Button closeButton = new Button("Закрыть");
        // We can set the id of a widget by accessing its Element
        closeButton.getElement().setId("closeButton");
        final Label textToServerLabel = new Label();
        final HTML serverResponseLabel = new HTML();
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        final HTML orderList = new HTML();
        dialogVPanel.add(orderList);
        dialogVPanel.add(textToServerLabel);
        dialogVPanel.add(table);
        dialogVPanel.add(serverResponseLabel);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);



        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
                sendButton.setEnabled(true);
                sendButton.setFocus(true);
            }
        });

        // Create a handler for the sendButton and userIdField
        class MyHandler implements ClickHandler, KeyUpHandler {
            /**
             * Fired when the user clicks on the sendButton.
             */
            public void onClick(ClickEvent event) {
                orderList.setHTML("<b>Список заказов пользователя:</b>" + userIdField.getText());
                sendNameToServer();
            }

            /**
             * Fired when the user types in the userIdField.
             */
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    orderList.setHTML("<b>Список заказов пользователя:</b>" + userIdField.getText());
                    sendNameToServer();
                }
            }

            /**
             * Send the name from the userIdField to the server and wait for a response.
             */
            private void sendNameToServer() {
                // First, we validate the input.
                errorLabel.setText("");
                String textToServer = userIdField.getText();
                if (!FieldVerifier.isValidName(textToServer)) {
                    errorLabel.setText("Id пользователя состоит из 8 цифр!");
                    return;
                }

                // Then, we send the input to the server.
                sendButton.setEnabled(false);
                serverResponseLabel.setText("");
                greetingService.greetServer(textToServer, new AsyncCallback<List<Order>>() {
                    public void onFailure(Throwable caught) {
                        caught.printStackTrace();
                        // Show the RPC error message to the user
                        dialogBox.setText("Remote Procedure Call - Failure");
                        serverResponseLabel.addStyleName("serverResponseLabelError");
                        serverResponseLabel.setHTML(SERVER_ERROR);
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }

                    public void onSuccess(List<Order> result) {
                        dialogBox.setText("");
                        serverResponseLabel.removeStyleName("serverResponseLabelError");
                        //serverResponseLabel.setHTML("RES:" + result);
                        table.setRowData(result);
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }
                });
            }
        }

        // Add a handler to send the name to the server
        MyHandler handler = new MyHandler();
        sendButton.addClickHandler(handler);
        userIdField.addKeyUpHandler(handler);
    }

    private CellTable<Order> createCellTable() {
        final CellTable<Order> table = new CellTable<Order>();
        table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        TextColumn<Order> orderIdColumn = new TextColumn<Order>() {
            @Override
            public String getValue(Order object) {
                return object.getOrderId();
            }
        };
        table.addColumn(orderIdColumn, "Номер заказа");

        TextColumn<Order> userIdColumn = new TextColumn<Order>() {
            @Override
            public String getValue(Order object) {
                return object.getUserId();
            }
        };
        table.addColumn(userIdColumn, "Пользователь");

        TextColumn<Order> userNameColumn = new TextColumn<Order>() {
            @Override
            public String getValue(Order object) {
                return object.getUserName();
            }
        };
        table.addColumn(userNameColumn, "ФИО");

        TextColumn<Order> orderColumn = new TextColumn<Order>() {
            @Override
            public String getValue(Order object) {
                StringBuilder sb = new StringBuilder();
                for(Order.Product p: object.getProducts()){
                    sb.append(p.getProductId())
                            .append(": ")
                            .append(p.getAmount())
                            .append("\n");
                }
                return sb.toString();
            }
        };
        table.addColumn(orderColumn, "Заказ");

        TextColumn<Order> titleColumn = new TextColumn<Order>() {
            @Override
            public String getValue(Order object) {
                StringBuilder sb = new StringBuilder();
                sb.append(object.getSum()/100)
                        .append('.')
                        .append(object.getSum()%100);
                return sb.toString();
            }
        };
        table.addColumn(titleColumn, "Итого");

        return table;
    }
}
