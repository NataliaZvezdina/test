package by.itstep.zvezdina.orders.controller;

import by.itstep.zvezdina.orders.dto.customer.CustomerSignInDto;
import by.itstep.zvezdina.orders.dto.ItemViewDto;
import by.itstep.zvezdina.orders.dto.OrderViewDto;
import by.itstep.zvezdina.orders.dto.customer.CustomerCreateDto;
import by.itstep.zvezdina.orders.dto.customer.CustomerUpdateDto;
import by.itstep.zvezdina.orders.dto.product.ProductCreateDto;
import by.itstep.zvezdina.orders.dto.shipper.ShipperCreateDto;
import by.itstep.zvezdina.orders.entity.*;
import by.itstep.zvezdina.orders.repository.*;

import by.itstep.zvezdina.orders.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static by.itstep.zvezdina.orders.security.SecurityService.*;

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
    private final ProductDtoService productDtoService;
    private final ShipperDtoService shipperDtoService;


    @Autowired
    public MyController(ProductRepository productRepository, ShipperRepository shipperRepository,
                        CustomerRepository customerRepository, OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository, OrderStatusRepository orderStatusRepository,
                        ItemViewDtoService itemViewDtoService, OrderViewDtoService orderViewDtoService,
                        CustomerDtoService customerDtoService, ProductDtoService productDtoService,
                        ShipperDtoService shipperDtoService) {
        this.productRepository = productRepository;
        this.shipperRepository = shipperRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.itemViewDtoService = itemViewDtoService;
        this.orderViewDtoService = orderViewDtoService;
        this.customerDtoService = customerDtoService;
        this.productDtoService = productDtoService;
        this.shipperDtoService = shipperDtoService;
    }

    @GetMapping ("/index")
    public String getIndex(Model model, @RequestParam(name = "page", defaultValue = "1") Integer page) {
        if (!isLoggedIn()) {
            return "redirect:/sign-in";
        }

        try {
            List<Product> foundProducts = productRepository.getPage(page);
            System.out.println("Found products: ");
            System.out.println(foundProducts);

            model.addAttribute("foundProducts", foundProducts);
            model.addAttribute("isLoggedIn", isLoggedIn());
            model.addAttribute("userId", getUserId());
            model.addAttribute("currentPage", page);

            Customer onlineCustomer = customerRepository.findById(getUserId());
            model.addAttribute("customerFullName", onlineCustomer.getFirstName() + " " +
                    onlineCustomer.getLastName());

            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/shippers")
    public String getShippers(Model model, @RequestParam(name = "page", defaultValue = "1") Integer page) {
        if (!isLoggedIn()) {
            return "redirect:/sign-in";
        }
        try {
            List<Shipper> foundShippers = shipperRepository.getPage(page);
            model.addAttribute("foundShippers", foundShippers);
            model.addAttribute("currentPage", page);
            return "shippers";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/customers")
    public String getCustomers(Model model, @RequestParam(name = "page", defaultValue = "1") Integer page) {
        if (!isLoggedIn()) {
            return "redirect:/sign-in";
        }
        try {
            List<Customer> foundCustomers = customerRepository.getPage(page);
            model.addAttribute("foundCustomers", foundCustomers);
            model.addAttribute("currentPage", page);
            return "customers";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/customer-delete/{id}")
    public String deleteCustomer(@PathVariable int id) {
        if (!isLoggedIn()) {
            return "redirect:/sign-in";
        }
        customerRepository.deleteById(id);
        return "redirect:/customers";
    }

    @GetMapping("/customer-update/{id}")
    public String getUpdateCustomerForm(Model model, @PathVariable int id) {
        if (!isLoggedIn()) {
            return "redirect:/sign-in";
        }
        CustomerUpdateDto customerToUpdate = customerDtoService.findById(id);
        model.addAttribute("customerToUpdate", customerToUpdate);
        return "customer-update";
    }


    @PostMapping("/customer-update")
    public String updateCustomer(CustomerUpdateDto customer) {
        customerDtoService.update(customer);
        return "redirect:/customers";
    }

    @GetMapping("/profile/{id}")
    public String getSingleCustomer(Model model, @PathVariable int id) {
        if (!isLoggedIn()) {
            return "redirect:/sign-in";
        }
        Customer customer = customerRepository.findById(id);
        List<OrderViewDto> orders = orderViewDtoService.findAllByCustomerId(id);
        model.addAttribute("customer", customer);
        model.addAttribute("orders", orders);

        return "profile";
    }

    @GetMapping("/orders")
    public String getOrders(Model model, @RequestParam(name = "page", defaultValue = "1") Integer page) {
        if (!isLoggedIn()) {
            return "redirect:/sign-in";
        }
        try {
            List<Order> foundOrders = orderRepository.findAll();
            List<OrderViewDto> foundOrderViewsDto = orderViewDtoService.getPage(page);
            model.addAttribute("foundOrders", foundOrders);
            model.addAttribute("foundOrderViewsDto", foundOrderViewsDto);
            model.addAttribute("currentPage", page);
            return "orders";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/orders/{id}")
    public String getOrderItems(Model model, @PathVariable int id) {
        if (!isLoggedIn()) {
            return "redirect:/sign-in";
        }
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

    @GetMapping("/order-delete/{id}")
    public String deleteOrder(@PathVariable int id) {
        if (!isLoggedIn()) {
            return "redirect:/sign-in";
        }
        orderRepository.deleteById(id);
        return "redirect:/orders";
    }

    @GetMapping("/sign-in")
    public String getSignInForm(Model model) {
        CustomerSignInDto request = new CustomerSignInDto();
        model.addAttribute("request", request);
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String authorize(CustomerSignInDto request) {
        Optional<Customer> found = customerDtoService.findByEmail(request.getEmail());
        if (found.isPresent()) {
            int id = found.get().getCustomerId();
            if (found.get().getPassword().equals(request.getPassword())) {
                logIn(id);
                return "redirect:/profile/" + id;
            }
        }
        return "redirect:/sign-in";
    }

    @GetMapping("/sign-up")
    public String getSignUpForm(Model model) {
        model.addAttribute("customerToCreate", new CustomerCreateDto());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String register(CustomerCreateDto customerToCreate) {
        customerDtoService.create(customerToCreate);
        return "redirect:/sign-in";
    }

    @GetMapping("/product-create")
    public String createProductForm(Model model) {
        if (!isLoggedIn()) {
            return "redirect:/sign-in";
        }
        ProductCreateDto productToCreate = new ProductCreateDto();
        model.addAttribute("productToCreate", productToCreate);
        return "product-create";
    }

    @PostMapping("/product-create")
    public String createProduct(ProductCreateDto product) {

        productDtoService.createProduct(product);
        return "redirect:/index";
    }

    @GetMapping("/product-delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        if (!isLoggedIn()) {
            return "redirect:/sign-in";
        }
        productRepository.deleteById(id);
        return "redirect:/index";
    }

    @GetMapping("/shipper-create")
    public String createShipperForm(Model model) {
        if (!isLoggedIn()) {
            return "redirect:/sign-in";
        }
        ShipperCreateDto shipperToCreate = new ShipperCreateDto();
        model.addAttribute("shipperToCreate", shipperToCreate);
        return "/shipper-create";
    }

    @PostMapping("/shipper-create")
    public String createShipper(ShipperCreateDto shipper) {

        shipperDtoService.createShipper(shipper);
        return "redirect:/shippers";
    }

    @GetMapping("/shipper-delete/{id}")
    public String deleteShipper(@PathVariable int id) {
        if (!isLoggedIn()) {
            return "redirect:/sign-in";
        }
        shipperRepository.deleteById(id);
        return "redirect:/shippers";
    }


}
