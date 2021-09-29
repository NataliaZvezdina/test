package by.itstep.zvezdina.orders.entity;

import java.util.Objects;

public class Product {

    private int productId;
    private String name;
    private int quantityInStock;
    private double unitPrice;

    public Product() {

    }

    public Product(int productId, String name, int quantityInStock, double unitPrice) {
        this.productId = productId;
        this.name = name;
        this.quantityInStock = quantityInStock;
        this.unitPrice = unitPrice;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Product product = new Product();

        public Builder setProductId(int productId) {
            product.productId = productId;
            return this;
        }

        public Builder setName(String name) {
            product.name = name;
            return this;
        }

        public Builder setQuantityInStock(int quantityInStock) {
            product.quantityInStock = quantityInStock;
            return this;
        }

        public Builder setUnitPrice(double unitPrice) {
            product.unitPrice = unitPrice;
            return this;
        }

        public Product build() {
            return product;
        }
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId && quantityInStock == product.quantityInStock && Double.compare(product.unitPrice, unitPrice) == 0 && name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, quantityInStock, unitPrice);
    }

    @Override
    public String toString() {
        return "entity.Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", quantityInStock=" + quantityInStock +
                ", unitPrice=" + unitPrice +
                '}';
    }
}

