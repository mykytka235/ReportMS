
package mykytka235.ms.report.web.model.referral;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReferralDtoPaginated {

    @NotNull
    @Valid
    private List<CustomerReferralDto> list = new ArrayList<>();
    @NotNull
    private Long totalItems;

}
