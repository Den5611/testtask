package testtask.parking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testtask.parking.dto.ParkingSpotDto;
import testtask.parking.entity.ParkingSpot;
import testtask.parking.enums.SpotAvailability;
import testtask.parking.exception.AlreadyExistsException;
import testtask.parking.exception.CustomNotFoundException;
import testtask.parking.repository.ParkingSpotRepository;
import testtask.parking.service.ParkingSpotService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private final ParkingSpotRepository spotRepository;

    @Transactional
    public void add(ParkingSpotDto dto) {

        if (spotRepository.findById(dto.getId()).isPresent()) {
            throw new AlreadyExistsException("Tjis spot already exists");
        }

        ParkingSpot parkingSpot = new ParkingSpot();

        parkingSpot.setId(dto.getId());
        parkingSpot.setAvailability(dto.getAvailability());
        parkingSpot.setVehicleType(dto.getVehicleType());

        spotRepository.save(parkingSpot);
    }

    @Transactional
    public void update(ParkingSpotDto dto) {

        ParkingSpot parkingSpot = spotRepository.findById(dto.getId())
                .orElseThrow(() -> new CustomNotFoundException("Parking spot was not found"));

        parkingSpot.setVehicleType(dto.getVehicleType());
        parkingSpot.setAvailability(dto.getAvailability());

        spotRepository.save(parkingSpot);
    }

    @Transactional(readOnly = true)
    public ParkingSpotDto get(UUID id) {

        ParkingSpot parkingSpot = spotRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Parking spot was not found"));

        return ParkingSpotDto.builder()
                .id(parkingSpot.getId())
                .availability(parkingSpot.getAvailability())
                .vehicleType(parkingSpot.getVehicleType())
                .build();
    }

    @Transactional
    public void delete(UUID id) {
        ParkingSpot parkingSpot = spotRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Parking spot was not found"));

        spotRepository.delete(parkingSpot);
    }

    @Transactional(readOnly = true)
    public List<ParkingSpotDto> getAllFreeSpots() {
        List<ParkingSpot> spotList = spotRepository.findAllByAvailability(SpotAvailability.FREE);

        List<ParkingSpotDto> parkingSpotDtos = new ArrayList<>();

        for (ParkingSpot parkingSpot : spotList) {

            ParkingSpotDto parkingSpotDto = ParkingSpotDto.builder()
                    .id(parkingSpot.getId())
                    .availability(parkingSpot.getAvailability())
                    .vehicleType(parkingSpot.getVehicleType())
                    .build();

            parkingSpotDtos.add(parkingSpotDto);
        }

        return parkingSpotDtos;
    }

    @Transactional(readOnly = true)
    public List<ParkingSpotDto> getAll() {

        List<ParkingSpot> spotList = spotRepository.findAll();

        List<ParkingSpotDto> parkingSpotDtos = new ArrayList<>();

        for (ParkingSpot parkingSpot : spotList) {

            ParkingSpotDto parkingSpotDto = ParkingSpotDto.builder()
                    .id(parkingSpot.getId())
                    .availability(parkingSpot.getAvailability())
                    .vehicleType(parkingSpot.getVehicleType())
                    .build();

            parkingSpotDtos.add(parkingSpotDto);
        }

        return parkingSpotDtos;
    }
}
