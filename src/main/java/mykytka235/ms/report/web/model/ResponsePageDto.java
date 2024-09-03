package mykytka235.ms.report.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePageDto<T> {

    private List<T> body;
    private Long totalElements;
    private Integer totalPages;

    public static <T> ResponsePageDto<T> createOf(List<T> data, int totalPages, long totalElements) {
        return ResponsePageDto.<T>builder()
                .body(data)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

}
