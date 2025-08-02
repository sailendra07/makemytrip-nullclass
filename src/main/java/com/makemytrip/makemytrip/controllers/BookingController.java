package com.makemytrip.makemytrip.controllers;

import com.makemytrip.makemytrip.models.Users;
import com.makemytrip.makemytrip.repositories.UserRepository;
import com.makemytrip.makemytrip.services.BookingService;
import com.makemytrip.makemytrip.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "*") // Allows requests from your frontend
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EmailService emailService; // Injected to handle email sending

    @Autowired
    private UserRepository userRepository; // Injected to find user details

    /**
     * Handles flight booking requests.
     * After a successful booking, it triggers a confirmation email.
     */
    @PostMapping("/flight")
    public ResponseEntity<Users.Booking> bookFlight(@RequestParam String userId, @RequestParam String flightId, @RequestParam int seats, @RequestParam double price) {
        // Create the booking using the service
        Users.Booking newBooking = bookingService.bookFlight(userId, flightId, seats, price);

        // Find the user who made the booking to get their email address
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            String userEmail = userOptional.get().getEmail();
            // Send the confirmation email
            emailService.sendBookingConfirmation(userEmail, newBooking.getBookingId(), "Flight", newBooking.getTotalPrice());
        } else {
            // Log an error if the user isn't found, as the email can't be sent
            System.err.println("Error: Could not find user with ID " + userId + " to send booking confirmation.");
        }

        return ResponseEntity.ok(newBooking);
    }

    /**
     * Handles hotel booking requests.
     * After a successful booking, it triggers a confirmation email.
     */
    @PostMapping("/hotel")
    public ResponseEntity<Users.Booking> bookhotel(@RequestParam String userId, @RequestParam String hotelId, @RequestParam int rooms, @RequestParam double price) {
        // Create the booking using the service
        Users.Booking newBooking = bookingService.bookhotel(userId, hotelId, rooms, price);

        // Find the user who made the booking to get their email address
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            String userEmail = userOptional.get().getEmail();
            // Send the confirmation email
            emailService.sendBookingConfirmation(userEmail, newBooking.getBookingId(), "Hotel", newBooking.getTotalPrice());
        } else {
            // Log an error if the user isn't found
            System.err.println("Error: Could not find user with ID " + userId + " to send booking confirmation.");
        }

        return ResponseEntity.ok(newBooking);
    }
}
