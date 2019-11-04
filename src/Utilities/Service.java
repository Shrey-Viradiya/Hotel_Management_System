package Utilities;

public class Service {
    private String Service;
    private double Price;

    public Service(String ser, double pr) {
        Service = ser;
        Price = pr;
    }

    // getter methods

    public String getServiceName() {
        return Service;
    }

    public double getPrice() {
        return Price;
    }

    // Setter Methods

    public void setService(String x) {
        Service = x;
    }

    public void setPrice(double x) {
        Price = x;
    }

    @Override
    public String toString() {
        return Service + "@ Rs. " + Price;
    }
}