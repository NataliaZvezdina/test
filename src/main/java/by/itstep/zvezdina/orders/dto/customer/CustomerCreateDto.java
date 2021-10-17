package by.itstep.zvezdina.orders.dto.customer;

import java.sql.Date;
import java.util.Objects;
import java.util.StringJoiner;

public class CustomerCreateDto {

    private String firstName;
    private String lastName;
    private Date birthDate;
    private String phone;
    private String address;
    private String city;
    private String state;
    private int points;
    private String email;
    private String password;

    public CustomerCreateDto() {}

    public CustomerCreateDto(String firstName, String lastName, Date birthDate, String phone, String address,
                             String city, String state, int points, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.points = points;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerCreateDto that = (CustomerCreateDto) o;
        return points == that.points && firstName.equals(that.firstName) && lastName.equals(that.lastName) && birthDate.equals(that.birthDate) && phone.equals(that.phone) && address.equals(that.address) && city.equals(that.city) && state.equals(that.state) && email.equals(that.email) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthDate, phone, address, city, state, points, email, password);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CustomerCreateDto.class.getSimpleName() + "[", "]")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("birthDate=" + birthDate)
                .add("phone='" + phone + "'")
                .add("address='" + address + "'")
                .add("city='" + city + "'")
                .add("state='" + state + "'")
                .add("points=" + points)
                .add("email='" + email + "'")
                .toString();
    }
}
