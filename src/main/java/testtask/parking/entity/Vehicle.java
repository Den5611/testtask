package testtask.parking.entity;

import jakarta.persistence.*;
import lombok.Data;
import testtask.parking.enums.VehicleType;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Data
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    private LocalTime arriveTime;

    private LocalTime departureTime;

    private String plateNumber;
}
