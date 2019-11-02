package Hotel_basic;

import UDexception.EmptyFieldException;
import UDexception.InvalidMobileNumber;
import Utilities.Item;
import Utilities.Service;

import java.util.LinkedList;

public class customer {
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

    public String toString(){
        String S = "Customer: \nID = " + customerId + "\nName = " + Name + "\nAddress = " + address + "\nContact No. = " + mobile;
        return S;
    }

}
