import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.UUID;

import Hotel_basic.Room;
import Hotel_basic.customer;

import UDexception.EmptyFieldException;
import UDexception.InvalidMobileNumber;
import UDexception.RoomsNotAvailable;
import Utilities.Item;
import jdk.nashorn.internal.codegen.DumpBytecode;

public class Hotel {

    // Available Rooms
    private static LinkedList<Room> available_rooms_2bed = new LinkedList<Room>();
    private static LinkedList<Room> available_rooms_1bed = new LinkedList<Room>();

    // customers
    private static LinkedList<customer> customers = new LinkedList<customer>();

    // Employees
//    private static LinkedList<Employee> employees = new LinkedList<Employee>();

    private static void getInfo() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        Scanner S = new Scanner(System.in);
        int one,two;

        System.out.println("Enter the no. of 1 bed rooms and 2 bed room wanted...");
        one = Integer.parseInt(br.readLine());
        two = Integer.parseInt(br.readLine());
//        one = S.nextInt();
//        two = S.nextInt();

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

    private static void newCustomer() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        Scanner S = new Scanner(System.in);
        customer temp = new customer();

        temp.setCustomerID(UUID.randomUUID().toString());


        while(true){
            System.out.println("Enter the Name: ");
            try{
                temp.setName(br.readLine());
                break ;
            } catch (EmptyFieldException | IOException e) {
                System.out.println(e.getMessage());
            }
        }

        while(true){
            System.out.println("Enter the Address: ");
            try{
                temp.setAddress(br.readLine());
                break;
            } catch (EmptyFieldException | IOException e) {
                System.out.println(e.getMessage());
            }
        }

        while(true){
            System.out.println("Enter the Mobile No.: ");
            try{
                temp.setMobile(Long.parseLong(br.readLine()));
                break;
            } catch (InvalidMobileNumber invalidMobileNumber) {
                System.out.println(invalidMobileNumber.getMessage());
            } catch (InputMismatchException e){
                System.out.println("Input Type is Mismatched. Enter Integral values only");
                br.readLine();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        customers.add(temp);

    }

    private static void provideItem(customer c) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String item;
        int quantity;
        double ppu;

        System.out.println("Item: ");
        item = br.readLine();

        System.out.println("Quantity: ");
        quantity = Integer.parseInt(br.readLine());

        System.out.println("Price Per Unit: ");
        ppu = Double.parseDouble(br.readLine());

        Item temp = new Item(item, quantity, ppu);

        c.addItem(temp);
    }

    private static void allocateRoom(customer c) throws IOException, RoomsNotAvailable {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int one,two;

        System.out.println("Enter the no. of 1 bed rooms and 2 bed room wanted...");
        one = Integer.parseInt(br.readLine());
        two = Integer.parseInt(br.readLine());

        if(one < available_rooms_1bed.size()){
            throw new RoomsNotAvailable("One bed rooms not available according to the demand! Please check other options");
        }
        if(one < available_rooms_2bed.size()){
            throw new RoomsNotAvailable("Two bed rooms not available according to the demand! Please check other options");
        }
        for (int i = 0; i < one; i++) {
            c.addRoom(available_rooms_1bed.pop());
        }

        for (int i = 0; i < two; i++) {
            c.addRoom(available_rooms_2bed.pop());
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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

//        getInfo();
        newCustomer();
        newCustomer();
        newCustomer();

        // Searching for the Customer

        System.out.println("Enter the name to search");
        String name2search;
        int e = 0;

        name2search = br.readLine().toLowerCase();
        for (customer cust: customers ) {
            if (cust.getName().toLowerCase().contains(name2search))
            {
                System.out.println(cust);
                System.out.println("______________");
            }
        }
        
        //
        System.out.println(customers.peekFirst());
    }
}
