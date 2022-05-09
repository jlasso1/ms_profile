package co.com.monks.model.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionModel extends Throwable{
    private List errors;
    private String message;
}
