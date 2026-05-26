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
    String man_ret_pendiente;


    public String getNombre(){ return (this.nombre != null) ? this.nombre.trim() : null; }
    public String getNom_comuna(){ return (this.nom_comuna != null) ? this.nom_comuna.trim() : null; }
    public String getNom_calle(){ return (this.nom_calle != null) ? this.nom_calle.trim() : null; }
    public String getNom_provincia(){ return (this.nom_provincia != null) ? this.nom_provincia.trim() : null; }
    public String getNom_sucursal(){ return (this.nom_sucursal != null) ? this.nom_sucursal.trim() : null; }
    public String getNom_partido() { return (this.nom_partido != null) ? this.nom_partido.trim() : null; }
    public String getNro_dir() { return (this.nro_dir != null) ? this.nro_dir.trim() : null; }
    public String getPiso_dir() { return (this.piso_dir != null) ? this.piso_dir.trim() : null; }
    public String getDepto_dir() { return (this.depto_dir != null) ? this.depto_dir.trim() : null; }
    public String getNom_entre() { return (this.nom_entre != null) ? this.nom_entre.trim() : null; }
    public String getNom_entre1() { return (this.nom_entre1 != null) ? this.nom_entre1.trim() : null; }
    public String getNom_barrio() { return (this.nom_barrio != null) ? this.nom_barrio.trim() : null; }
    public String getDescrip_empalme() { return (this.descrip_empalme != null) ? this.descrip_empalme.trim() : null; }
    public String getDescrip_tipo_cliente() { return (this.descrip_tipo_cliente != null) ? this.descrip_tipo_cliente.trim() : null; }
    public String getDescrip_voltaje() { return (this.descrip_voltaje != null) ? this.descrip_voltaje.trim() : null; }
    public String getDescrip_acometida() { return (this.descrip_acometida != null) ? this.descrip_acometida.trim() : null; }
    public String getObs_dir() { return (this.obs_dir != null) ? this.obs_dir.trim() : null; }
    public String getInfo_adic_lectura() { return (this.info_adic_lectura != null) ? this.info_adic_lectura.trim() : null; }
    public String getRut() { return (this.rut != null) ? this.rut.trim() : null; }

}

