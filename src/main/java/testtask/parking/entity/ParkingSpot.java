package testtask.parking.entity;

import jakarta.persistence.*;
import lombok.Data;
import testtask.parking.enums.SpotAvailability;
import testtask.parking.enums.VehicleType;

import java.util.UUID;

@Entity
@Data
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private SpotAvailability availability;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
}
