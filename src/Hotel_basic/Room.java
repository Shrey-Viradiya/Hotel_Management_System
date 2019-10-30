package Hotel_basic;

public class Room {
    private int room_id;
    private String room_no;
    private int bed_number;
    private double rentPerDay;
    // private customer person;

    public Room(int id, int bed, double rentPerDay){
        setRoomID(id);
        setRoomNO("Room"+ (id+100));
        setBedNo(bed);
        setRent(rentPerDay);
    }

    public int getRoomID(){
        return room_id;
    }

    public String getrRoomNO(){
        return room_no;
    }

    public int getBedNo(){
        return bed_number;
    }

    public double getRentPerDay(){
        return rentPerDay;
    }

    // customer getPerson()
    // {
    //     return person;
    // }

    public void setRoomID(int x){
        room_id = x;
    }

    public void setRoomNO(String x){
        room_no = x;
    }

    public void setBedNo(int x){
        bed_number = x;
    }

    public void setRent(double x){
        rentPerDay = x;
    }

    // void setPerson(customer x){
    //     person = x;
    // }
}
