package mykytka235.ms.report.integration.rest;

import mykytka235.ms.report.constants.CurrencyIso;
import mykytka235.ms.report.integration.uri.MyAccountUriBuilder;
import mykytka235.ms.report.web.model.ResponsePageDto;
import mykytka235.ms.report.web.model.referral.CustomerReferralDtoPaginated;
import mykytka235.ms.report.web.model.referral.ReferralBonusesDtoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
public class MyAccountCommunicationServiceImpl extends RestTemplateCommunicationService implements MyAccountCommunicationService {

    private final MyAccountUriBuilder myAccountUriBuilder;

    public MyAccountCommunicationServiceImpl(RestTemplate myAccountRestTemplate, MyAccountUriBuilder myAccountUriBuilder) {
        super(myAccountRestTemplate);
        this.myAccountUriBuilder = myAccountUriBuilder;
    }

    @Override
    public ResponsePageDto<ReferralBonusesDtoResponse> getReferralBonusesByCustomerIdAndMultipleCriteria(String customerId,
                                                                                                         CurrencyIso iso,
                                                                                                         Long from,
                                                                                                         Long before,
                                                                                                         Integer page,
                                                                                                         Integer size) {
        URI uri = myAccountUriBuilder.getReferralBonusesByCustomerIdAndMultipleCriteriaUri(customerId, iso, from, before, page, size);

        return performParametrizedRequest(uri, HttpMethod.GET, HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {},
                () -> log.info("Request to be-myaccount to get referral bonuses info. Email: {}", customerId),
                () -> log.info("Request to be-myaccount to get referral bonuses processed. Email: {}", customerId),
                e -> log.error("No response from be-myaccount on get all referral bonuses. Email: {}, error: {}", customerId, e.getMessage())
        );
    }

    @Override
    public CustomerReferralDtoPaginated getAllCustomerReferralByCustomerId(String customerId, int page, int size) {
        URI uri = myAccountUriBuilder.getReferralInvitedCustomersUri(customerId, page, size);

        return performRequest(uri, HttpMethod.GET, HttpEntity.EMPTY,
                CustomerReferralDtoPaginated.class,
                () -> log.info("Get all customer referrals by customer id request was sent to be-myaccount service"),
                res -> log.info("Response with list of customer referrals by customer id from be-myaccount service: isEmpty = {}", res.getBody().getList().isEmpty()),
                e -> log.info("No response from be-myaccount service for get all customer referrals by customer id: error = {}", e.getMessage())
        );
    }
}
