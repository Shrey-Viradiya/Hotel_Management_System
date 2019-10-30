package Hotel_basic;

import java.util.*;
import Utilities.*;
import UDexception.*;

public class customer {
// User Attributes

    private String customerId;
    private String firstName;
    private String lastName;
    private String address;
    private long mobile;

    //rooms allocated
    LinkedList<Room> rooms = new LinkedList<Room>();

    //orders
    LinkedList<Item> items = new LinkedList<Item>();

    //Get methods

    public String getCustomerID(){
        return customerId;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
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

    public void setFirstName(String n){
        firstName = n;
    }

    public void setLastName(String n){
        lastName = n;
    }

    public void setAddress(String adr){
        address = adr;
    }

    public boolean setMobile(long number) throws InvalidMobileNumber{
        if(number < 1000000000 || number > 9999999999L){
            throw new InvalidMobileNumber("Mobile Number Entered is Not Correct");
        }
        else{
            mobile = number;
            return true;
        }
    }

}
