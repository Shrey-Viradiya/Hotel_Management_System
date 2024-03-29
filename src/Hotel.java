import Hotel_basic.Room;
import Hotel_basic.customer;
import UDexception.*;
import Utilities.Item;
import Utilities.Payment;
import Utilities.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hotel {

    // Available Rooms
    private static LinkedList<Room> available_rooms_2bed = new LinkedList<>();
    private static LinkedList<Room> available_rooms_1bed = new LinkedList<>();

    // customers
    private static LinkedList<customer> customers = new LinkedList<>();

    // Vouchers Available
    private static LinkedList<String> vouchers = new LinkedList<>();

    // Employees
//    private static LinkedList<Employee> employees = new LinkedList<Employee>();

    // Room Increasing Thread
    private static class DayChange
            implements Runnable     {
        public void run() {
            try {
                while (true) {
                    // Pause for 120 seconds
                    Thread.sleep(120000);
                    // Day Change
                    for (customer c : customers) {
                        c.addDay();
                    }

                    // Writing Operations on Log File:
                    try {
                        PrintWriter output = new PrintWriter(new FileOutputStream(log_file, true));
                        output.append("\nDay Change");
                        output.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Exiting Application... Closing All Threads");
            }
        }
    }

    // Log file creation
    private static File log_file = new File("logs.txt");

    // voucher file creation
    private static File voucher_file = new File("vouchers.txt");

    // Customer file Creation
    private static File customer_data = new File("customers.txt");

    // OneBedRoom file creation
    private static File OneBedRoom = new File("OneBedRoom.txt");

    // TwoBedRoom file creation
    private static File TwoBedRoom = new File("TwoBedRoom.txt");

    // Register file creation
    private static File Register = new File("Register.csv");

    // Date_time variables
    private static DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private static Calendar calobj = Calendar.getInstance();

    private static void initialize() throws IOException {

System.out.println(" _    _       _       _     _____ _           _ _                                  \n" +
        "| |  | |     | |     | |   / ____| |         | (_)                                 \n" +
        "| |__| | ___ | |_ ___| |  | (___ | |__   __ _| |_ _ __ ___   __ _  __ _  __ _ _ __ \n" +
        "|  __  |/ _ \\| __/ _ \\ |   \\___ \\| '_ \\ / _` | | | '_ ` _ \\ / _` |/ _` |/ _` | '__|\n" +
        "| |  | | (_) | ||  __/ |   ____) | | | | (_| | | | | | | | | (_| | (_| | (_| | |   \n" +
        "|_|  |_|\\___/ \\__\\___|_|  |_____/|_| |_|\\__,_|_|_|_| |_| |_|\\__,_|\\__,_|\\__,_|_|   ");
        if (!(customer_data.exists()) || !(OneBedRoom.exists()) || !(TwoBedRoom.exists())) {
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
        } else {

            System.out.println("Initializing Hotel System: ");
            System.out.println("-----------------------");

            System.out.println("Fetching Data");
            System.out.println("----------------");

            FileInputStream fi1 = new FileInputStream(customer_data);
            ObjectInputStream input = new ObjectInputStream(fi1);

            try {
                while (true) {
                    customer S = (customer) input.readObject();
                    customers.add(S);
                }
            } catch (EOFException | ClassNotFoundException ignored) {
            }

            input.close();
            fi1.close();
            customer_data.delete();

            FileInputStream fi2 = new FileInputStream(OneBedRoom);
            ObjectInputStream input2 = new ObjectInputStream(fi2);

            try {
                while (true) {
                    Room R = (Room) input2.readObject();
                    available_rooms_1bed.add(R);
                }
            } catch (EOFException | ClassNotFoundException ignored) {
            }

            input2.close();
            fi2.close();
            OneBedRoom.delete();

            FileInputStream fi3 = new FileInputStream(TwoBedRoom);
            ObjectInputStream input3 = new ObjectInputStream(fi3);

            try {
                while (true) {
                    Room R = (Room) input3.readObject();
                    available_rooms_2bed.add(R);
                }
            } catch (EOFException | ClassNotFoundException ignored) {
            }

            input3.close();
            fi3.close();
            TwoBedRoom.delete();

            FileInputStream fi4 = new FileInputStream(voucher_file);
            ObjectInputStream input4 = new ObjectInputStream(fi4);

            try {
                while (true) {
                    String R = (String) input4.readObject();
                    vouchers.add(R);
                }
            } catch (EOFException | ClassNotFoundException ignored) {
            }

            input4.close();
            fi4.close();
            voucher_file.delete();
        }
    }

    private static void getInfo() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        Scanner S = new Scanner(System.in);
        int one, two;
        while(true) {
        try{

                System.out.println("Enter the no. of 1 bed rooms and 2 bed room wanted...");
                one = Integer.parseInt(br.readLine());
                two = Integer.parseInt(br.readLine());
                break;

        }catch (NumberFormatException ignored){
            System.out.println("Please Enter a valid Number of rooms");
        }
        }
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

    private static customer newCustomer() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


//        Scanner S = new Scanner(System.in);
        customer temp = new customer();

        temp.setCustomerID(UUID.randomUUID().toString());


        while (true) {
            System.out.println("Enter the Name: ");
            try {
                Pattern specialCharPattern = Pattern.compile("[`[0-9]~!@#$%^&.*()/*-+,<>;:?{}=_]");
                String Name = br.readLine();

                if (Name == null || Name.trim().isEmpty()) {
                    throw new EmptyFieldException("Name Empty Not Valid! Try Again...");
                }
                Matcher m = specialCharPattern.matcher(Name);
                boolean b = m.find();
                if(b){
                    throw new SpecialCharNotAllowed("Special Characters are not allowed in Name. Try Again.");
                }
                temp.setName(Name);
                break;
            } catch (SpecialCharNotAllowed| EmptyFieldException | IOException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Enter the Address: ");
            try {
                Pattern specialCharPattern = Pattern.compile("[`.~!@#$%^&*()*-+<>;:?{}=_]");
                String Address = br.readLine();

                if (Address == null || Address.trim().isEmpty()) {
                    throw new EmptyFieldException("Address Empty Not Valid! Try Again...");
                }
                Matcher m = specialCharPattern.matcher(Address);
                boolean b = m.find();
                if(b){
                    throw new SpecialCharNotAllowed("\nSpecial Characters are not allowed in Address.\nOnly , . / are allowed.\nTry Again.");
                }
                temp.setAddress(Address);
                break;
            } catch (SpecialCharNotAllowed| EmptyFieldException | IOException e) {
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

        final String time = df.format(calobj.getTime());

        // Writing Operations on Log File:
        try {
            PrintWriter output = new PrintWriter(new FileOutputStream(log_file, true));
            output.append("\nNew customer: (@time: ").append(time).append("): ").append(String.valueOf(temp));
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Writing to Register File:
        FileWriter csvWriter = new FileWriter(Register,true);
        csvWriter.append(temp.getCustomerID()).append(',').append(temp.getName()).append(',').append(time).append(',').append(" ").append('\n');
        csvWriter.flush();
        csvWriter.close();

        return temp;
    }

    private static void provideItem(customer c) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String item;
        int quantity;
        double ppu;

        while (true) {
            System.out.println("Enter the Item: ");
            try {
                Pattern specialCharPattern = Pattern.compile("[`[0-9]~!@#$%^&.*()/*-+,<>;:?{}=_]");
                item = br.readLine();

                if (item == null || item.trim().isEmpty()) {
                    throw new EmptyFieldException("Item Name Empty Not Valid! Try Again...");
                }
                Matcher m = specialCharPattern.matcher(item);
                boolean b = m.find();
                if(b){
                    throw new SpecialCharNotAllowed("Special Characters are not allowed in Item Name. Try Again.");
                }
                break;
            } catch (SpecialCharNotAllowed| EmptyFieldException | IOException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Enter the Quantity: ");
            try {
                while (true)
                    try {
                        quantity = Integer.parseInt(br.readLine());
                        break;
                    } catch (NumberFormatException ignored) {
                    }
                break;
            } catch (IOException exp) {
                System.out.println(exp.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Input Type is Mismatched. Enter Integral values only");
                br.readLine();
            }
        }

        while (true) {
            System.out.println("Enter the Price Per Unit: ");
            try {
                while (true)
                    try {
                        ppu = Double.parseDouble(br.readLine());
                        break;
                    } catch (NumberFormatException ignored) {
                    }
                break;
            } catch (IOException exp) {
                System.out.println(exp.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Input Type is Mismatched. Enter Integral values only");
                br.readLine();
            }
        }

        Item temp = new Item(item, quantity, ppu);

        c.addItem(temp);

        // Writing Operations on Log File:
        try {
            PrintWriter output = new PrintWriter(new FileOutputStream(log_file, true));
            output.append("\nItem Ordered: (@time: ").append(df.format(calobj.getTime())).append("): By customer: ").append(c.getCustomerID()).append(": ").append(String.valueOf(temp));
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void provideService(customer c) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String Service;
        double Price;

        while (true) {
            System.out.println("Enter the Service: ");
            try {
                Pattern specialCharPattern = Pattern.compile("[`[0-9]~!@#$%^&.*()/*-+,<>;:?{}=_]");
                Service = br.readLine();

                if (Service == null || Service.trim().isEmpty()) {
                    throw new EmptyFieldException("Service Name Empty Not Valid! Try Again...");
                }
                Matcher m = specialCharPattern.matcher(Service);
                boolean b = m.find();
                if(b){
                    throw new SpecialCharNotAllowed("Special Characters are not allowed in Item Name. Try Again.");
                }
                break;
            } catch (SpecialCharNotAllowed| EmptyFieldException | IOException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            System.out.println("Enter the Price: ");
            try {
                while (true)
                    try {
                        Price = Double.parseDouble(br.readLine());
                        break;
                    } catch (NumberFormatException ignored) {
                    }
                break;
            } catch (IOException exp) {
                System.out.println(exp.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Input Type is Mismatched. Enter Integral values only");
                br.readLine();
            }
        }

        Service temp = new Service(Service, Price);

        c.addService(temp);

        // Writing Operations on Log File:
        try {
            PrintWriter output = new PrintWriter(new FileOutputStream(log_file, true));
            output.append("\nService Provided: (@time: ").append(df.format(calobj.getTime())).append("): To customer: ").append(c.getCustomerID()).append(": ").append(String.valueOf(temp));
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
            System.out.println("RoomNo " + temp.getRoomNO() + " Allocated..");
            // Writing Operations on Log File:
            try {
                PrintWriter output = new PrintWriter(new FileOutputStream(log_file, true));
                output.append("\nRoom Allocated: (@time: ").append(df.format(calobj.getTime())).append("): To customer: ").append(c.getCustomerID()).append(": ").append(String.valueOf(temp));
                output.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < two; i++) {
            Room temp = available_rooms_2bed.pop();
            c.addRoom(temp);
            System.out.println("RoomNo " + temp.getRoomNO() + " Allocated..");
            // Writing Operations on Log File:
            try {
                PrintWriter output = new PrintWriter(new FileOutputStream(log_file, true));
                output.append("\nRoom Allocated: (@time: ").append(df.format(calobj.getTime())).append("): To customer: ").append(c.getCustomerID()).append(": ").append(String.valueOf(temp));
                output.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void serve(customer c){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {

            CustomerMenu:
            while (true) {
                // Customer Service:
                System.out.println("\n______________________\nCustomer Service Options");
                System.out.println("1. Allocate Rooms");
                System.out.println("2. Item/ Orders");
                System.out.println("3. Services");
                System.out.println("4. Check Out");
                System.out.println("5. Get Info");
                System.out.println("6. Avail Voucher Offer");
                System.out.println("0. Exit customer menu");

                short innerInput;
                innerInput = Short.parseShort(br.readLine());

                switch (innerInput) {
                    case 1:
                        try {
                            allocateRoom(c);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        provideItem(c);
                        break;
                    case 3:
                        provideService(c);
                        break;
                    case 4:
                        checkout(c);
                        break CustomerMenu;
                    case 5:
                        c.getInfo();
                        break;
                    case 6:
                        System.out.println("Enter Voucher Code:");
                        String temp = br.readLine();
                        if (vouchers.contains(temp)) {
                            if (temp.contains("_CarWash3000_")) {
                                c.addService(new Service("CarWash", 0));
                            } else if (temp.contains("_Spa&Massage5000_")) {
                                c.addService(new Service("Spa & Massage", 0));
                            } else if (temp.contains("_FreeDrinksFor1Night8000_")) {
                                c.addItem(new Item("Free Drinks", 25, 0));
                            }
                            vouchers.remove(temp);
                        } else {
                            System.out.println("Invalid Voucher Code...\n\n");
                        }

                        break;
                    case 7:
                        System.out.println("Total: " + c.getTotal());
                        break;
                    case 0:
                        break CustomerMenu;
                    default:
                        System.out.println("Unexpected Value Given! Try Again! ");
                        break;

                }
            }

        } catch (IOException e) {
            System.out.println("Input Error! Try Again..");
        } catch (Exception E) {
            System.out.println("Error Occurred! Please Try Again..");
        }
    }

    private static void serve(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
                System.out.println("5. Get Info");
                System.out.println("6. Avail Voucher Offer");
                System.out.println("0. Exit customer menu");

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
                        fetched2serve.getInfo();
                        break;
                    case 6:
                        System.out.println("Enter Voucher Code:");
                        String temp = br.readLine();
                        if (vouchers.contains(temp)) {
                            if (temp.contains("_CarWash3000_")) {
                                fetched2serve.addService(new Service("CarWash", 0));
                            } else if (temp.contains("_Spa&Massage5000_")) {
                                fetched2serve.addService(new Service("Spa & Massage", 0));
                            } else if (temp.contains("_FreeDrinksFor1Night8000_")) {
                                fetched2serve.addItem(new Item("Free Drinks", 25, 0));
                            }
                            vouchers.remove(temp);
                        } else {
                            System.out.println("Invalid Voucher Code...\n\n");
                        }

                        break;
                    case 7:
                        System.out.println("Total: " + fetched2serve.getTotal());
                        break;
                    case 0:
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
        } catch (Exception E) {
            System.out.println("Error Occurred! Please Try Again..");
        }
    }

    private static void serveByRoom(){
        try {
            customer fetched2serve = fetchByRoom();
            serve(fetched2serve);
        } catch (IOException | CustomerNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    private static customer fetchByRoom() throws IOException, CustomerNotFound {
        // Searching for the Customer
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter the RoomNo: ");
        String room2search;

        room2search = br.readLine().toLowerCase();

        for (Room r:available_rooms_1bed ) {
            if (r.getRoomNO().toLowerCase().contains(room2search)){
                throw new CustomerNotFound("Room is not allocated Yet");
            }
        }

        for (Room r:available_rooms_2bed ) {
            if (r.getRoomNO().toLowerCase().contains(room2search)){
                throw new CustomerNotFound("Room is not allocated Yet");
            }
        }

        for (customer C: customers ) {
            for (Room r: C.getRooms()) {
                if (r.getRoomNO().toLowerCase().contains(room2search)){
                    return C;
                }
            }
        }

        throw new CustomerNotFound("Check the Room Number Entered...");
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
                System.out.println(cust.toStringProper());
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

    private static void checkout(customer fetched2serve) throws IOException {
        fetched2serve.generate_bill();
        Payment checkout_payment = new Payment(fetched2serve.getTotal());

        try {
            while (true) {
                Room temp;
                temp = fetched2serve.remove_room();
                temp.setStay_day(1);
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


        checkout_payment.doPayment();
        try {
            vouchers.add(checkout_payment.generateVoucher());
        } catch (ExpensiveOfferException ignored) {
        }


        customers.remove(fetched2serve);
        String CheckOutDateTime = df.format(calobj.getTime());

        // Writing Operations on Log File:
        try {
            PrintWriter output = new PrintWriter(new FileOutputStream(log_file, true));
            output.append("\nCustomer Checked Out: (@time: ").append(CheckOutDateTime).append("): ").append(String.valueOf(fetched2serve));
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Editing Register
        File newReg = new File("temp.csv");
        Scanner x = new Scanner(Register);
        x.useDelimiter("[,\n]");
        try {
            FileWriter csvWriter = new FileWriter(newReg, true);
            BufferedWriter bw = new BufferedWriter(csvWriter);
            PrintWriter pw = new PrintWriter(bw);

            while(x.hasNext()){
                String ID = x.next();
                String Name = x.next();
                String CheckIn = x.next();
                String CheckOut = x.next();

                if(ID.equals(fetched2serve.getCustomerID())){
                    pw.append(fetched2serve.getCustomerID()).append(",").append(fetched2serve.getName()).append(",").append(CheckIn).append(",").append(CheckOutDateTime).append("\n");
                }
                else{
                    pw.append(ID+","+Name+","+CheckIn+","+CheckOut + "\n");
                }
            }
            x.close();
            pw.flush();
            pw.close();
            csvWriter.close();
            Register.delete();
            newReg.renameTo(new File("Register.csv"));

        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Register might not have updated...");
        }
    }

    public static void main(String[] args) throws Exception {
        initialize();

        Thread t = new Thread(new DayChange());
        t.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Main_control_loop:
        while (true) {
            System.out.println("\n\n______________________");
            System.out.println("Welcome to Hotel Shalimaaar Management System");
            System.out.println("_____________________\n\n");
            System.out.println("1. Add New Customer");
            System.out.println("2. Fetch the Customer");
            System.out.println("3. Fetch by Room");
            System.out.println("4. Get Info");
            System.out.println("5. Get Customers");
            System.out.println("6. Clear Screen");
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
                    t.interrupt();
                    break Main_control_loop;
                case 1:
                    serve(newCustomer());
                    break;
                case 2:
                    serve();
                    break;
                case 3:
                    serveByRoom();
                    break;
                case 4:
                    getInfo();
                    break;
                case 5:
                    System.out.println("Customers............");
                    System.out.println("_____________________");

                    for (customer c : customers) {
                        System.out.println(c.getName());
                    }
                    break;
                case 6:
                    new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
                    break;
                default:
                    System.out.println("Unexpected Value Given! Try Again! ");
                    break;
            }
        }

        // writing to Files

        {
            FileOutputStream fo = new FileOutputStream(customer_data);
            ObjectOutputStream output = new ObjectOutputStream(fo);
            try {
                while (true) {
                    output.writeObject(customers.pop());
                }
            } catch (Exception ignored) {

            }
            output.close();
            fo.close();
        }

        writeToFiles(OneBedRoom, available_rooms_1bed);

        writeToFiles(TwoBedRoom, available_rooms_2bed);

        {
            FileOutputStream fo2 = new FileOutputStream(voucher_file);
            ObjectOutputStream output2 = new ObjectOutputStream(fo2);
            try {
                while (true) {
                    output2.writeObject(vouchers.pop());
                }
            } catch (Exception ignored) {

            }
            output2.close();
            fo2.close();
        }
    }

    private static void writeToFiles(File twoBedRoom, LinkedList<Room> available_rooms_2bed) throws IOException {
        FileOutputStream fo3 = new FileOutputStream(twoBedRoom);
        ObjectOutputStream output3 = new ObjectOutputStream(fo3);
        try {
            while (true) {
                output3.writeObject(available_rooms_2bed.pop());
            }
        } catch (Exception ignored) {

        }
        output3.close();
        fo3.close();
    }
}