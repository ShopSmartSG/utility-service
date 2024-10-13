package sg.edu.nus.iss.utility_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Service
public class AlertService {

    private final SnsClient snsClient;
    private final String topicArn;

    @Autowired
    public AlertService(SnsClient snsClient, @Value("${sns.topic.arn}") String topicArn) {
        this.snsClient = snsClient;
        this.topicArn = topicArn;
    }

    public void sendAlert(String message) {
        PublishRequest request = PublishRequest.builder()
                .topicArn(topicArn)
                .message(message)
                .build();

        try {
            PublishResponse response = snsClient.publish(request);
            System.out.println("Alert sent successfully. Message ID: " + response.messageId());
        } catch (SdkException e) {
            System.err.println("Error sending alert: " + e.getMessage());
        }
    }
}

