package edesur.mac.iMacSrv.gestionOT.beans;

import edesur.mac.iMacSrv.gestionOT.model.response.ClienteOTRes;
import io.undertow.security.handlers.CachedAuthenticatedSessionHandler;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataClienteManRet {
    private final JdbcClient jdbcClient;
    public DataClienteManRet(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public ClienteOTRes getDataCliente(long nroCliente){
        StringBuilder sb = new StringBuilder(SEL_DATA_CLIENTE);
        List<Object> params = new ArrayList<>();

        params.add(nroCliente);
        Optional<ClienteOTRes> resu = jdbcClient.sql(sb.toString()).params(params).query(ClienteOTRes.class).optional();

        if(resu.isEmpty()){
            ClienteOTRes miResu= new ClienteOTRes();
            miResu.setCodigoResultado("KO");
            return miResu;
        }

        return resu.get();
    }


    private static final String SEL_DATA_CLIENTE = "SELECT cliente.numero_cliente, " +
            "cliente.dv_numero_cliente, " +
            "cliente.tipo_empalme, " +
            "cliente.potencia_contrato, " +
            "cliente.potencia_inst_fp, " +
            "cliente.nombre, " +
            "cliente.nom_comuna, " +
            "cliente.nom_calle, " +
            "cliente.nom_provincia, " +
            "cliente.nom_sucursal, " +
            "cliente.nom_partido, " +
            "cliente.nro_dir, " +
            "cliente.piso_dir, " +
            "cliente.depto_dir, " +
            "cliente.nom_entre, " +
            "cliente.nom_entre1, " +
            "cliente.nom_barrio, " +
            "cliente.estado_facturacion, " +
            "cliente.tipo_sum, " +
            "cliente.sector, " +
            "cliente.zona, " +
            "cliente.correlativo_ruta, " +
            "cliente.estado_cliente, " +
            "cliente.sucursal, " +
            "ot_sucursal.suc_padre, " +
            "TRIM(tabemp.descripcion) descrip_empalme, " +
            "cliente.telefono, " +
            "cliente.cod_postal, " +
            "cliente.tipo_cliente, " +
            "TRIM(tabtip.descripcion) descrip_tipo_cliente, " +
            "cliente.obs_dir, " +
            "cliente.info_adic_lectura, " +
            "cliente.tipo_iva, " +
            "cliente.tarifa, " +
            "cliente.rut, " +
            "cliente.actividad_economic, " +
            "cliente.cod_propiedad, " +
            "cliente.tip_doc, " +
            "TRUNC(cliente.nro_doc, 0), " +
            "cliente.estado_cobrabilida, " +
            "tc.nro_subestacion, " +
            "tc.codigo_voltaje, " +
            "TRIM(t1.descripcion) descrip_voltaje, " +
            "tc.acometida, " +
            "t2.descripcion descrip_acometida, " +
            "CASE " +
            "   WHEN r.codigo IS NOT NULL THEN 'S' " +
            "   ELSE 'N' "+
            "END man_ret_pendiente, " +
            "tecni.tipo_conexion " +
            "FROM cliente, OUTER ot_sucursal, OUTER tabla tabemp , OUTER tabla tabtip, " +
            "OUTER (tecni tc, OUTER tabla t1, OUTER tabla t2), OUTER retcli r " +
            "WHERE cliente.numero_cliente = ?" +
            "AND ot_sucursal.suc_hijo = cliente.sucursal " +
            "AND (tabemp.codigo = cliente.tipo_empalme " +
            "AND tabemp.sucursal = cliente.sucursal " +
            "AND tabemp.nomtabla = 'EMPAL') " +
            "AND (tabtip.codigo = cliente.tipo_cliente " +
            "AND tabtip.sucursal = cliente.sucursal " +
            "AND tabtip.nomtabla = 'VENCIM') " +
            "AND tc.numero_cliente = CLIENTE.numero_cliente " +
            "AND t1.nomtabla = 'VOLTA' " +
            "AND t1.sucursal = '0000' " +
            "AND t1.codigo = tc.codigo_voltaje " +
            "AND t1.fecha_desactivac IS NULL " +
            "AND t2.nomtabla = 'TIRED' " +
            "AND t2.sucursal = '0000' " +
            "AND t2.codigo = tc.acometida " +
            "AND t2.fecha_desactivac IS NULL " +
            "AND r.numero_cliente = cliente.numero_cliente ";

}
