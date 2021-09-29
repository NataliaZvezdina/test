package by.itstep.zvezdina.orders.entity;

import java.util.Objects;

public class OrderStatus {

    private int orderStatusId;
    private String name;

    public OrderStatus() {

    }

    public OrderStatus(int orderStatusId, String name) {
        this.orderStatusId = orderStatusId;
        this.name = name;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private OrderStatus orderStatus = new OrderStatus();

        public Builder setOrderStatusId(int orderStatusId) {
            orderStatus.orderStatusId = orderStatusId;
            return this;
        }

        public Builder setName(String name) {
            orderStatus.name = name;
            return this;
        }

        public OrderStatus build() {
            return orderStatus;
        }
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatus that = (OrderStatus) o;
        return orderStatusId == that.orderStatusId && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderStatusId, name);
    }

    @Override
    public String toString() {
        return "entity.OrderStatus{" +
                "orderStatusId=" + orderStatusId +
                ", name='" + name + '\'' +
                '}';
    }
}
