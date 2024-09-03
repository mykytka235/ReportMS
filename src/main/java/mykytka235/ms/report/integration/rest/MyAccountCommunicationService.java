package mykytka235.ms.report.integration.rest;

import mykytka235.ms.report.constants.CurrencyIso;
import mykytka235.ms.report.web.model.ResponsePageDto;
import mykytka235.ms.report.web.model.referral.CustomerReferralDtoPaginated;
import mykytka235.ms.report.web.model.referral.ReferralBonusesDtoResponse;

public interface MyAccountCommunicationService {

    ResponsePageDto<ReferralBonusesDtoResponse> getReferralBonusesByCustomerIdAndMultipleCriteria(String customerId,
                                                                                                  CurrencyIso iso,
                                                                                                  Long from, Long before,
                                                                                                  Integer page, Integer size);

    CustomerReferralDtoPaginated getAllCustomerReferralByCustomerId(String customerId, int page, int size);
}
