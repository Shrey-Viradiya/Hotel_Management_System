package Hotel_basic;

public class Room {
    private int room_id;
    private String room_no;
    private int bed_number;
    private int stay_day = 1;
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

    public String getRoomNO() {
        return room_no;
    }

    public int getBedNo(){
        return bed_number;
    }

    int getStay_day() {
        return stay_day;
    }

    double getRentPerDay() {
        return rentPerDay;
    }

    // customer getPerson()
    // {
    //     return person;
    // }

    private void setRoomID(int x) {
        room_id = x;
    }

    private void setRoomNO(String x) {
        room_no = x;
    }

    private void setBedNo(int x) {
        bed_number = x;
    }

    private void setRent(double x) {
        rentPerDay = x;
    }

    // void setPerson(customer x){
    //     person = x;
    // }

    void setStay_day(int x) {
        stay_day = x;
    }
}
