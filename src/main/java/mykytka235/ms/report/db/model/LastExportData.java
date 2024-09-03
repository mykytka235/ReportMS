package mykytka235.ms.report.db.model;

import mykytka235.ms.report.constants.ExportFileType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.EnumMap;
import java.util.Map;

@Getter
@Setter
@Builder
@Document(collection = "referralBonusesExportData")
public class LastExportData {

    @Id
    private ObjectId id;
    private String customerId;
    @Builder.Default
    private Map<ExportFileType, Long> lastTimeExportMap = new EnumMap<>(ExportFileType.class); // value represents timestamp of last export
    private Long lastTimeExport;

}
