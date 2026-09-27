package testtask.parking.service;

import testtask.parking.dto.VehicleDto;
import testtask.parking.dto.VehicleResponseDto;

import java.util.List;

public interface VehicleService {

    void add(VehicleDto dto);

    void update(VehicleDto dto);

    VehicleResponseDto get(String plateNumber);

    void delete(String plateNumber);

    List<VehicleResponseDto> getAll();
}
