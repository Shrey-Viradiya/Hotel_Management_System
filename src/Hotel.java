import Hotel_basic.Room;
import Hotel_basic.customer;
import UDexception.CustomerNotFound;
import UDexception.EmptyFieldException;
import UDexception.InvalidMobileNumber;
import UDexception.RoomsNotAvailable;
import Utilities.Item;
import Utilities.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.UUID;

public class Hotel {

    // Available Rooms
    private static LinkedList<Room> available_rooms_2bed = new LinkedList<>();
    private static LinkedList<Room> available_rooms_1bed = new LinkedList<>();

    // customers
    private static LinkedList<customer> customers = new LinkedList<>();

    // Employees
//    private static LinkedList<Employee> employees = new LinkedList<Employee>();

    static {
        System.out.println("Creating Hotel System: ");
        System.out.println("-----------------------");

        System.out.println("Allocating Rooms");
        System.out.println("----------------");

        for (int i = 0; i < 15; i++) {
            available_rooms_1bed.add(new Room(i, 1, 1000));
        }
        for (int i = 15; i < 30; i++) {
            available_rooms_2bed.add(new Room(i, 2, 1800));
        }
    }

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
            } catch (InvalidMobileNumber | IOException exp) {
                System.out.println(exp.getMessage());
            } catch (InputMismatchException e){
                System.out.println("Input Type is Mismatched. Enter Integral values only");
                br.readLine();
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

    private static void provideService(customer c) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String Service;
        double Price;

        System.out.println("Service: ");
        Service = br.readLine();

        System.out.println("Price: ");
        Price = Double.parseDouble(br.readLine());

        Service temp = new Service(Service, Price);

        c.addService(temp);
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
        if (two < available_rooms_2bed.size()) {
            throw new RoomsNotAvailable("Two bed rooms not available according to the demand! Please check other options");
        }
        for (int i = 0; i < one; i++) {
            c.addRoom(available_rooms_1bed.pop());
        }

        for (int i = 0; i < two; i++) {
            c.addRoom(available_rooms_2bed.pop());
        }
    }

    private static customer fetchCustomer() throws IOException, CustomerNotFound {
        // Searching for the Customer
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter the name to search");
        String name2search;

        name2search = br.readLine().toLowerCase();
        for (customer cust : customers) {
            if (cust.getName().toLowerCase().contains(name2search)) {
                System.out.println(cust);
                System.out.println("______________");
            }
        }

        System.out.println("Enter the Mobile No. of the customer to fetch it:");
        Long mobile2search;

        mobile2search = Long.parseLong(br.readLine());
        for (customer cust : customers) {
            if (cust.getMobile() == mobile2search) {
                return cust;
            } else {
                throw new CustomerNotFound("No customer with given mobile number is registered.");
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Main_control_loop:
        while (true) {
            System.out.println("\n\n______________________");
            System.out.println("Welcome to Hotel JavaKenia Management System");
            System.out.println("______________________\n\n");
            System.out.println("1. Add New Customer");
            System.out.println("2. Fetch the Customer");
            System.out.println("3. Get Info");
            System.out.println("0. Quit");
            System.out.println("______________________\n\n");
            System.out.println("Enter Your choice: ");

            short input;
            input = Short.parseShort(br.readLine());

            switch (input) {
                case 0:
                    break Main_control_loop;
                case 1:
                    newCustomer();
                    break;
                case 2:
                    boolean flag = false;
                    do {
                        try {
                            customer fetched2serve = fetchCustomer();
                            flag = false;

                            // Customer Service:
                            System.out.println("\n______________________\nCustomer Service Options");
                            System.out.println("1. Allocate Rooms");
                            System.out.println("2. Item/ Orders");
                            System.out.println("3. Services");
                            System.out.println("4. Check Out");

                            short innerInput;
                            innerInput = Short.parseShort(br.readLine());

                            switch (innerInput) {
                                case 1:
                                    try {
                                        allocateRoom(fetched2serve);
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                case 2:
                                    provideItem(fetched2serve);
                                    break;
                                case 3:
                                    provideService(fetched2serve);
                                    break;
                                case 4:

                                    break;
                                default:
                                    System.out.println("Unexpected Value Given! Try Again! ");
                                    break;
                            }


                        } catch (IOException e) {
                            System.out.println("Input Error! Try Again..");
                            flag = true;
                        } catch (CustomerNotFound customerNotFound) {
                            System.out.println(customerNotFound.getMessage());
                            flag = true;
                        }
                    } while (flag);
                    break;
                case 3:
                    getInfo();
                    break;

                default:
                    System.out.println("Unexpected Value Given! Try Again! ");
                    break;
        }
        }

    }
}
