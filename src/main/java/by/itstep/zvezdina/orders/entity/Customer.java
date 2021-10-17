package by.itstep.zvezdina.orders.entity;

import java.sql.Date;
import java.util.Objects;

public class Customer {

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
    private String password;

    public Customer() {}

    public Customer(int customerId, String firstName, String lastName, Date birthDate, String phone, String address,
                    String city, String state, int points, String email, String password) {
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
        this.password = password;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Customer customer = new Customer();

        public Builder setCustomerId(int customerId) {
            customer.customerId = customerId;
            return this;
        }

        public Builder setFirstName(String firstName) {
            customer.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            customer.lastName = lastName;
            return this;
        }

        public Builder setBirthDate(Date birthDate) {
            customer.birthDate = birthDate;
            return this;
        }

        public Builder setPhone(String phone) {
            customer.phone = phone;
            return this;
        }

        public Builder setAddress(String address) {
            customer.address = address;
            return this;
        }

        public Builder setCity(String city) {
            customer.city = city;
            return this;
        }

        public Builder setState(String state) {
            customer.state = state;
            return this;
        }

        public Builder setPoints(int points) {
            customer.points = points;
            return this;
        }

        public Builder setEmail(String email) {
            customer.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            customer.password = password;
            return this;
        }

        public Customer build() {
            return customer;
        }
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
        Customer customer = (Customer) o;
        return customerId == customer.customerId && points == customer.points && firstName.equals(customer.firstName) && lastName.equals(customer.lastName) && birthDate.equals(customer.birthDate) && phone.equals(customer.phone) && address.equals(customer.address) && city.equals(customer.city) && state.equals(customer.state) && email.equals(customer.email) && password.equals(customer.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, firstName, lastName, birthDate, phone, address, city, state, points, email, password);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", points=" + points +
                ", email='" + email + '\'' +
                '}';
    }
}
