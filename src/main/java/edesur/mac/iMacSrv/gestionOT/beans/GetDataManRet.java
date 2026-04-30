package edesur.mac.iMacSrv.gestionOT.beans;

import edesur.mac.iMacSrv.gestionOT.model.response.ClienteOTRes;
import edesur.mac.iMacSrv.gestionOT.model.response.DataCabeceraManRet;
import edesur.mac.iMacSrv.gestionOT.model.response.ManserFinalRes;
import edesur.mac.iMacSrv.gestionOT.model.response.RetcliFinalRes;
import edesur.mac.iMacSrv.gestionOT.model.response.TextonRes;
import edesur.mac.iMacSrv.gestionOT.model.response.MedidorRetiradoRes;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class GetDataManRet {
    private final JdbcClient jdbcClient;

    public GetDataManRet(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public DataCabeceraManRet getDataCabecera(long nroMensaje) {
        StringBuilder sb = new StringBuilder(SEL_CABECERA_MAN_RET);
        List<Object> params = new ArrayList<>();

        params.add(nroMensaje);
        DataCabeceraManRet resu = jdbcClient.sql(sb.toString()).params(params).query(DataCabeceraManRet.class).single();

        return resu;
    }

    public ManserFinalRes getManserFinal(long nroCliente, long nroMensaje) {
        StringBuilder sb = new StringBuilder(SEL_MANSER_FINAL);
        List<Object> params = new ArrayList<>();

        params.add(nroMensaje);
        params.add(nroCliente);
        ManserFinalRes resu = jdbcClient.sql(sb.toString()).params(params).query(ManserFinalRes.class).single();

        return resu;
    }

    public RetcliFinalRes getRetcliFinal(long nroCliente, long nroMensaje) {
        StringBuilder sb = new StringBuilder(SEL_RETCLI_FINAL);
        List<Object> params = new ArrayList<>();

        params.add(nroMensaje);
        params.add(nroCliente);
        RetcliFinalRes resu = jdbcClient.sql(sb.toString()).params(params).query(RetcliFinalRes.class).single();

        return resu;
    }

    public List<TextonRes> getTexton(long nroMensaje){
        StringBuilder sb = new StringBuilder(SEL_TEXTON);
        List<Object> params = new ArrayList<>();

        params.add(nroMensaje);
        List<TextonRes> resu = jdbcClient.sql(sb.toString()).params(params).query(TextonRes.class).list();

        return resu;
    }

    public MedidorRetiradoRes getMedidorRetirado(long nroCliente, long nroMensaje) {
        StringBuilder sb = new StringBuilder(SEL_MEDIDOR_RETIRADO);
        List<Object> params = new ArrayList<>();

        params.add(nroMensaje);
        params.add(nroCliente);
        MedidorRetiradoRes resu = jdbcClient.sql(sb.toString()).params(params).query(MedidorRetiradoRes.class).single();

        return resu;
    }

    private static final String SEL_CABECERA_MAN_RET = "SELECT o.mensaje_xnear, o.numero_orden, m.etapa, m.fecha_creacion, " +
            "m.rol_creacion, r.area, o.ident_etapa, o.tema, o.trabajo," +
            "CASE " +
            "   WHEN o.tipo_orden = 'MAN' then " +
            "        (select trim(t1.descripcion) from tabla t1 " +
            "        where t1.nomtabla = 'OTMOMA' and t1.sucursal = '0000' " +
            "        and t1.codigo = o.tema and t1.fecha_desactivac is null) " +
            "   ELSE " +
            "        (select trim(t1.descripcion) from tabla t1  " +
            "        where t1.nomtabla = 'OTMORE' and t1.sucursal = '0000' " +
            "        and t1.codigo = trim(o.tema) || trim(o.trabajo) and t1.fecha_desactivac is null) " +
            "END desc_motivo, " +
            "CASE " +
            "   WHEN o.ident_etapa = 'RQ' AND o.tipo_orden = 'MAN' THEN " +
            "      (select 'SC' || lpad(p.ot_nro_orden, 10, 0) from ot_mac p " +
            "        where p.ot_mensaje_xnear = m.mensaje) " +
            "   WHEN o.ident_etapa = 'RQ' AND o.tipo_orden = 'RET' THEN " +
            "      (select 'SR' || lpad(p.ot_nro_orden, 10, 0) from ot_mac p " +
            "        where p.ot_mensaje_xnear = m.mensaje) " +
            "   WHEN o.ident_etapa != 'RQ' AND o.tipo_orden = 'MAN' THEN " +
            "      (select 'SC' || lpad(p.otf_nro_orden, 10, 0) from ot_final p " +
            "        where p.mensaje_xnear = m.mensaje) " +
            "   WHEN o.ident_etapa != 'RQ' AND o.tipo_orden = 'RET' THEN " +
            "      (select 'SR' || lpad(p.otf_nro_orden, 10, 0) from ot_final p " +
            "        where p.mensaje_xnear = m.mensaje) " +
            "END nro_orden, " +
            "CASE " +
            "   WHEN o.ident_etapa = 'RQ' THEN " +
            "        (select upper(trim(s.descripcion)) from ot_mac p, ot_status s " +
            "        where p.ot_mensaje_xnear = m.mensaje " +
            "        and s.codigo  = p.ot_status " +
            "        and s.fecha_activacion <= today " +
            "        and s.fecha_desactivac is null) " +
            "   ELSE 'FINALIZADA' " +
            "END estado, " +
            "CASE  " +
            "   WHEN o.ident_etapa = 'RQ' THEN " +
            "        (select to_char(p.ot_fecha_vto, '%d/%m/%Y') from ot_mac p " +
            "        where p.ot_mensaje_xnear = m.mensaje) " +
            "END fecha_vto, " +
            "CASE " +
            "   WHEN o.ident_etapa = 'RQ' THEN " +
            "        (select p.ot_tipo_traba from ot_mac p " +
            "        where p.ot_mensaje_xnear = m.mensaje) " +
            "   ELSE " +
            "        (select p.otf_tipo_traba from ot_final p " +
            "        where p.mensaje_xnear = m.mensaje) " +
            "END tipo_trabajo, " +
            "o.numero_cliente " +
            "FROM xnear2:mensaje m, orden o, OUTER rol r " +
            "WHERE m.mensaje = ? " +
            "AND o.mensaje_xnear = m.mensaje " +
            "AND r.rol = m.rol_creacion " +
            "AND r.vigencia = 'V' ";

    public static final String SEL_MANSER_FINAL = "SELECT TO_CHAR(o.fecha_ejecucion, '%d/%m/%Y') fecha_ejecucion, " +
            "TO_CHAR(o.otf_hora_inicio, '%H:%M') otf_hora_inicio, TO_CHAR(o.otf_hora_final, '%H:%M') otf_hora_final, " +
            "o.otf_lect_retiro, o.otf_lect_instal, m.lectu_instal_reac, o.otf_med_distinto, " +
            "o.otf_modifica_red, o.otf_proyecto, o.cod_ejecutor, TRIM(c.nombre) nombre_ejecutor, " +
            "o.numero_med_ant, o.marca_med_ant, o.modelo_med_ant, " +
            "o.numero_med_coloca, o.marca_med_coloca, o.modelo_med_coloca, " +
            "o.serie_prec_retira, o.nro_prec_retira, o.serie_prec_coloca, o.nro_prec_coloca " +
            "FROM ot_final o, contratista c, OUTER medid m " +
            "WHERE o.mensaje_xnear = ? " +
            "AND o.fecha_ejecucion = (select max(o2.fecha_ejecucion) from ot_final o2 " +
            "   where o2.mensaje_xnear = o.mensaje_xnear " +
            "   and o2.proced = o.proced) " +
            "AND c.contratista = o.cod_ejecutor[1,3] " +
            "AND c.tipo_contratista in ('O','S') " +
            "AND m.numero_medidor = o.numero_med_coloca " +
            "AND m.marca_medidor = o.marca_med_coloca " +
            "AND m.modelo_medidor = o.modelo_med_coloca " +
            "AND m.numero_cliente = ? ";

    public static final String SEL_RETCLI_FINAL = "SELECT TO_CHAR(o.fecha_ejecucion, '%d/%m/%Y') fecha_ejecucion, " +
            "TO_CHAR(o.otf_hora_inicio, '%H:%M') otf_hora_inicio, TO_CHAR(o.otf_hora_final, '%H:%M') otf_hora_final, " +
            "o.otf_lect_retiro, o.otf_lect_instal, m.lectu_instal_reac, m.constante, m.ultima_lect_activa, o.otf_med_distinto, " +
            "m.clave_montri, m.ultima_lect_reac, o.otf_modifica_red, o.otf_proyecto, o.cod_ejecutor, TRIM(c.nombre) nombre_ejecutor, " +
            "o.numero_med_ant, o.marca_med_ant, o.modelo_med_ant, " +
            "o.serie_prec_retira, o.nro_prec_retira, o.serie_prec_coloca, o.nro_prec_coloca, " +
            "h.lectura_terreno, " +
            "funmed.amperaje, " +
            "tabla.descripcion descrip_tension, " +
            "hh.lectu_terreno_reac, " +
            "prt.serie, " +
            "prt.numero_precinto " +
            "FROM ot_final o, contratista c, medid m, " +
            "hislec h, OUTER hislec_reac hh, OUTER prt_precintos prt, " +
            "OUTER (funmed, tabla) " +
            "WHERE o.mensaje_xnear = ? " +
            "AND o.fecha_ejecucion = (select max(o2.fecha_ejecucion) from ot_final o2 " +
            "   where o2.mensaje_xnear = o.mensaje_xnear " +
            "   and o2.proced = o.proced) " +
            "AND c.contratista = o.cod_ejecutor[1,3] " +
            "AND c.tipo_contratista in ('O','S') " +
            "AND m.numero_medidor = o.numero_med_ant " +
            "AND m.marca_medidor = o.marca_med_ant " +
            "AND m.modelo_medidor = o.modelo_med_ant " +
            "AND m.numero_cliente = ? " +
            "AND h.numero_cliente = m.numero_cliente " +
            "AND h.numero_medidor = m.numero_medidor " +
            "AND h.marca_medidor = m.marca_medidor " +
            "AND h.fecha_lectura = (SELECT max(h2.fecha_lectura) " +
            "    FROM hislec h2 " +
            "    WHERE h2.numero_cliente = m.numero_cliente " +
            "    AND h2.numero_medidor = m.numero_medidor " +
            "    AND h2.marca_medidor = m.marca_medidor ) " +
            "AND hh.numero_cliente = h.numero_cliente " +
            "AND hh.numero_medidor = h.numero_medidor " +
            "AND hh.marca_medidor = h.marca_medidor " +
            "AND hh.corr_facturacion = h.corr_facturacion " +
            "AND prt.numero_medidor = m.numero_medidor " +
            "AND prt.marca = m.marca_medidor " +
            "AND prt.modelo = m.modelo_medidor " +
            "AND prt.estado_actual = '08' " +
            "AND funmed.codigo = (SELECT cla_codigo[1,1] " +
            "    FROM medidor " +
            "    WHERE med_numero = m.numero_medidor " +
            "    AND mar_codigo = m.marca_medidor " +
            "    AND mod_codigo = m.modelo_medidor) " +
            "AND tabla.nomtabla = 'VOLTA' " +
            "AND tabla.sucursal = '0000' " +
            "AND funmed.voltaje = tabla.codigo " +
            "AND tabla.fecha_activacion <= TODAY " +
            "AND (tabla.fecha_desactivac > TODAY OR tabla.fecha_desactivac is NULL) ";

    public static final String SEL_TEXTON = "SELECT pagina, texton FROM xnear2:pagina " +
            "WHERE mensaje = ? " +
            "AND servidor = 1 " +
            "ORDER BY pagina ";

    public static final String SEL_MEDIDOR_RETIRADO = "SELECT medid.marca_medidor, " +
            "medid.modelo_medidor, " +
            "medid.numero_medidor, " +
            "medid.constante, " +
            "medid.ultima_lect_activa, " +
            "hislec.lectura_terreno, " +
            "funmed.amperaje, " +
            "tabla.descripcion descripcion_voltaje, " +
            "medid.clave_montri, " +
            "medid.ultima_lect_reac, " +
            "hh.lectu_terreno_reac, " +
            "prt.serie, " +
            "prt.numero_precinto " +
            "FROM ot_final o, medid, hislec, " +
            "OUTER hislec_reac hh, " +
            "OUTER prt_precintos prt, " +
            "cliente, " +
            "OUTER ( funmed, tabla ) " +
            "WHERE o.mensaje_xnear = ? " +
            "AND o.fecha_ejecucion = (select max(o2.fecha_ejecucion) from ot_final o2" +
            "   where o2.mensaje_xnear = o.mensaje_xnear " +
            "   and o2.proced = o.proced) " +
            "AND medid.numero_medidor = o.numero_med_ant " +
            "AND medid.marca_medidor = o.marca_med_ant " +
            "AND medid.modelo_medidor = o.modelo_med_ant " +
            "AND medid.numero_cliente = ? " +
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