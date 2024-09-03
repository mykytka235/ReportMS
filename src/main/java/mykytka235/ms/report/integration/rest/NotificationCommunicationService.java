package mykytka235.ms.report.integration.rest;

public interface NotificationCommunicationService {

    void sendReferralBonusesReport(String from, String to, String locale, String fileName, byte[] bytes);

}
