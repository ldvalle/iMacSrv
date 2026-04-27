package edesur.mac.iMacSrv.xnear.model.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "codigo" })
public interface Response {
    default String getCodigo() {
        return "Ok";
    }
}
