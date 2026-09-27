package testtask.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import testtask.parking.entity.ParkingSpot;
import testtask.parking.enums.SpotAvailability;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, UUID> {
    List<ParkingSpot> findAllByAvailability(SpotAvailability spotAvailability);
}
