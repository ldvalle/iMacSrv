package edesur.mac.iMacSrv.xnear.model.internal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MsgXnearParam {
    long   nroMensaje;
    String rolOrigen;
    String areaRolOrigen;
    String carpetaDestino;
    String areaCarpetaDestino;
    String procedimiento;
    String etapa;
    String referencia;
    String observaciones;
}
