package by.itstep.zvezdina.orders.controller;

import by.itstep.zvezdina.orders.entity.Product;
import by.itstep.zvezdina.orders.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MyController {

    private ProductRepository productRepository = new ProductRepository();

    @GetMapping ("/index")
    public String getIndex(Model model) {
        try {
            List<Product> foundProducts = productRepository.findAll();
            System.out.println("FOUND");
            System.out.println(foundProducts);

            model.addAttribute("foundProducts", foundProducts);
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

}
