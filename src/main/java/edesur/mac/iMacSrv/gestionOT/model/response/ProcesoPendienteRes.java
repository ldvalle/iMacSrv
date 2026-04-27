package edesur.mac.iMacSrv.gestionOT.model.response;

import java.util.Date;

public record ProcesoPendienteRes(
    String tipo_orden,
    String fecha_inicio,
    long   mensaje_xnear,
    Date   vencimiento,
    long   sfc_caso
) { }