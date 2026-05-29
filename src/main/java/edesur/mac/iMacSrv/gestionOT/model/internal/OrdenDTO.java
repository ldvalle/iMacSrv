package edesur.mac.iMacSrv.gestionOT.model.internal;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdenDTO {
    String tipo_orden;
    String numero_orden;
    long   mensaje_xnear;
    int    servidor;
    String sucursal;
    String area_emisora;
    Date   fecha_inicio;
    String ident_etapa;
    String term_dir;
    String area_ejecutora;
    Date   duracion;
    String prioridad;
    String estado;
    String rol_usuario;
    String tema;
    String trabajo;
    String clase;
    String tipo_orden_rel;
    long   numero_orden_rel;
    long   valor_cobro;
    long   numero_cliente;
    Date   vencimiento;
    String cuenta_conver;
    String sucu_usu;
    long   sfc_caso;
    long   sfc_nro_orden;
}
