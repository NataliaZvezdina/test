package by.itstep.zvezdina.orders.service;

import by.itstep.zvezdina.orders.dto.shipper.ShipperCreateDto;
import by.itstep.zvezdina.orders.entity.Shipper;
import by.itstep.zvezdina.orders.repository.ShipperRepository;
import org.springframework.stereotype.Service;

@Service
public class ShipperDtoService {

    private final ShipperRepository shipperRepository;

    public ShipperDtoService(ShipperRepository shipperRepository) {
        this.shipperRepository = shipperRepository;
    }

    public void createShipper(ShipperCreateDto shipper) {
        Shipper shipperToCreate = Shipper.getBuilder()
                .setName(shipper.getName())
                .build();
        shipperRepository.create(shipperToCreate);
    }
}
