package edesur.mac.iMacSrv.gestionOT.model.internal;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTMacDTO {

    long    ot_nro_orden;
    String  ot_envia_sap;
    long    ot_numero_cliente;
    long    ot_mensaje_xnear;
    String  ot_proced;
    String  ot_gise;
    long    ot_nro_solicitud;
    String  ot_estado;
    Date    ot_fecha_est;
    String  ot_status;
    Date    ot_fecha_status;
    Date    ot_fecha_inicio;
    String  ot_estado_ifaz;
    String  ot_sucursal_padre;
    String  ot_sucursal;
    int     ot_sector;
    int     ot_zona;
    long    ot_corr_ruta;
    String  ot_tipo_traba;
    String  ot_area_interloc;
    String  ot_motivo;
    String  ot_rol_ejecuta;
    String  ot_area_ejecuta;
    float   ot_potencia;
    String  ot_tension;
    String  ot_acometida;
    String  ot_tipo_obra;
    String  ot_toma;
    String  ot_conexion;
    String  ot_modifica_red;
    Date    ot_fecha_vto;
    String  ot_proyecto;
    Date    ot_fecha_oc;
    String  ot_tarifa;
    Date    ot_fecha_ctec;
    double  ot_costo_mo;
    double  ot_costo_mt;

}
