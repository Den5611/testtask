package testtask.parking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import testtask.parking.dto.ParkingSessionDto;
import testtask.parking.service.ParkingSessionService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parking-session")
@Tag(name = "Parking Session", description = "Parking session management")
public class ParkingSessionController {

    private final ParkingSessionService sessionService;

    @Operation(
            summary = "Vehicle arrival",
            description = "Creates a parking session for a vehicle and occupies the parking spot"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Parking session successfully created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parking spot is occupied or vehicle type is incompatible"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Vehicle or parking spot not found"
            )
    })
    @PostMapping("/arrive")
    @ResponseStatus(HttpStatus.CREATED)
    public void arrive(
            @Parameter(description = "Vehicle UUID")
            @RequestParam UUID vehicleId,

            @Parameter(description = "Parking spot UUID")
            @RequestParam UUID parkingSpotId) {

        sessionService.arrive(vehicleId, parkingSpotId);
    }

    @Operation(
            summary = "Vehicle departure",
            description = "Completes the parking session, calculates the price and frees the parking spot"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Vehicle successfully departed"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Active parking session not found"
            )
    })
    @PutMapping("/departure/{vehicleId}")
    @ResponseStatus(HttpStatus.OK)
    public ParkingSessionDto departure(
            @Parameter(description = "Vehicle UUID")
            @PathVariable UUID vehicleId) {

        return sessionService.departure(vehicleId);
    }
}