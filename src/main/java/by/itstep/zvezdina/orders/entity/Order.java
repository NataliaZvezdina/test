package by.itstep.zvezdina.orders.entity;

import java.sql.Date;
import java.util.Objects;

public class Order {

    private int orderId;
    private int customerId;
    private Date orderDate;
    private int statusId;
    private String comments;
    private Date shippedDate;
    private int shipperId;

    public Order() {

    }

    public Order(int orderId, int customerId, Date orderDate, int statusId, String comments, Date shippedDate,
                 int shipperId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.statusId = statusId;
        this.comments = comments;
        this.shippedDate = shippedDate;
        this.shipperId = shipperId;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Order order = new Order();

        public Builder setOrderId(int orderId) {
            order.orderId = orderId;
            return this;
        }

        public Builder setCustomerId(int customerId) {
            order.customerId = customerId;
            return this;
        }

        public Builder setOrderDate(Date orderDate) {
            order.orderDate = orderDate;
            return this;
        }

        public Builder setStatusId(int statusId) {
            order.statusId = statusId;
            return this;
        }

        public Builder setComments(String comments) {
            order.comments = comments;
            return this;
        }

        public Builder setShippedDate(Date shippedDate) {
            order.shippedDate = shippedDate;
            return this;
        }

        public Builder setShipperId(int shipperId) {
            order.shipperId = shipperId;
            return this;
        }

        public Order build() {
            return order;
        }

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public int getShipperId() {
        return shipperId;
    }

    public void setShipperId(int shipperId) {
        this.shipperId = shipperId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && customerId == order.customerId && statusId == order.statusId && shipperId == order.shipperId && orderDate.equals(order.orderDate) && comments.equals(order.comments) && shippedDate.equals(order.shippedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customerId, orderDate, statusId, comments, shippedDate, shipperId);
    }

    @Override
    public String toString() {
        return "entity.Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                ", statusId=" + statusId +
                ", comments='" + comments + '\'' +
                ", shippedDate=" + shippedDate +
                ", shipperId=" + shipperId +
                '}';
    }
}
