package testtask.parking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import testtask.parking.dto.VehicleDto;
import testtask.parking.dto.VehicleResponseDto;
import testtask.parking.service.VehicleService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/vehicle")
@Tag(name = "Vehicle", description = "Vehicle management")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Operation(summary = "Create vehicle")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Vehicle successfully created"),
            @ApiResponse(responseCode = "409", description = "Vehicle already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid vehicle data")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@Valid @RequestBody VehicleDto dto) {
        vehicleService.add(dto);
    }

    @Operation(summary = "Update vehicle")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicle successfully updated"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found"),
            @ApiResponse(responseCode = "400", description = "Invalid vehicle data")
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody VehicleDto dto) {
        vehicleService.update(dto);
    }

    @Operation(summary = "Get vehicle by plate number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicle found"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @GetMapping("/{plateNumber}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleResponseDto get(
            @Parameter(description = "Vehicle plate number")
            @PathVariable String plateNumber) {

        return vehicleService.get(plateNumber);
    }

    @Operation(summary = "Delete vehicle")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Vehicle successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @DeleteMapping("/{plateNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "Vehicle plate number")
            @PathVariable String plateNumber) {

        vehicleService.delete(plateNumber);
    }

    @Operation(summary = "Get all vehicles")
    @ApiResponse(responseCode = "200", description = "Vehicles successfully retrieved")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleResponseDto> getAll() {
        return vehicleService.getAll();
    }
}