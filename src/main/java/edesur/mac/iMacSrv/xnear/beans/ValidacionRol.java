package edesur.mac.iMacSrv.xnear.beans;

import edesur.mac.iMacSrv.xnear.model.response.rolResponse;
import edesur.mac.iMacSrv.xnear.model.response.ListaRoles;
import edesur.mac.iMacSrv.xnear.model.response.Tarea;
import edesur.mac.iMacSrv.xnear.model.internal.rolMac;

import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.*;

public class ValidacionRol {
    private final JdbcClient jdbcClient;
    public ValidacionRol(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public rolResponse validaRol(String sRol){
        StringBuilder sb = new StringBuilder(SEL_VAL_ROL);
        List<Object> params = new ArrayList<>();

        params.add(sRol.trim().toUpperCase());
        Optional <rolResponse> resu = jdbcClient.sql(sb.toString()).params(params).query(rolResponse.class).optional();

        if(resu.isEmpty()){
            rolResponse miResu = new rolResponse("KO", "", "");
            return miResu;
        }

        return resu.get();
    }

    public List<ListaRoles> getRolesRepre(String sRol){
        StringBuilder sb = new StringBuilder(SEL_ROLES_REPRE);
        List<Object> params = new ArrayList<>();

        params.add(sRol.trim().toUpperCase());
        List<ListaRoles> resu = jdbcClient.sql(sb.toString()).params(params).query(ListaRoles.class).list();

        return resu;
    }

    public List<Tarea> getTareas(String sRol){
        StringBuilder sb = new StringBuilder(SEL_TAREAS);
        List<Object> params = new ArrayList<>();

        params.add(sRol.trim().toUpperCase());
        List<Tarea> resu = jdbcClient.sql(sb.toString()).params(params).query(Tarea.class).list();

        return resu;
    }

    public rolMac getDataRol(String sRol){
        StringBuilder sb = new StringBuilder(SEL_ROL_DATA);
        List<Object> params = new ArrayList<>();

        params.add(sRol.trim().toUpperCase());
        rolMac resu = jdbcClient.sql(sb.toString()).params(params).query(rolMac.class).single();

        return resu;
    }

    public String getCarpetaSalida(String procedimiento, String sucursal){
        StringBuilder sb = new StringBuilder(SEL_CARPETA_SALIDA);
        List<Object> params = new ArrayList<>();

        params.add(procedimiento.trim().toUpperCase());
        params.add(sucursal.trim().toUpperCase());

        String resu = jdbcClient.sql(sb.toString()).params(params).query(String.class).single();

        return resu;
    }

    private static final String SEL_VAL_ROL = "SELECT 'OK' resultado, rol, password FROM rol " +
        "WHERE rol = ? " +
        "AND vigencia = 'S' ";

    private static final String SEL_ROLES_REPRE = "SELECT orden, trim(rol_a) rol " +
            "FROM xnear2:repres " +
            "WHERE rol = ? " +
            "ORDER BY orden, rol_a ";

    private static final String SEL_TAREAS = "SELECT m.mensaje, trim(m.referencia) as referencia, " +
            "trim(m.rol_anterior) as rol_anterior, " +
            "trim(m.rol_actual) as rol_actual, " +
            "m.fecha_traspaso, " +
            "'EDESUR-' || m.mensaje as etiqueta, " +
            "m.proced " +
            "FROM xnear2:mensaje m, xnear2:referencia r " +
            "WHERE m.servidor=1 " +
            "AND m.rol_actual = ? " +
            "AND m.estado <> '5' " +
            "AND r.servidor=m.servidor " +
            "AND r.mensaje=m.mensaje " +
            "AND r.carpeta=m.rol_actual " +
            "ORDER BY 1 DESC ";

    private static final String SEL_ROL_DATA = "SELECT r.rol, r.nombre, r.area, r.carpeta_salida, s.sucursal " +
            "FROM rol r, sucar s " +
            "WHERE r.rol = ? " +
            "AND r.vigencia = 'S' " +
            "AND s.area = r.area ";

    private static final String SEL_CARPETA_SALIDA = "SELECT otx_carpeta " +
            "FROM ot_xpro_accion " +
            "WHERE otx_proced = ? " +
            "AND otx_sucursal = ? " +
            "AND otx_accion = '10' ";

}
