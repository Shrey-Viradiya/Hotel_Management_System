package Utilities;

public class Item {
    private String item;
    private int quantity;
    private double priceperunit;

    public Item(String item, int quantity, double priceperunit){
        this.item = item;
        this.quantity = quantity;
        this.priceperunit = priceperunit;
    }

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
