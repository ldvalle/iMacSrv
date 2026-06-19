package edesur.mac.iMacSrv.gestionOT.model.internal;

import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedidDTO {
    long    numero_medidor;
    String  marca_medidor;
    String  modelo_medidor;
    long    numero_cliente;
    int     correlativo;
    String  clase;
    String  estado;
    String  clave_montri;
    LocalDate   fecha_prim_insta;
    LocalDate   fecha_ult_insta;
    String      propiedad_medidor;
    int         enteros;
    int         decimales;
    float       constante;
    float       lectura_instal;
    String      cr_origen;
    String      cr_tipo;
    int         cr_ano;
    String      cr_reacondiciona;
    String      tipo_orden_xn;
    long        numero_orden_xn;
    float       ultima_lect_activa;
    float       consumo_30_dias;
    LocalDate   fecha_orden_xn;
    String      almac_contratista;
    float       consumo_med_ant;
    LocalDate   fecha_alta;
    float       lectu_instal_reac;
    float       ultima_lect_reac;
    float       cons_30_dias_reac;
    float       cons_med_ant_reac;
    String      tipo_medidor;

}
