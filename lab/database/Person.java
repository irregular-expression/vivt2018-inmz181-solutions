package lab.database;

public class Person {
    private int id;
    private String name;
    private String phone;
    private boolean sex;
    private String address;
    private String occupation;

    public Person(int id, String name, String phone, boolean sex, String address, String occupation) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.address = address;
        this.occupation = occupation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }


}
