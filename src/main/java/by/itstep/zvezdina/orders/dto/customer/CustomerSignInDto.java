package by.itstep.zvezdina.orders.dto.customer;

import java.util.Objects;

public class CustomerSignInDto {

    private String email;
    private String password;

    public CustomerSignInDto() {}

    public CustomerSignInDto(String email, String password) {
        this.email = email;
        this.password = password;
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
        CustomerSignInDto that = (CustomerSignInDto) o;
        return email.equals(that.email) && password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return "CustomerSignInDto{" +
                "email='" + email + '\'' +
                '}';
    }
}
