package Hotel_basic;

import java.util.*;
import Utilities.*;

public class customer {
// User Attributes

    private int customerId;
    private String firstName;
    private String lastName;
    private String address;
    private int mobile;

    //rooms allocated
    LinkedList<Room> rooms = new LinkedList<Room>();

    //orders
    LinkedList<Item> items = new LinkedList<Item>();

    //Get methods

    int getCustomerID(){
        return customerId;
    }

    String getFirstName(){
        return firstName;
    }

    String getLastName(){
        return lastName;
    }

    String getAddress(){
        return address;
    }

    int getMobile(){
        return mobile;
    }

    // Set methods

    void setCustomerID(int customerId){
        this.customerId = customerId;
    }

    void setFirstName(String n){
        firstName = n;
    }

    void setLastName(String n){
        lastName = n;
    }

    void setAddress(String adr){
        address = adr;
    }

    boolean setMobile(int number){
        if(number < 1000000000 ){
            return false;
        }
        else{
            mobile = number;
            return true;
        }
    }

}
