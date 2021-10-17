package by.itstep.zvezdina.orders.service;

import by.itstep.zvezdina.orders.dto.customer.CustomerCreateDto;
import by.itstep.zvezdina.orders.entity.Customer;
import by.itstep.zvezdina.orders.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerDtoService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerDtoService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findAll()
                .stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findAny();
    }

    public void createCustomerFullDto(CustomerCreateDto customer) {
        Customer c = Customer.getBuilder()
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setBirthDate(customer.getBirthDate())
                .setAddress(customer.getAddress())
                .setCity(customer.getCity())
                .setState(customer.getState())
                .setPhone(customer.getPhone())
                .setEmail(customer.getEmail())
                .setPoints(customer.getPoints())
                .setPassword(customer.getPassword())
                .build();

        customerRepository.create(c);
    }
}
