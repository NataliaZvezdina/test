package by.itstep.zvezdina.orders.service;

import by.itstep.zvezdina.orders.dto.customer.CustomerCreateDto;
import by.itstep.zvezdina.orders.dto.customer.CustomerUpdateDto;
import by.itstep.zvezdina.orders.dto.customer.CustomerViewDto;
import by.itstep.zvezdina.orders.entity.Customer;
import by.itstep.zvezdina.orders.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void create(CustomerCreateDto customer) {
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

    public CustomerUpdateDto findById(int id) {
        Customer found = customerRepository.findById(id);
        return new CustomerUpdateDto(found.getCustomerId(), found.getFirstName(), found.getLastName(),
                found.getBirthDate(), found.getPhone(), found.getAddress(), found.getCity(),
                found.getState(), found.getPoints());
    }

    public void update(CustomerUpdateDto customerToUpdate) {
        Customer old = customerRepository.findById(customerToUpdate.getCustomerId());
        Customer newOne = Customer.getBuilder()
                .setCustomerId(customerToUpdate.getCustomerId())
                .setFirstName(customerToUpdate.getFirstName())
                .setLastName(customerToUpdate.getLastName())
                .setBirthDate(customerToUpdate.getBirthDate())
                .setPhone(customerToUpdate.getPhone())
                .setAddress(customerToUpdate.getAddress())
                .setCity(customerToUpdate.getCity())
                .setState(customerToUpdate.getState())
                .setPoints(customerToUpdate.getPoints())
                .setEmail(old.getEmail())
                .setPassword(old.getPassword())
                .build();

        customerRepository.update(newOne);
    }
}
