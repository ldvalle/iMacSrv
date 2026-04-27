package edesur.mac.iMacSrv.gestionOT.beans;

import edesur.mac.iMacSrv.gestionOT.model.response.MedidorClienteRes;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedidorClienteManRet {
    private final JdbcClient jdbcClient;
    public MedidorClienteManRet(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public MedidorClienteRes getMedidorClienteManRet(long nroCliente){
        StringBuilder sb = new StringBuilder(SEL_MEDIDOR_CLIENTE);
        List<Object> params = new ArrayList<>();

        params.add(nroCliente);
        Optional<MedidorClienteRes> resu = jdbcClient.sql(sb.toString()).params(params).query(MedidorClienteRes.class).optional();

        if(resu.isEmpty()){
            MedidorClienteRes miResu= new MedidorClienteRes();
            miResu.setCodResultado("KO");
            return miResu;
        }

        return resu.get();
    }

    private static final String SEL_MEDIDOR_CLIENTE = "SELECT  FIRST 1 medid.marca_medidor, " +
            "medid.modelo_medidor, " +
            "medid.numero_medidor, " +
            "medid.constante, " +
            "medid.ultima_lect_activa, " +
            "hislec.lectura_terreno, " +
            "funmed.amperaje, " +
            "tabla.descripcion, " +
            "medid.clave_montri, " +
            "NVL(medid.ultima_lect_reac, 0) ultima_lect_reac, " +
            "NVL(hh.lectu_terreno_reac, 0) lectu_terreno_reac, " +
            "prt.serie, " +
            "NVL(prt.numero_precinto, 0) numero_precinto " +
            "FROM medid, hislec, " +
            "OUTER hislec_reac hh, " +
            "OUTER prt_precintos prt, " +
            "cliente, " +
            "OUTER funmed, tabla " +
            "WHERE medid.numero_cliente = ? " +
            "AND medid.estado = 'I' " +
            "AND hislec.numero_cliente = medid.numero_cliente " +
            "AND hislec.numero_medidor = medid.numero_medidor " +
            "AND hislec.marca_medidor = medid.marca_medidor " +
            "AND hh.numero_cliente = hislec.numero_cliente " +
            "AND hh.numero_medidor = hislec.numero_medidor " +
            "AND hh.marca_medidor = hislec.marca_medidor " +
            "AND hh.corr_facturacion = hislec.corr_facturacion " +
            "AND prt.numero_medidor = medid.numero_medidor " +
            "AND prt.marca = medid.marca_medidor " +
            "AND prt.modelo = medid.modelo_medidor " +
            "AND prt.estado_actual = '08' " +
            "AND cliente.numero_cliente =medid.numero_cliente " +
            "AND hislec.fecha_lectura = (SELECT max(h2.fecha_lectura) " +
            "    FROM hislec h2 " +
            "    WHERE h2.numero_cliente = medid.numero_cliente " +
            "    AND h2.numero_medidor = medid.numero_medidor " +
            "    AND h2.marca_medidor = medid.marca_medidor ) " +
            "AND funmed.codigo = (SELECT cla_codigo[1,1] " +
            "    FROM medidor " +
            "    WHERE med_numero = medid.numero_medidor " +
            "    AND mar_codigo = medid.marca_medidor " +
            "    AND mod_codigo = medid.modelo_medidor) " +
            "AND tabla.nomtabla = 'VOLTA' " +
            "AND tabla.sucursal = '0000' " +
            "AND funmed.voltaje = tabla.codigo " +
            "AND tabla.fecha_activacion <= TODAY " +
            "AND (tabla.fecha_desactivac > TODAY OR tabla.fecha_desactivac is NULL) ";
}
