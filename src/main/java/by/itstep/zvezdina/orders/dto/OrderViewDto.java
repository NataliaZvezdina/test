package by.itstep.zvezdina.orders.dto;

import java.sql.Date;
import java.util.Objects;

public class OrderViewDto {

    private int orderId;
    private String customerLastName;
    private Date orderDate;
    private String orderStatus;
    private String comments;
    private Date shippedDate;
    private String shipperName;

    public OrderViewDto() {}

    public OrderViewDto(int orderId, String customerLastName, Date orderDate, String orderStatus, String comments,
                        Date shippedDate, String shipperName) {
        this.orderId = orderId;
        this.customerLastName = customerLastName;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.comments = comments;
        this.shippedDate = shippedDate;
        this.shipperName = shipperName;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderViewDto that = (OrderViewDto) o;
        return orderId == that.orderId && customerLastName.equals(that.customerLastName) && orderDate.equals(that.orderDate) && orderStatus.equals(that.orderStatus) && comments.equals(that.comments) && shippedDate.equals(that.shippedDate) && shipperName.equals(that.shipperName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customerLastName, orderDate, orderStatus, comments, shippedDate, shipperName);
    }

    @Override
    public String toString() {
        return "OrderViewDto{" +
                "orderId=" + orderId +
                ", customerLastName='" + customerLastName + '\'' +
                ", orderDate=" + orderDate +
                ", orderStatus='" + orderStatus + '\'' +
                ", comments='" + comments + '\'' +
                ", shippedDate=" + shippedDate +
                ", shipperName='" + shipperName + '\'' +
                '}';
    }
}
