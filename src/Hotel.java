import java.awt.*;
import java.util.*;
import java.lang.Math;
import Hotel_basic.*;
import UDexception.*;
import Utilities.*;

public class Hotel {

    // Available Rooms
    private static LinkedList<Room> available_rooms_2bed = new LinkedList<Room>();
    private static LinkedList<Room> available_rooms_1bed = new LinkedList<Room>();

    // customers
    private static LinkedList<customer> customers = new LinkedList<customer>();

    // Employees
//    private static LinkedList<Employee> employees = new LinkedList<Employee>();

    private static void getInfo(){
        Scanner S = new Scanner(System.in);
        int one,two;

        System.out.println("Enter the no. of 1 bed rooms and 2 bed room wanted...");
        one = S.nextInt();
        two = S.nextInt();

        if(one <= available_rooms_1bed.size()){
            System.out.println("One bed Rooms available..");
        }
        else{
            System.out.println("One bed Rooms NOT available..");
        }

        if(two <= available_rooms_2bed.size()){
            System.out.println("Two bed Rooms available..");
        }
        else{
            System.out.println("Two bed Rooms NOT available..");
        }
    }

    private static void newCustomer(){
        Scanner S = new Scanner(System.in);
        customer temp = new customer();

        temp.setCustomerID(UUID.randomUUID().toString());

        System.out.println("Enter the First Name: ");
        temp.setFirstName(S.next());

        System.out.println("Enter the Last Name: ");
        temp.setLastName(S.next());

        System.out.println("Enter the Address: ");
        S.nextInt();
        temp.setAddress(S.nextLine());

        X:
        while(true){
            System.out.println("Enter the Mobile No.: ");
            try{
                temp.setMobile(S.nextLong());
                break X;
            } catch (InvalidMobileNumber invalidMobileNumber) {
                System.out.println(invalidMobileNumber.getMessage());
            }
        }

        customers.add(temp);

    }

    public static void main(String[] args) {
        System.out.println("Creating Hotel System: ");
        System.out.println("-----------------------");

        System.out.println("Allocating Rooms");
        System.out.println("----------------");
        for (int i = 0; i < 15; i++) {
            available_rooms_1bed.add(new Room(i,1,1000));
        }
        for (int i = 15; i < 30; i++) {
            available_rooms_2bed.add(new Room(i,2,1800));
        }

//        System.out.println(available_rooms_1bed);

//        System.out.println("\nHiring Employees");
//        System.out.println("----------------\n");

        getInfo();
        newCustomer();

        System.out.println(customers.peekFirst());
    }
}
