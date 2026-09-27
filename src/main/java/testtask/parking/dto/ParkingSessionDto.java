package testtask.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import testtask.parking.entity.ParkingSpot;
import testtask.parking.entity.Vehicle;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ParkingSessionDto {

    private UUID id;

    private Vehicle vehicle;

    private ParkingSpot parkingSpot;

    private LocalDateTime arriveTime;

    private LocalDateTime departureTime;

    private Long price;
}