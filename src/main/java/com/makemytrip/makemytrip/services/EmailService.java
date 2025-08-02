package com.makemytrip.makemytrip.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends a booking confirmation email.
     * @param toEmail The recipient's email address.
     * @param bookingId The unique ID of the booking.
     * @param bookingType "Flight" or "Hotel".
     * @param price The total price of the booking.
     */
    public void sendBookingConfirmation(String toEmail, String bookingId, String bookingType, double price) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // true indicates multipart message
            helper.setFrom("cuitepiee4@gmail.com"); // Your sending email address
            helper.setTo(toEmail);
            helper.setSubject("Your " + bookingType + " Booking Confirmation - #" + bookingId);

            // Generate the HTML content for the email
            String htmlContent = createHtmlEmailBody(bookingId, bookingType, price);
            helper.setText(htmlContent, true); // true indicates HTML email

            // You can add an e-ticket attachment here in the future if needed
            // FileSystemResource file = new FileSystemResource(new File("path/to/your/eticket.pdf"));
            // helper.addAttachment("E-Ticket.pdf", file);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            // It's good practice to log this error
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    /**
     * Creates a mobile-friendly HTML email body.
     * @return A string containing the HTML content.
     */
    private String createHtmlEmailBody(String bookingId, String bookingType, double price) {
        // This is a basic template. For production, you could use a more robust
        // templating engine like Thymeleaf or FreeMarker.
        return "<!DOCTYPE html>"
                + "<html>"
                + "<head><style>body{font-family: Arial, sans-serif; line-height: 1.6;} .container{width: 80%; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px;} .header{font-size: 24px; font-weight: bold; color: #E53935;} .footer{font-size: 12px; color: #777; margin-top: 20px;}</style></head>"
                + "<body>"
                + "<div class='container'>"
                + "<div class='header'>Booking Confirmed!</div>"
                + "<p>Dear Customer,</p>"
                + "<p>Thank you for booking with MakeMyTour. Your " + bookingType + " booking is confirmed.</p>"
                + "<p><strong>Booking ID:</strong> " + bookingId + "</p>"
                + "<p><strong>Total Price:</strong> ₹" + String.format("%,.2f", price) + "</p>"
                + "<hr>"
                + "<h3>Cancellation Policy</h3>"
                + "<p>Please refer to the terms and conditions on our website for cancellation details. Fees may apply based on the time of cancellation.</p>"
                + "<div class='footer'>"
                + "<p>For support, contact us at support@makemytour.com or call +91-123-456-7890.</p>"
                + "<p>&copy; 2025 MakeMyTour. All rights reserved.</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }
}
