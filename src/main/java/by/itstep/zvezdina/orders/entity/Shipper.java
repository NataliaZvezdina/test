package by.itstep.zvezdina.orders.entity;

import java.util.Objects;

public class Shipper { // ENTITY (-> table shippers)

    private int shipperId;
    private String name;

    // getters + setters + constructors + toString + equals and hashcode


    public Shipper() {
    }

    public Shipper(int shipperId, String name) {
        this.shipperId = shipperId;
        this.name = name;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Shipper shipper = new Shipper();

        public Builder setShipperId(int shipperId) {
            shipper.shipperId = shipperId;
            return this;
        }

        public Builder setName(String name) {
            shipper.name = name;
            return this;
        }

        public Shipper build() {
            return shipper;
        }
    }

    public int getShipperId() {
        return shipperId;
    }

    public void setShipperId(int shipperId) {
        this.shipperId = shipperId;
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
        Shipper shipper = (Shipper) o;
        return shipperId == shipper.shipperId && Objects.equals(name, shipper.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipperId, name);
    }

    @Override
    public String toString() {
        return "entity.Shipper{" +
                "shipperId=" + shipperId +
                ", name='" + name + '\'' +
                '}';
    }
}
