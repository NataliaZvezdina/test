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

    @GetMapping("/customer-delete/{id}")
    public String deleteCustomer(@PathVariable int id) {
        customerRepository.deleteById(id);
        return "redirect:/customers";
    }

    @GetMapping("/customer-update/{id}")
    public String getUpdateCustomerForm(Model model, @PathVariable int id) {
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
        return "redirect:/index";
    }

    @GetMapping("/product-create")
    public String createProductForm(Model model) {
        ProductCreateDto productToCreate = new ProductCreateDto();
        model.addAttribute("productToCreate", productToCreate);
        return "product-create";
    }

    @PostMapping("/product-create")
    public String createProduct(ProductCreateDto product) {
        productDtoService.createProduct(product);
        return "redirect:/index";
    }

    @GetMapping("/shipper-create")
    public String createShipperForm(Model model) {
        ShipperCreateDto shipperToCreate = new ShipperCreateDto();
        model.addAttribute("shipperToCreate", shipperToCreate);
        return "/shipper-create";
    }

    @PostMapping("shipper-create")
    public String createShipper(ShipperCreateDto shipper) {
        shipperDtoService.createShipper(shipper);
        return "redirect:/shippers";
    }
}
