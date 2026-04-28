package edesur.mac.iMacSrv.gestionOT.beans;

import edesur.mac.iMacSrv.gestionOT.model.response.MotivosOTsRes;
import edesur.mac.iMacSrv.gestionOT.model.response.ProcesoPendienteRes;

import edesur.mac.iMacSrv.gestionOT.utils.Mapeos;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class getProcesosPendientes {
    private final JdbcClient jdbcClient;
    public getProcesosPendientes(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<ProcesoPendienteRes> getProcesosPendientes(long nroCliente){
        StringBuilder sb = new StringBuilder(SEL_PROCESOS_PENDIENTES);
        List<Object> params = new ArrayList<>();

        params.add(nroCliente);
        List<ProcesoPendienteRes> resu = jdbcClient.sql(sb.toString()).params(params).query(ProcesoPendienteRes.class).list();

        return resu;
    }

    public static final String SEL_PROCESOS_PENDIENTES = "SELECT o.tipo_orden, TO_CHAR(o.fecha_inicio, '%d/%m/%Y') fecha_inicio, " +
            "o.mensaje_xnear, o.vencimiento, o.sfc_caso " +
            "FROM retcli r, orden o " +
            "WHERE r.numero_cliente = ? " +
            "AND o.numero_cliente = r.numero_cliente " +
            "AND o.tipo_orden IN ('MAN', 'RET') " +
            "AND o.ident_etapa != 'FI' " +
            "ORDER BY 2 ";
}
