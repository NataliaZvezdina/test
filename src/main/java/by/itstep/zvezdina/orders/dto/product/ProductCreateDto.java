package by.itstep.zvezdina.orders.dto.product;

import java.util.Objects;

public class ProductCreateDto {

    private String name;
    private int quantityInStock;
    private double unitPrice;

    public ProductCreateDto() {}

    public ProductCreateDto(String name, int quantityInStock, double unitPrice) {
        this.name = name;
        this.quantityInStock = quantityInStock;
        this.unitPrice = unitPrice;
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
        ProductCreateDto that = (ProductCreateDto) o;
        return quantityInStock == that.quantityInStock && Double.compare(that.unitPrice, unitPrice) == 0 && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantityInStock, unitPrice);
    }

    @Override
    public String toString() {
        return "ProductCreateDto{" +
                "name='" + name + '\'' +
                ", quantityInStock=" + quantityInStock +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
