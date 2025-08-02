package com.makemytrip.makemytrip.controllers;

import com.makemytrip.makemytrip.models.Flight;
import com.makemytrip.makemytrip.models.Hotel;
import com.makemytrip.makemytrip.repositories.FlightRepository;
import com.makemytrip.makemytrip.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*") // Allows requests from any frontend
public class RootController {

    // Spring will automatically provide an instance of HotelRepository
    @Autowired
    private HotelRepository hotelRepository;

    // Spring will automatically provide an instance of FlightRepository
    @Autowired
    private FlightRepository flightRepository;

    /**
     * A simple endpoint to check if the application is running.
     * Accessible at the root URL (e.g., https://your-app.onrender.com/)
     * @return A success message.
     */
    @GetMapping("/")
    public String home() {
        return "✅ It's running on port 8080!";
    }

    /**
     * THIS IS THE FIX: Handles GET requests to the /hotel endpoint.
     * It fetches all hotels from the database and returns them as a JSON list.
     * @return A ResponseEntity containing the list of all hotels.
     */
    @GetMapping("/hotel")
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return ResponseEntity.ok(hotels);
    }

    /**
     * Handles GET requests to the /flight endpoint.
     * It fetches all flights from the database and returns them as a JSON list.
     * @return A ResponseEntity containing the list of all flights.
     */
    @GetMapping("/flight")
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        return ResponseEntity.ok(flights);
    }
}
