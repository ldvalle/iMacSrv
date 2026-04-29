package edesur.mac.iMacSrv.gestionOT.model.response;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ManserFinalRes {
    String fecha_ejecucion;
    String otf_hora_inicio;
    String otf_hora_final;
    Float otf_lect_retiro;
    Float otf_lect_instal;
    Float lectu_instal_reac;
    String otf_med_distinto;
    String otf_modifica_red;
    String otf_proyecto;
    String cod_ejecutor;
    String nombre_ejecutor;
    Long numero_med_ant;
    String marca_med_ant;
    String modelo_med_ant;
    Long numero_med_coloca;
    String marca_med_coloca;
    String modelo_med_coloca;
    String serie_prec_retira;
    Long nro_prec_retira;
    String serie_prec_coloca;
    Long nro_prec_coloca;

}
