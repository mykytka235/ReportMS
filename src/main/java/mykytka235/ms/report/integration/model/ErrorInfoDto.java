package mykytka235.ms.report.integration.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfoDto implements Serializable {
    @NotNull
    private Integer status;
    @NotNull
    private List<String> messages;
    @NotNull
    private String debugMessage;
    @NotNull
    private Integer code;
    private String header;
}
