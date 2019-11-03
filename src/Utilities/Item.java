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

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPricePerUnit() {
        return priceperunit;
    }

    public void setItem(String x) {
        item = x;
    }

    public void setQuantity(int x) {
        quantity = x;
    }

    public void setPricePerUnit(double x) {
        priceperunit = x;
    }

}
