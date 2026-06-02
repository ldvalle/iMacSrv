package edesur.mac.iMacSrv.gestionOT.model.internal;

import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTHisevenDTO {
    long    ots_nro_orden;
    long    ots_numero_cliente;
    String  ots_status;
    LocalDateTime   ots_fecha;
    String  ots_observac;
    LocalDateTime   ots_fecha_proc;
    String  ots_tipo_ifaz;
}
