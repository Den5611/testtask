package testtask.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import testtask.parking.enums.VehicleType;

import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class VehicleResponseDto {

    private UUID id;

    private VehicleType vehicleType;

    private String plateNumber;

    private LocalTime arriveTime;

    private LocalTime departureTime;
}
