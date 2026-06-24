package edesur.mac.iMacSrv.gestionOT.model.request;

import java.util.Date;
import java.time.LocalDate;

public record EnviaManserReq(
        long nroMensaje,
        long nroCliente,
        String rolOrigen,
        String codMotivo,
        LocalDate fechaVto,
        String codTension,
        float potencia,
        String codAcometida,
        String observaciones) { }
