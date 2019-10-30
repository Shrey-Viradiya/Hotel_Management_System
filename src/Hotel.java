import java.util.*;
import java.lang.Math;
import Hotel_basic.*;
import Utilities.*;

public class Hotel {
    // Available Rooms
    private static LinkedList<Room> available_rooms_2bed = new LinkedList<Room>();
    private static LinkedList<Room> available_rooms_1bed = new LinkedList<Room>();

    // customers
    // static LinkedList<customer> customers = new LinkedList<customer>();

    // Employees
    LinkedList<Employee> employees = new LinkedList<Employee>();

    public static void main(String[] args) {
        System.out.println("Creating Hotel System: ");
        System.out.println("-----------------------");

        System.out.println("Allocating Rooms");
        System.out.println("----------------");
        for (int i = 0; i < 15; i++) {
            available_rooms_1bed.add(new Room(i,1,1000));
        }
        for (int i = 15; i < 30; i++) {
            available_rooms_2bed.add(new Room(i,2,800));
        }

        System.out.println(available_rooms_1bed);

        System.out.println("\nHiring Employees");
        System.out.println("----------------\n");

    }
}
