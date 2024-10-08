package sg.edu.nus.iss.utility_service.controller;

import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.iss.utility_service.service.LocationService;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/coordinates")
    public ResponseEntity<?> getCoordinates(@RequestParam String pincode) {
        try {
            LatLng coordinates = locationService.getCoordinatesFromPincode(pincode);
            return new ResponseEntity<>(coordinates, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}