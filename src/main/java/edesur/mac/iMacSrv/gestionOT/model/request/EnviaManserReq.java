package edesur.mac.iMacSrv.gestionOT.model.request;

import java.util.Date;

public record EnviaManserReq(
        long nroMensaje,
        long nroCliente,
        String rolOrigen,
        String codMotivo,
        Date fechaVto,
        String codAcometida,
        String observaciones) { }
