package edesur.mac.iMacSrv.xnear.model.response;

import java.sql.Date;

public record Tarea(
    long mensaje, 
    String referencia,
    String rol_anterior,
    String rol_actual,
    Date fecha_traspaso,
    String etiqueta,
    String proced
) {}
