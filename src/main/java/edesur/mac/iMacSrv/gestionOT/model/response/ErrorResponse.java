package edesur.mac.iMacSrv.gestionOT.model.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class ErrorResponse {
    @Getter
    private final String codigo;
    private final String descripcion;

    public ErrorResponse(String codigo, String descripcion) {
        this.codigo      = codigo;
        this.descripcion = descripcion;
    }

    public ErrorResponse(int codigo, String descripcion) {
        this(String.valueOf(codigo), descripcion);
    }

    public ErrorResponse(HttpStatus status, String descripcion) {
        this(String.valueOf(status.value()), descripcion);
    }

    public String getDescripcion() {
        return descripcion != null ? StringUtils.truncate(descripcion, 100) : null;
    }
}
