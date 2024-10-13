package sg.edu.nus.iss.utility_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.nus.iss.utility_service.service.AlertService;

import java.util.Map;

@RestController
@Tag(name = "Webhook", description = "Receive alert from monitoring system")
@RequestMapping("/api/alerts")
public class WebhookController {

    private final AlertService alertService;

    Logger logger = LoggerFactory.getLogger(WebhookController.class);

    public WebhookController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping(value = "/webhook", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Receive alert from monitoring system")
    public ResponseEntity<String> receiveAlert(@RequestBody Map<String, Object> payload) {
        // Extract relevant information
        String alertName = (String) payload.getOrDefault("alert_name", "");
        String alertType = (String) payload.getOrDefault("alert_type", "");
        String alertDescription = (String) payload.getOrDefault("alert_description", "");
        String alertSeverity = (String) payload.getOrDefault("alert_severity", "");
        String alertStatus = (String) payload.getOrDefault("alert_status", "");
        String alertTimestamp = (String) payload.getOrDefault("alert_timestamp", "");
        String alertSource = (String) payload.getOrDefault("alert_source", "");
        String alertTags = (String) payload.getOrDefault("alert_tags", "");
        String alertDetails = (String) payload.getOrDefault("alert_details", "");

        String message = "Alert Name: " + alertName +
                "\nType: " + alertType +
                "\nDescription: " + alertDescription +
                "\nSeverity: " + alertSeverity +
                "\nStatus: " + alertStatus +
                "\nTimestamp: " + alertTimestamp +
                "\nSource: " + alertSource +
                "\nTags: " + alertTags +
                "\nDetails: " + alertDetails;

        // Log additional details
        logger.info("Received alert details:");
        logger.info("Type: {}", alertType);
        logger.info("Severity: {}", alertSeverity);
        logger.info("Status: {}", alertStatus);
        logger.info("Timestamp: {}", alertTimestamp);
        logger.info("Source: {}", alertSource);
        logger.info("Tags: {}", alertTags);
        logger.info("Details: {}", alertDetails);

        alertService.sendAlert(message);

        return ResponseEntity.ok("Alert received and sent to SNS");
    }
}
