package mykytka235.ms.report.integration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ms.myaccount")
public class MyAccountProperties extends RestTemplateCommunicationProperties {

    private Path path;

    @Getter
    @Setter
    public static class Path {
        private String referralBonusesByMultipleCriteria;
        private String referralInvitedCustomers;
    }

}
