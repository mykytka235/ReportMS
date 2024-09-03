package mykytka235.ms.report.integration.uri;

import mykytka235.ms.report.constants.CurrencyIso;
import mykytka235.ms.report.integration.properties.MyAccountProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Service
public class MyAccountUriBuilder {

    private static final String EMAIL = "email";
    private static final String CURRENCY_ISO = "currencyIso";
    private static final String FROM = "from";
    private static final String BEFORE = "before";
    private static final String PAGE = "page";
    private static final String SIZE = "size";

    private final MyAccountProperties myAccountProperties;

    public URI getReferralBonusesByCustomerIdAndMultipleCriteriaUri(String email, CurrencyIso iso, Long from, Long before, Integer page, Integer size) {
        return UriComponentsBuilder.fromUriString(myAccountProperties.getUri())
                .path(myAccountProperties.getPath().getReferralBonusesByMultipleCriteria())
                .queryParam(EMAIL, email)
                .queryParam(CURRENCY_ISO, iso)
                .queryParam(FROM, from)
                .queryParam(BEFORE, before)
                .queryParam(PAGE, page)
                .queryParam(SIZE, size)
                .build()
                .encode()
                .toUri();
    }

    public URI getReferralInvitedCustomersUri(String customerId, int page, int size) {
        return UriComponentsBuilder.fromUriString(myAccountProperties.getUri())
                .path(myAccountProperties.getPath().getReferralInvitedCustomers())
                .queryParam(PAGE, page)
                .queryParam(SIZE, size)
                .buildAndExpand(customerId)
                .encode()
                .toUri();
    }

}
