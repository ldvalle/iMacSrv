package edesur.mac.iMacSrv.gestionOT.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedidorRetiradoRes {
    String marca_medidor;
    String modelo_medidor;
    long numero_medidor;
    float constante;
    Float ultima_lect_activa;
    Float lectura_terreno;
    String amperaje;
    String descripcion_voltaje;
    String clave_montri;
    Float ultima_lect_reac;
    Float lectu_terreno_reac;
    String serie;
    Long numero_precinto;

    public String getDescripcion_voltaje(){ return (this.descripcion_voltaje != null)? this.descripcion_voltaje.trim(): null; }
}
