package by.itstep.zvezdina.orders.dto;

import java.util.Objects;

public class ItemViewDto {


    private String productName;
    private int quantity;
    private double unitPrice;
    private double totalPrice;

    public ItemViewDto() {}

    public ItemViewDto(String productName, int quantity, double unitPrice, double totalPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemViewDto that = (ItemViewDto) o;
        return quantity == that.quantity && Double.compare(that.unitPrice, unitPrice) == 0 && Double.compare(that.totalPrice, totalPrice) == 0 && productName.equals(that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, quantity, unitPrice, totalPrice);
    }

    @Override
    public String toString() {
        return "ItemViewDto{" +
                "productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
