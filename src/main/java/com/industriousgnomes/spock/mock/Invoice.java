package com.industriousgnomes.spock.mock;

import org.codehaus.groovy.util.ListHashMap;

import java.util.Map;
import java.util.logging.Logger;

public class Invoice {

    private Logger logger = Logger.getLogger("Invoice");

    private Pricing pricing;

    private ListHashMap<String, Double> itemList = new ListHashMap<>();

    public void add(String item, double quantity) {
        logger.info("Added item: " + item + " at quantity: " + quantity);
        itemList.put(item, quantity);
    }

    public double lookupPriceOfLastItem() {
        String item = (String)itemList.keySet().toArray()[itemList.size() - 1];
        return pricing.getPrice(item);
    }

    public double getTotal() {
        double total = 0.0d;

        for (Map.Entry<String, Double> entry : itemList.entrySet()) {
            total += pricing.getPrice(entry.getKey()) * entry.getValue();
        }

        return total;
    }
}
