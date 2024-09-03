
package mykytka235.ms.report.web.model.referral;

import mykytka235.ms.report.constants.CurrencyIso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReferralBonusesDtoResponse {

    @NotNull
    private String email;
    private String providedReferralId;
    @NotNull
    private CurrencyIso currencyIso;
    @NotNull
    private Double amount;
    @NotNull
    private Long date;

}
