package co.com.monks.model.exception.rds.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechnicalErrorMessage {
    USER_FIND_ALL("TXT0001", "Error al obtener todos los usuarios"),
    USER_FIND_BY_ID("TXT0002", "Error al obtener usuario por id"),
    USER_UPDATE("TXT0004", "No fue posible modificar el usuario"),

    UNEXPECTED_EXCEPTION("TXT","Error inesperado");

    private final String code;
    private final String message;
}
