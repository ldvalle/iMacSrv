package edesur.mac.iMacSrv.gestionOT.model.internal;

import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTMacSapDTO {
    String  oms_tipo_ifaz;
    String  oms_estado_ifaz;
    String  oms_nro_orden;
    String  oms_tipo_traba;
    String  oms_sucursal;
    String  oms_area_ejecuta;
    String  oms_motivo;
    LocalDate   oms_fecha_ini;
    String  oms_obs_dir;
    String  oms_obs_lectu;
    String  oms_obs_segen;
    String  oms_area_interloc;
    String  oms_proyecto;
    long    oms_nro_medidor;
    String  oms_marca_med;
    String  oms_modelo_med;
    String  oms_codbar;
    String  oms_cla_servi;
    float   oms_potencia;
    String  oms_tension;
    String  oms_acometida;
    String  oms_tipo_obra;
    String  oms_toma;
    String  oms_conexion;
    String  oms_pre1_ubic;
    String  oms_pre2_ubic;
    String  oms_pre3_ubic;
    String  oms_ruta_lectura;
    String  oms_nombre_cli;
    long    oms_nro_cli;
    String  oms_nom_entre;
    String  oms_nom_entre1;
    String  oms_telefono;
    String  oms_nom_calle;
    String  oms_nro_dir;
    String  oms_nom_partido;
    String  oms_piso_dir;
    String  oms_nom_comuna;
    int     oms_cod_postal;
    LocalDate   oms_fecha_vto;
    String  oms_depto_dir;
    String  oms_rol_creador;
    String  oms_nombre_rol;
    String  oms_proced;
    long    oms_nro_proced;
    String  oms_serie_prec_ret;
    long    oms_nro_prec_ret;

}
