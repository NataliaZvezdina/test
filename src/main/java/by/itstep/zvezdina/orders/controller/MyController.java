package by.itstep.zvezdina.orders.controller;

import by.itstep.zvezdina.orders.dto.CustomerSignInDto;
import by.itstep.zvezdina.orders.dto.ItemViewDto;
import by.itstep.zvezdina.orders.dto.OrderViewDto;
import by.itstep.zvezdina.orders.dto.customer.CustomerCreateDto;
import by.itstep.zvezdina.orders.entity.*;
import by.itstep.zvezdina.orders.repository.*;
import by.itstep.zvezdina.orders.service.CustomerDtoService;
import by.itstep.zvezdina.orders.service.ItemViewDtoService;
import by.itstep.zvezdina.orders.service.OrderViewDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class MyController {

    private final ProductRepository productRepository;
    private final ShipperRepository shipperRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderStatusRepository orderStatusRepository;

    private final ItemViewDtoService itemViewDtoService;
    private final OrderViewDtoService orderViewDtoService;
    private final CustomerDtoService customerDtoService;


    @Autowired
    public MyController(ProductRepository productRepository, ShipperRepository shipperRepository,
                        CustomerRepository customerRepository, OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository, OrderStatusRepository orderStatusRepository,
                        ItemViewDtoService itemViewDtoService, OrderViewDtoService orderViewDtoService,
                        CustomerDtoService customerDtoService) {
        this.productRepository = productRepository;
        this.shipperRepository = shipperRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.itemViewDtoService = itemViewDtoService;
        this.orderViewDtoService = orderViewDtoService;
        this.customerDtoService = customerDtoService;

    }

    @GetMapping ("/index")
    public String getIndex(Model model) {

        try {
            List<Product> foundProducts = productRepository.findAll();
            System.out.println("Found products: ");
            System.out.println(foundProducts);

            model.addAttribute("foundProducts", foundProducts);
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/shippers")
    public String getShippers(Model model) {
        try {
            List<Shipper> foundShippers = shipperRepository.findAll();
            model.addAttribute("foundShippers", foundShippers);
            return "shippers";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/customers")
    public String getCustomers(Model model) {
        try {
            List<Customer> foundCustomers = customerRepository.findAll();
            model.addAttribute("foundCustomers", foundCustomers);
            return "customers";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/customer-create")
    public String createCustomerForm(CustomerCreateDto customer) {
        return "customer-create";
    }

    @PostMapping("/customer-create")
    public String createCustomer(CustomerCreateDto customer) {
        //customerRepository.create(customer);
        customerDtoService.createCustomerFullDto(customer);
        return "redirect:/customers";
    }

    @GetMapping("/customer-delete/{id}")
    public String deleteCustomer(@PathVariable int id) {
        customerRepository.deleteById(id);
        return "redirect:/customers";
    }

    @GetMapping("/customer-update/{id}")
    public String updateCustomerForm(Model model, @PathVariable int id) {
        Customer customer = customerRepository.findById(id);
        System.out.println("Found customer to update");
        model.addAttribute("customer", customer);
        return "customer-update";
    }

    @PostMapping("/customer-update")
    public String updateCustomer(Customer customer) {
        customerRepository.update(customer);
        return "redirect:/customers";
    }

    @GetMapping("/profile/{id}")
    public String getSingleCustomer(Model model, @PathVariable int id) {
        Customer customer = customerRepository.findById(id);
        List<OrderViewDto> orders = orderViewDtoService.findAllByCustomerId(id);
        model.addAttribute("customer", customer);
        model.addAttribute("orders", orders);

        return "profile";
    }

    @GetMapping("/orders")
    public String getOrders(Model model) {
        try {
            List<Order> foundOrders = orderRepository.findAll();
            List<OrderViewDto> foundOrderViewsDto = orderViewDtoService.findAll();
            model.addAttribute("foundOrders", foundOrders);
            model.addAttribute("foundOrderViewsDto", foundOrderViewsDto);
            return "orders";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/orders/{id}")
    public String getOrderItems(Model model, @PathVariable int id) {
        try {
            Order singleOrder = orderRepository.findById(id);
            //List<OrderItem> foundOrderItems = orderItemRepository.findByOrderId(id);
            int customerId = singleOrder.getCustomerId();
            Customer customer = customerRepository.findById(customerId);

            int orderStatusId = singleOrder.getStatusId();
            OrderStatus orderStatus = orderStatusRepository.findById(orderStatusId);

            List<ItemViewDto> items = itemViewDtoService.findAllByOrderId(id);
            double totalOrderPrice = items.stream()
                    .mapToDouble(item -> (item.getQuantity() * item.getUnitPrice()))
                    .sum();

            model.addAttribute("singleOrder", singleOrder);
            model.addAttribute("customer", customer);
            model.addAttribute("orderStatus", orderStatus);
            model.addAttribute("total", totalOrderPrice);
            model.addAttribute("items", items);

            return "single-order";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/sign-in-page")
    public String getSignInForm(CustomerSignInDto customer) {
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String getCustomerSignIn(CustomerSignInDto customer) {
        Optional<Customer> found = customerDtoService.findByEmail(customer.getEmail());
        if (found.isPresent()) {
            int id = found.get().getCustomerId();
            if (found.get().getPassword().equals(customer.getPassword())) {
                return "redirect:/profile/" + id;
            }
        }
        return "redirect:/sign-in-page";
    }

    @GetMapping("/product-create")
    public String createProductForm(Product product) {
        return "product-create";
    }

    @PostMapping("/product-create")
    public String createProduct(Product product) {
        productRepository.create(product);
        return "redirect:/index";
    }

    @GetMapping("/shipper-create")
    public String createShipperForm(Shipper shipper) {
        return "/shipper-create";
    }

    @PostMapping("shipper-create")
    public String createShipper(Shipper shipper) {
        shipperRepository.create(shipper);
        return "redirect:/shippers";
    }
}
