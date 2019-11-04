import Hotel_basic.Room;
import Hotel_basic.customer;
import UDexception.CustomerNotFound;
import UDexception.EmptyFieldException;
import UDexception.InvalidMobileNumber;
import UDexception.RoomsNotAvailable;
import Utilities.Item;
import Utilities.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    // Log file creation
    private static File log_file = new File("logs.txt");
    private static PrintWriter output;

    static {
        try {
            output = new PrintWriter(new FileOutputStream(log_file, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    // Date_time variables
    private static DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private static Calendar calobj = Calendar.getInstance();

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

    public Hotel() throws FileNotFoundException {
    }

    private static void getInfo() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        Scanner S = new Scanner(System.in);
        int one, two;

        System.out.println("Enter the no. of 1 bed rooms and 2 bed room wanted...");
        one = Integer.parseInt(br.readLine());
        two = Integer.parseInt(br.readLine());
//        one = S.nextInt();
//        two = S.nextInt();

        if (one <= available_rooms_1bed.size()) {
            System.out.println("One bed Rooms available..");
        } else {
            System.out.println("One bed Rooms NOT available..");
        }

        if (two <= available_rooms_2bed.size()) {
            System.out.println("Two bed Rooms available..");
        } else {
            System.out.println("Two bed Rooms NOT available..");
        }

        System.out.println("One bed Rooms: " + available_rooms_1bed.size() + " Rooms available");
        System.out.println("Two bed Rooms: " + available_rooms_2bed.size() + " Rooms available");
    }

    private static void newCustomer() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        Scanner S = new Scanner(System.in);
        customer temp = new customer();

        temp.setCustomerID(UUID.randomUUID().toString());


        while (true) {
            System.out.println("Enter the Name: ");
            try {
                temp.setName(br.readLine());
                break;
            } catch (EmptyFieldException | IOException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Enter the Address: ");
            try {
                temp.setAddress(br.readLine());
                break;
            } catch (EmptyFieldException | IOException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Enter the Mobile No.: ");
            try {
                while (true)
                    try {
                        temp.setMobile(Long.parseLong(br.readLine()));
                        break;
                    } catch (NumberFormatException ignored) {
                    }
                break;
            } catch (InvalidMobileNumber | IOException exp) {
                System.out.println(exp.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Input Type is Mismatched. Enter Integral values only");
                br.readLine();
            }
        }

        customers.add(temp);

        // Writing Operations on Log File:
        output.append("\nNew customer: (@time: " + df.format(calobj.getTime()) + "): " + temp);

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

        // Writing Operations on Log File:
        output.append("\nItem Ordered: (@time: " + df.format(calobj.getTime()) + "): By customer: " + c.getCustomerID() + ": " + temp);

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

        // Writing Operations on Log File:
        output.append("\nService Provided: (@time: " + df.format(calobj.getTime()) + "): To customer: " + c.getCustomerID() + ": " + temp);

    }

    private static void allocateRoom(customer c) throws IOException, RoomsNotAvailable {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int one, two;

        System.out.println("Enter the no. of 1 bed rooms and 2 bed room wanted...");
        one = Integer.parseInt(br.readLine());
        two = Integer.parseInt(br.readLine());

        if (one > available_rooms_1bed.size()) {
            throw new RoomsNotAvailable("One bed rooms not available according to the demand! Please check other options");
        }
        if (two > available_rooms_2bed.size()) {
            throw new RoomsNotAvailable("Two bed rooms not available according to the demand! Please check other options");
        }
        for (int i = 0; i < one; i++) {
            Room temp = available_rooms_1bed.pop();
            c.addRoom(temp);
            // Writing Operations on Log File:
            output.append("\nRoom Allocated: (@time: " + df.format(calobj.getTime()) + "): To customer: " + c.getCustomerID() + ": " + temp);

        }

        for (int i = 0; i < two; i++) {
            Room temp = available_rooms_2bed.pop();
            c.addRoom(temp);
            // Writing Operations on Log File:
            output.append("\nRoom Allocated: (@time: " + df.format(calobj.getTime()) + "): To customer: " + c.getCustomerID() + ": " + temp);

        }
    }

    private static customer fetchCustomer() throws IOException, CustomerNotFound {
        // Searching for the Customer
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter the name to search");
        String name2search;
        int count = 0;

        name2search = br.readLine().toLowerCase();
        for (customer cust : customers) {
            if (cust.getName().toLowerCase().contains(name2search)) {
                System.out.println(cust);
                System.out.println("______________");
                count++;
            }
        }

        if (count == 0) {
            throw new CustomerNotFound("No customer with given name is registered.");
        }
        System.out.println("Enter the Mobile No. of the customer to fetch it:");
        Long mobile2search;

        mobile2search = Long.parseLong(br.readLine());
        for (customer cust : customers) {
            if (cust.getMobile() == mobile2search) {
                return cust;
            }
        }
        throw new CustomerNotFound("No customer with given mobile number is registered.");
    }

    private static void checkout(customer fetched2serve) {
        fetched2serve.generate_bill();

        try {
            while (true) {
                Room temp;
                temp = fetched2serve.remove_room();

                if (temp.getBedNo() == 1) {
                    available_rooms_1bed.add(temp);
                } else {
                    available_rooms_2bed.add(temp);
                }
            }
        } catch (RoomsNotAvailable roomsNotAvailable) {
            System.out.println(roomsNotAvailable.getMessage());
            System.out.println("Room(s) Cleaning Done. Room(s) are now available to be used again. ");
        }

        customers.remove(fetched2serve);

        // Writing Operations on Log File:
        output.append("\nCustomer Checked Out: (@time: " + df.format(calobj.getTime()) + "): " + fetched2serve);

    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Main_control_loop:
        while (true) {
            System.out.println("\n\n______________________");
            System.out.println("Welcome to Hotel JavaKania Management System");
            System.out.println("______________________\n\n");
            System.out.println("1. Add New Customer");
            System.out.println("2. Fetch the Customer");
            System.out.println("3. Get Info");
            System.out.println("4. Get Customers");
            System.out.println("0. Quit");
            System.out.println("______________________\n\n");
            System.out.println("Enter Your choice: ");

            short input;
            while (true)
                try {
                    input = Short.parseShort(br.readLine());
                    break;
                } catch (NumberFormatException ignored) {

                }

            switch (input) {
                case 0:
                    output.close();
                    break Main_control_loop;
                case 1:
                    newCustomer();
                    break;
                case 2:
                        try {
                            customer fetched2serve = fetchCustomer();

                            CustomerMenu:
                            while (true) {
                                // Customer Service:
                                System.out.println("\n______________________\nCustomer Service Options");
                                System.out.println("1. Allocate Rooms");
                                System.out.println("2. Item/ Orders");
                                System.out.println("3. Services");
                                System.out.println("4. Check Out");
                                System.out.println("5. Exit customer menu");

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
                                        checkout(fetched2serve);
                                        break CustomerMenu;
                                    case 5:
                                        break CustomerMenu;
                                    default:
                                        System.out.println("Unexpected Value Given! Try Again! ");
                                        break;

                                }
                            }

                        } catch (IOException e) {
                            System.out.println("Input Error! Try Again..");
                        } catch (CustomerNotFound customerNotFound) {
                            System.out.println(customerNotFound.getMessage());
                        }
                    break;
                case 3:
                    getInfo();
                    break;
                case 4:
                    System.out.println("Customers............");
                    System.out.println("_____________________");

                    for (customer c : customers) {
                        System.out.println(c.getName());
                    }
                    break;
                default:
                    System.out.println("Unexpected Value Given! Try Again! ");
                    break;
        }
        }
    }
}
