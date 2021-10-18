package by.itstep.zvezdina.orders.service;

import by.itstep.zvezdina.orders.dto.product.ProductCreateDto;
import by.itstep.zvezdina.orders.entity.Product;
import by.itstep.zvezdina.orders.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDtoService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductDtoService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductCreateDto product) {
        Product productToCreate = Product.getBuilder()
                .setName(product.getName())
                .setQuantityInStock(product.getQuantityInStock())
                .setUnitPrice(product.getUnitPrice())
                .build();
        productRepository.create(productToCreate);
    }
}
