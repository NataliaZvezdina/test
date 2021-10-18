package by.itstep.zvezdina.orders.dto.shipper;

import java.util.Objects;

public class ShipperCreateDto {

    private String name;

    public ShipperCreateDto() {}

    public ShipperCreateDto(String name) {
        this.name = name;
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
        ShipperCreateDto that = (ShipperCreateDto) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ShipperCreateDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
