package edesur.mac.iMacSrv.xnear.model.internal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MsgXnearParam {
    long   nroMensaje;
    String procedimiento;
    String etapa;
    short  privacidad;
    short  urgencia;
    String encriptado;
    String referencia;
    String rolOrigen;
    String areaRolOrigen;
    String carpetaDestino;
    String areaCarpetaDestino;
    short  empCon;
    short  empOrg;
    short  empDest;
    String texton;
}
