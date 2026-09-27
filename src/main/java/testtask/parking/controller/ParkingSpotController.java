package testtask.parking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import testtask.parking.dto.ParkingSpotDto;
import testtask.parking.service.ParkingSpotService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parking-spot")
@Tag(name = "Parking Spot", description = "Parking spot management")
public class ParkingSpotController {

    private final ParkingSpotService spotService;

    @Operation(summary = "Create parking spot")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Parking spot successfully created"),
            @ApiResponse(responseCode = "409", description = "Parking spot already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid parking spot data")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@Valid @RequestBody ParkingSpotDto dto) {
        spotService.add(dto);
    }

    @Operation(summary = "Update parking spot")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parking spot successfully updated"),
            @ApiResponse(responseCode = "404", description = "Parking spot not found"),
            @ApiResponse(responseCode = "400", description = "Invalid parking spot data")
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody ParkingSpotDto dto) {
        spotService.update(dto);
    }

    @Operation(summary = "Get parking spot by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Parking spot found"),
            @ApiResponse(responseCode = "404", description = "Parking spot not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ParkingSpotDto get(
            @Parameter(description = "Parking spot UUID")
            @PathVariable UUID id) {

        return spotService.get(id);
    }

    @Operation(summary = "Delete parking spot")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Parking spot successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Parking spot not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "Parking spot UUID")
            @PathVariable UUID id) {

        spotService.delete(id);
    }

    @Operation(summary = "Get all free parking spots")
    @ApiResponse(
            responseCode = "200",
            description = "Free parking spots successfully retrieved"
    )
    @GetMapping("/free")
    @ResponseStatus(HttpStatus.OK)
    public List<ParkingSpotDto> getAllFreeSpots() {
        return spotService.getAllFreeSpots();
    }

    @Operation(summary = "Get all parking spots")
    @ApiResponse(
            responseCode = "200",
            description = "All parking spots successfully retrieved"
    )
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ParkingSpotDto> getAll() {
        return spotService.getAll();
    }
}