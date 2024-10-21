package sg.edu.nus.iss.utility_service.controller;

import com.google.maps.model.LatLng;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.iss.utility_service.service.LocationService;

@RestController
@RequestMapping("/location")
@Tag(name = "Location", description = "Get location coordinates via APIs")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/coordinates")
    @Operation(summary = "Get coordinates from pincode")
    public ResponseEntity<?> getCoordinates(@RequestParam String pincode) {
        try {
            LatLng coordinates = locationService.getCoordinatesFromPincode(pincode);
            return new ResponseEntity<>(coordinates, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}