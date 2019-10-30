package Utilities;

public class Item {
    private String item;
    private int quantity;
    private double priceperunit;

    String getString() {
        return item;
    }

    int getQuantity() {
        return quantity;
    }

    double getPricePerUnit() {
        return priceperunit;
    }

    void setString(String x) {
        item = x;
    }

    void setQuantity(int x) {
        quantity = x;
    }

    void setPricePerUnit(double x) {
        priceperunit = x;
    }

}
