package org.example.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer id;
    @NotEmpty
    @Range(max = 250)
    private String author;
    @NotEmpty
    @Range(max = 250)
    private String title;
    @Range(max = 4)
    private Integer size;
}
