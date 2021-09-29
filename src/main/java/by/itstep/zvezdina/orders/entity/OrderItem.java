package by.itstep.zvezdina.orders.entity;

import java.util.Objects;

public class OrderItem {

    private int orderId;
    private int productId;
    private int quantity;
    private double unitPrice;

    public OrderItem() {

    }

    public OrderItem(int orderId, int productId, int quantity, double unitPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private OrderItem orderItem = new OrderItem();

        public Builder setOrderId(int orderId) {
            orderItem.orderId = orderId;
            return this;
        }

        public Builder setProductId(int productId) {
            orderItem.productId = productId;
            return this;
        }

        public Builder setQuantity(int quantity) {
            orderItem.quantity = quantity;
            return this;
        }

        public Builder setUnitPrice(double unitPrice) {
            orderItem.unitPrice = unitPrice;
            return this;
        }

        public OrderItem build() {
            return orderItem;
        }
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return orderId == orderItem.orderId && productId == orderItem.productId && quantity == orderItem.quantity && Double.compare(orderItem.unitPrice, unitPrice) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, quantity, unitPrice);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
