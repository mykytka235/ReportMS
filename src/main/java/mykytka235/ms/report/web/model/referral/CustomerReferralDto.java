
package mykytka235.ms.report.web.model.referral;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReferralDto {

    @NotNull
    private String referralId;
    @NotNull
    private Long date;

}
