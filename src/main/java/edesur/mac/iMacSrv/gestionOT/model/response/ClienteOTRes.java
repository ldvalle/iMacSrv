package edesur.mac.iMacSrv.gestionOT.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteOTRes {
    String codigoResultado = "OK";
    long numero_cliente;
    String dv_numero_cliente;
    String tipo_empalme;
    float potencia_contrato;
    float potencia_inst_fp;
    String nombre;
    String nom_comuna;
    String nom_calle;
    String nom_provincia;
    String nom_sucursal;
    String nom_partido;
    String nro_dir;
    String piso_dir;
    String depto_dir;
    String nom_entre;
    String nom_entre1;
    String nom_barrio;
    String estado_facturacion;
    String tipo_sum;
    int sector;
    int zona;
    long correlativo_ruta;
    int estado_cliente;
    String sucursal;
    String suc_padre;
    String descrip_empalme;
    String telefono;
    int cod_postal;
    String tipo_cliente;
    String descrip_tipo_cliente;
    String obs_dir;
    String info_adic_lectura;
    String tipo_iva;
    String tarifa;
    String rut;
    String actividad_economic;
    String cod_propiedad;
    String tip_doc;
    long nro_doc;
    String estado_cobrabilida;
    String nro_subestacion;
    String codigo_voltaje;
    String descrip_voltaje;
    String acometida;
    String descrip_acometida;
}

