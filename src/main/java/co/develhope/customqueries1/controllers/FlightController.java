package co.develhope.customqueries1.controllers;
import co.develhope.customqueries1.entities.Flight;
import co.develhope.customqueries1.entities.FlightStatus;
import co.develhope.customqueries1.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@RestController
@RequestMapping("/flights")
public class FlightController {
    @Autowired
    private FlightRepository flightRepository;
    String generateRandomString(){
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
    @GetMapping("/get-all-flights")
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
    @PostMapping("/create")
    public ResponseEntity<String> createFlights() {
        List<Flight> flights = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            String description = generateRandomString();
            String fromAirport = generateRandomString();
            String toAirport = generateRandomString();
            flights.add(new Flight(0, description, fromAirport, toAirport, FlightStatus.ONTIME));
        }
        flightRepository.saveAll(flights);
        return ResponseEntity.status(HttpStatus.CREATED).body("50 Flights have been created successfully.");
    }

}