package co.com.monks.model.exception.rds.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BussinessErrorMessage {
    USER_NOT_FOUND("TXB0001", "Usuario no encontrado"),
    BAD_REQUEST_PARAM("TXB0002", "Error en el envio de parametros"),
    BAD_REQUEST_BODY("TXB0003", "Error en el envio del cuerpo de la peticion");

    private final String code;
    private final String message;
}
