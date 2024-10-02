package bkd.src.salus.api.Domain.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArgumentException {
    private String field;
    private String message;
}
