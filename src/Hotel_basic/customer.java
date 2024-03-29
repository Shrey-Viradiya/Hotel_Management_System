package Hotel_basic;

import UDexception.EmptyFieldException;
import UDexception.InvalidMobileNumber;
import UDexception.RoomsNotAvailable;
import Utilities.Item;
import Utilities.Service;

import java.io.Serializable;
import java.util.LinkedList;

public class customer implements Serializable {
// User Attributes

    private String customerId;
    private String Name;
    private String address;
    private long mobile;

    //rooms allocated
    private LinkedList<Room> rooms = new LinkedList<Room>();

    //orders
    private LinkedList<Item> items = new LinkedList<Item>();

    //Services
    private LinkedList<Service> services = new LinkedList<>();

    //Get methods

    public String getCustomerID(){
        return customerId;
    }

    public String getName(){
        return Name;
    }

    public String getAddress(){
        return address;
    }

    public long getMobile(){
        return mobile;
    }

    public LinkedList<Room> getRooms(){ return rooms; }

    public double getTotal() {
        double total = 0;

        for (Room x : rooms) {
            total += x.getStay_day() * x.getRentPerDay();
        }

        for (Item i : items) {
            total += i.getQuantity() * i.getPricePerUnit();
        }

        for (Service S : services) {
            total += S.getPrice();
        }

        return total;
    }

    // Set methods

    public void setCustomerID(String customerId){
        this.customerId = customerId;
    }

    public void setName(String n) throws EmptyFieldException{
        if(n == null && n.isEmpty()){
            throw new EmptyFieldException("Check the Input! String Empty or Null");
        }
        Name = n;
    }


    public void setAddress(String adr) throws EmptyFieldException{
        if(adr == null && adr.isEmpty()){
            throw new EmptyFieldException("Check the Input! String Empty or Null");
        }
        address = adr;
    }

    public void setMobile(long number) throws InvalidMobileNumber {
        if(number < 1000000000 || number > 9999999999L){
            throw new InvalidMobileNumber("Mobile Number Entered is Not Correct");
        }
        else{
            mobile = number;
        }
    }

    public void addRoom(Room r){
        rooms.add(r);
    }

    public void addItem(Item i){
        items.add(i);
    }

    public void addService(Service s) {
        services.add(s);
    }

    // String Display of the object
    @Override
    public String toString(){
        return "Customer: ID = " + customerId + " Name = " + Name + " Address = " + address + " Contact No. = " + mobile;
    }

    public String toStringProper() {
        return "\nCustomer: ID = " + customerId + "\nName = " + Name + "\nAddress = " + address + "\nContact No. = " + mobile;
    }

    public void generate_bill() {
        System.out.println("Generating Bill of Stay......");
        System.out.println("_____________________________");

        double total = 0;

        for (Room x : rooms) {
            total += x.getStay_day() * x.getRentPerDay();
        }

        System.out.println("Room Stay\t\t: " + total);

        for (Item i : items) {
            System.out.println(i.getItem() + "\t\t: " + (i.getQuantity() * i.getPricePerUnit()));
            total += i.getQuantity() * i.getPricePerUnit();
        }

        for (Service S : services) {
            System.out.println(S.getServiceName() + "\t\t: " + S.getPrice());
            total += S.getPrice();
        }

        System.out.println("__________________________________");

        System.out.println("Total\t\t: " + total);
    }

    public Room remove_room() throws RoomsNotAvailable {
        if (rooms.isEmpty()) {
            throw new RoomsNotAvailable("No rooms remaining...");
        } else {
            Room temp = rooms.pop();
            temp.setStay_day(0);
            return temp;
        }
    }

    public void addDay() {
        for (Room i : rooms) {
            i.setStay_day(i.getStay_day() + 1);
        }
    }

    public void getInfo() {
        System.out.println("Customer Info...");
        System.out.println("----------------");
        System.out.println("Name: " + Name);
        System.out.println("Contact: " + mobile);
        System.out.println("Address: " + address);
        System.out.println("Rooms Staying Info: ");
        System.out.println("--------------------");

        for (Room r : rooms) {
            System.out.println("Room No: " + r.getRoomNO());
            System.out.println("Bed No.: " + r.getBedNo());
            System.out.println("Rent Per Day: " + r.getRentPerDay());
            System.out.println("Stay Day: " + r.getStay_day());
            System.out.println("--------------------------------");
        }
    }
}