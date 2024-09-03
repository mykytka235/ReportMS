package mykytka235.ms.report.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExportFileType {
    REFERRAL_BONUSES("Referral bonuses"),
    REFERRAL_FRIENDS("Referral friends");

    private final String fileName;
}
