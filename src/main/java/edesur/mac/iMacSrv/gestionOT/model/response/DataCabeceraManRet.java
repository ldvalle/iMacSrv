package edesur.mac.iMacSrv.gestionOT.model.response;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataCabeceraManRet {
    long mensaje_xnear;
    String numero_orden;
    String etapa;
    Date fecha_creacion;
    String rol_creacion;
    String rol_actual;
    String area;
    String ident_etapa;
    String tema;
    String trabajo;
    String desc_motivo;
    String nro_orden_sap;
    String estado;
    String fecha_vto;
    String tipo_trabajo;
    long numero_cliente;
    Long sfc_caso;

    public String getRol_creacion(){ return (this.rol_creacion != null) ? this.rol_creacion.trim() : null; }
    public String getDesc_motivo(){ return (this.desc_motivo != null) ? this.desc_motivo.trim() : null; }
}
