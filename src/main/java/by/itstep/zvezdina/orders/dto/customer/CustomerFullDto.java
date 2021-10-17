package by.itstep.zvezdina.orders.dto.customer;

import java.sql.Date;

public class CustomerFullDto {

    private int customerId;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String phone;
    private String address;
    private String city;
    private String state;
    private int points;
    private String email;

    public CustomerFullDto(int customerId, String firstName, String lastName, Date birthDate, String phone,
                           String address, String city, String state, int points, String email) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.points = points;
        this.email = email;
    }


}
