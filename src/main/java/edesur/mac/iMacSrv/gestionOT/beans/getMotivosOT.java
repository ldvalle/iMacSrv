package edesur.mac.iMacSrv.gestionOT.beans;

import edesur.mac.iMacSrv.gestionOT.model.request.MotivosOTsReq;
import edesur.mac.iMacSrv.gestionOT.model.response.MotivosOTsRes;
import edesur.mac.iMacSrv.gestionOT.model.response.FeedBackRes;
import edesur.mac.iMacSrv.gestionOT.utils.Mapeos;
import edesur.mac.iMacSrv.xnear.model.response.Tarea;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class getMotivosOT {
    private final JdbcClient jdbcClient;
    public getMotivosOT(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<MotivosOTsRes> getMotivos(String sPreCodTipo){
        StringBuilder sb = new StringBuilder(SEL_MOTIVOS_OT);
        List<Object> params = new ArrayList<>();

        Mapeos miMap = new Mapeos();
        String sCodTipo = miMap.getCodigoMotivo(sPreCodTipo);

        params.add(sCodTipo.trim().toUpperCase());
        List<MotivosOTsRes> resu = jdbcClient.sql(sb.toString()).params(params).query(MotivosOTsRes.class).list();

        return resu;
    }


    public FeedBackRes ValidaMotivo(String codigo, String procedimiento){
        StringBuilder sb = new StringBuilder(SEL_VAL_MOTIVO);
        List<Object> params = new ArrayList<>();

        FeedBackRes resu = null;
        Mapeos miMap =new Mapeos();
        String nomTabla = miMap.getCodigoMotivo(procedimiento);

        params.add(nomTabla.trim());
        params.add(codigo.trim());
        String resuMotivo="";
        resuMotivo = jdbcClient.sql(sb.toString()).params(params).query(String.class).optional().orElse("X");

        if(resuMotivo.equals("X")){
            resu.setCodResultado("KO");
            resu.setMensaje("El Motivo No Existe");
            return resu;
        }

        resu.setMensaje(resuMotivo);
        resu.setCodResultado("OK");

        return resu;
    }


    public static final String SEL_MOTIVOS_OT = "SELECT TRIM(codigo) codigo, TRIM(descripcion) descripcion, " +
            "TRIM(valor_alf) valor_alf  " +
            "FROM tabla " +
            "WHERE nomtabla = ? " +
            "AND sucursal = '0000' " +
            "AND fecha_activacion <= TODAY " +
            "AND (fecha_desactivac IS NULL OR fecha_desactivac > TODAY) " +
            "ORDER BY descripcion ";

    public static final String SEL_VAL_MOTIVO = "SELECT valor_alf[1,1] valor_alf " +
            "FROM tabla " +
            "WHERE nomtabla = ? " +
            "AND sucursal = '0000' " +
            "AND codigo = ? " +
            "AND fecha_activacion <= TODAY " +
            "AND (fecha_desactivac IS NULL OR fecha_desactivac > TODAY) " +
            "ORDER BY descripcion ";

}
