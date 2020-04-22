package org.xez.jip.gwt.orders.server.data;

/**
 * Giving access to DataProvider from any class of Server
 */
public class DataConfig {
    /**
     * Default DataProvider - just few Hardcoded examples;
     */
    private static DataProvider dataProvider = new SimpleDataProvider();

    /**
     * @return DataProvider implementation
     */
    public static DataProvider getDataProvider(){
        return dataProvider;
    }
}
