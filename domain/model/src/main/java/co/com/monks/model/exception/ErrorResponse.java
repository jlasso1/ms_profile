package co.com.monks.model.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class ErrorResponse {
    private String reason;
    private String code;
    private Object message;
}
