package edesur.mac.iMacSrv.gestionOT.model.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "codigoResultado" })
public interface Response {
    default String getCodigoResultado() {
        return "OK";
    }
}
