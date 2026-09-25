package model;

public class Customer {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String drivingLicenseNumber;

    public Customer(int id, String name, String phone,
                    String email, String drivingLicenseNumber) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.drivingLicenseNumber = drivingLicenseNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    @Override
    public String toString() {
        return String.format("%d | %s | %s | %s | License: %s",
                id, name, phone, email, drivingLicenseNumber);
    }
}
