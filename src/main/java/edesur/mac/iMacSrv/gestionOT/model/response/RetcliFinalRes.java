package edesur.mac.iMacSrv.gestionOT.model.response;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class RetcliFinalRes {
    String fecha_ejecucion;
    String otf_hora_inicio;
    String otf_hora_final;
    Float otf_lect_retiro;
    Float otf_lect_instal;
    Float lectu_instal_reac;
    Float constante;
    Float ultima_lect_activa;
    String otf_med_distinto;
    String clave_montri;
    Float ultima_lect_reac;
    String otf_modifica_red;
    String otf_proyecto;
    String cod_ejecutor;
    String nombre_ejecutor;
    Long numero_med_ant;
    String marca_med_ant;
    String modelo_med_ant;
    String serie_prec_retira;
    Long nro_prec_retira;
    String serie_prec_coloca;
    Long nro_prec_coloca;
    Float lectura_terreno;
    String amperaje;
    String descrip_tension;
    Float lectu_terreno_reac;
    String serie;
    Long numero_precinto;

}
