package testtask.parking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testtask.parking.dto.VehicleDto;
import testtask.parking.dto.VehicleResponseDto;
import testtask.parking.entity.Vehicle;
import testtask.parking.exception.AlreadyExistsException;
import testtask.parking.exception.CustomNotFoundException;
import testtask.parking.repository.VehicleRepository;
import testtask.parking.service.VehicleService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Transactional
    public void add(VehicleDto dto) {

        if (vehicleRepository.findByPlateNumber(dto.getPlateNumber()).isPresent()) {
            throw new AlreadyExistsException("This vehicle already exists");
        }

        Vehicle vehicle = new Vehicle();

        vehicle.setVehicleType(dto.getVehicleType());
        vehicle.setPlateNumber(dto.getPlateNumber());

        vehicleRepository.save(vehicle);
    }

    @Transactional
    public void update(VehicleDto dto) {

        Vehicle vehicle = vehicleRepository.findByPlateNumber(dto.getPlateNumber())
                .orElseThrow(() -> new CustomNotFoundException("There's no such vehicle"));

        vehicle.setVehicleType(dto.getVehicleType());
        vehicle.setPlateNumber(dto.getPlateNumber());

        vehicleRepository.save(vehicle);
    }

    @Transactional(readOnly = true)
    public VehicleResponseDto get(String plateNumber) {

        Vehicle vehicle = vehicleRepository.findByPlateNumber(plateNumber)
                .orElseThrow(() -> new CustomNotFoundException("There's no such vehicle"));

        return VehicleResponseDto
                .builder()
                .id(vehicle.getId())
                .vehicleType(vehicle.getVehicleType())
                .plateNumber(vehicle.getPlateNumber())
                .build();
    }

    @Transactional
    public void delete(String plateNumber) {

        Vehicle vehicle = vehicleRepository.findByPlateNumber(plateNumber)
                .orElseThrow(() -> new CustomNotFoundException("There's no such vehicle"));

        vehicleRepository.delete(vehicle);
    }

    @Transactional(readOnly = true)
    public List<VehicleResponseDto> getAll() {

        List<Vehicle> vehicleList = vehicleRepository.findAll();

        List<VehicleResponseDto> vehicleResponseDtos = new ArrayList<>();

        for (Vehicle vehicle : vehicleList) {

            VehicleResponseDto vehicleResponseDto = VehicleResponseDto.builder()
                    .id(vehicle.getId())
                    .vehicleType(vehicle.getVehicleType())
                    .plateNumber(vehicle.getPlateNumber())
                    .arriveTime(vehicle.getArriveTime())
                    .departureTime(vehicle.getDepartureTime())
                    .build();

            vehicleResponseDtos.add(vehicleResponseDto);
        }

        return vehicleResponseDtos;
    }
}
