package testtask.parking.service;

import testtask.parking.dto.ParkingSessionDto;

import java.util.UUID;

public interface ParkingSessionService {

    void arrive(UUID vehicleId, UUID parkingSpotId);

    ParkingSessionDto departure(UUID vehicleId);
}