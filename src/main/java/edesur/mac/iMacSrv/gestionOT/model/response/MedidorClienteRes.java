package edesur.mac.iMacSrv.gestionOT.model.response;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedidorClienteRes extends ResponseBase {
    String marca_medidor;
    String modelo_medidor;
    long numero_medidor;
    float constante;
    float ultima_lect_activa;
    float lectura_terreno;
    String amperaje;
    String descripcion;
    String clave_montri;
    @Nullable
    float ultima_lect_reac;
    @Nullable
    float lectu_terreno_reac;
    String serie;
    long numero_precinto;

}
