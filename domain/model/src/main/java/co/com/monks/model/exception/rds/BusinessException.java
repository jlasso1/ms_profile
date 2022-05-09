package co.com.monks.model.exception.rds;

import co.com.monks.model.exception.rds.message.BussinessErrorMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessException extends Exception{
    private final BussinessErrorMessage businessErrorMessage;
}
