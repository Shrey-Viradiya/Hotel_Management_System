package Hotel_basic;

public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String address;
    private int mobile;
    private String position;
    private int salary;

    public int getEmployeeId(){
        return employeeId;
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

    public int getMobile(){
        return mobile;
    }

    public String getPosition(){
        return position;
    }

    public int getSalary(){
        return salary;
    }

    // Set methods

    public void seteEployeeId(int employeeId){
        this.employeeId = employeeId;
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

    public boolean setMobile(int number){
        if(number < 1000000000 ){
            return false;
        }
        else{
            mobile = number;
            return true;
        }
    }

    public void setPosition(String pos){
        position = pos;
    }

    public void setSalary(int s){
        salary = s;
    }
}
