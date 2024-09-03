package mykytka235.ms.report.integration.rest;

import mykytka235.ms.report.integration.uri.NotificationUriBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
public class NotificationCommunicationServiceImpl extends RestTemplateCommunicationService implements NotificationCommunicationService {

    private final NotificationUriBuilder notificationUriBuilder;

    public NotificationCommunicationServiceImpl(RestTemplate notificationRestTemplate, NotificationUriBuilder notificationUriBuilder) {
        super(notificationRestTemplate);
        this.notificationUriBuilder = notificationUriBuilder;
    }

    @Override
    public void sendReferralBonusesReport(String from, String to, String locale, String fileName, byte[] bytes) {
        URI uri = notificationUriBuilder.getSendReportUri(from, to, locale, fileName);
        performRequest(uri, HttpMethod.POST, new HttpEntity<>(bytes), Void.class,
                () -> log.info("Request to be-notification to get referral bonuses info. Email to: {}", to),
                () -> log.info("Request to be-notification to get referral bonuses processed. Email to: {} ", to),
                e -> log.error("No response from be-notification on get all referral bonuses. Email to: {} , error: {}", to, e.getMessage())
        );
    }
}
