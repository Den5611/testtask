package testtask.parking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testtask.parking.dto.ParkingSessionDto;
import testtask.parking.entity.ParkingSession;
import testtask.parking.entity.ParkingSpot;
import testtask.parking.entity.Vehicle;
import testtask.parking.enums.SpotAvailability;
import testtask.parking.enums.VehicleType;
import testtask.parking.exception.CustomNotFoundException;
import testtask.parking.repository.ParkingSessionRepository;
import testtask.parking.repository.ParkingSpotRepository;
import testtask.parking.repository.VehicleRepository;
import testtask.parking.service.ParkingSessionService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParkingSessionServiceImpl implements ParkingSessionService {

    private final ParkingSessionRepository sessionRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingSpotRepository spotRepository;

    @Transactional
    public void arrive(UUID vehicleId, UUID parkingSpotId) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new CustomNotFoundException("Vehicle was not found"));

        ParkingSpot parkingSpot = spotRepository.findById(parkingSpotId)
                .orElseThrow(() -> new CustomNotFoundException("Parking spot was not found"));

        if (parkingSpot.getAvailability() != SpotAvailability.FREE) {
            throw new CustomNotFoundException("Parking spot is not free");
        }

        if (parkingSpot.getVehicleType() != vehicle.getVehicleType()) {
            throw new CustomNotFoundException("Vehicle type is not compatible with this parking spot");
        }

        ParkingSession parkingSession = new ParkingSession();

        parkingSession.setVehicle(vehicle);
        parkingSession.setParkingSpot(parkingSpot);
        parkingSession.setArriveTime(LocalDateTime.now());

        parkingSpot.setAvailability(SpotAvailability.OCCUPIED);

        sessionRepository.save(parkingSession);
        spotRepository.save(parkingSpot);
    }

    @Transactional
    public ParkingSessionDto departure(UUID vehicleId) {

        ParkingSession parkingSession = sessionRepository.findByVehicleIdAndDepartureTimeIsNull(vehicleId)
                .orElseThrow(() -> new CustomNotFoundException("Active parking session was not found"));

        LocalDateTime departureTime = LocalDateTime.now();

        parkingSession.setDepartureTime(departureTime);

        long hours = ChronoUnit.HOURS.between(
                parkingSession.getArriveTime(),
                departureTime
        );

        if (hours == 0) {
            hours = 1;
        }

        long price = hours * getPrice(parkingSession.getVehicle().getVehicleType());

        parkingSession.setPrice(price);

        ParkingSpot parkingSpot = parkingSession.getParkingSpot();
        parkingSpot.setAvailability(SpotAvailability.FREE);

        sessionRepository.save(parkingSession);
        spotRepository.save(parkingSpot);

        return ParkingSessionDto.builder()
                .id(parkingSession.getId())
                .vehicle(parkingSession.getVehicle())
                .parkingSpot(parkingSession.getParkingSpot())
                .arriveTime(parkingSession.getArriveTime())
                .departureTime(parkingSession.getDepartureTime())
                .price(parkingSession.getPrice())
                .build();
    }

    private long getPrice(VehicleType vehicleType) {

        return switch (vehicleType) {
            case CAR -> 100;
            case MOTORCYCLE -> 50;
            case TRUCK -> 150;
        };
    }
}