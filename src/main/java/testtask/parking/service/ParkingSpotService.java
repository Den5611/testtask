package testtask.parking.service;

import testtask.parking.dto.ParkingSpotDto;

import java.util.List;
import java.util.UUID;

public interface ParkingSpotService {

    void add(ParkingSpotDto dto);

    void update(ParkingSpotDto dto);

    ParkingSpotDto get(UUID id);

    void delete(UUID id);

    List<ParkingSpotDto> getAllFreeSpots();

    List<ParkingSpotDto> getAll();
}
