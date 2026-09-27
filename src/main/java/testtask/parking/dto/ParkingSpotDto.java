package testtask.parking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import testtask.parking.enums.SpotAvailability;
import testtask.parking.enums.VehicleType;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ParkingSpotDto {

    private UUID id;

    @NotNull
    private SpotAvailability availability;

    @NotNull
    private VehicleType vehicleType;
}
