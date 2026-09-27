package testtask.parking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import testtask.parking.enums.VehicleType;

import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class VehicleDto {

    private UUID id;

    @NotNull
    private VehicleType vehicleType;

    private LocalTime arriveTime;

    private LocalTime departureTime;

    @NotBlank
    private String plateNumber;
}
