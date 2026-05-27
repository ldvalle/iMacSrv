package edesur.mac.iMacSrv.xnear.backOffice;

import edesur.mac.iMacSrv.xnear.model.internal.MsgXnearParam;
import edesur.mac.iMacSrv.xnear.model.internal.MsgCreacion;

import org.springframework.jdbc.core.simple.JdbcClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MensajeXnear {
    private final JdbcClient jdbcClient;
    public MensajeXnear(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public long getNroMensaje(String procedimiento){
        StringBuilder sb = new StringBuilder(SEL_NRO_MENSAJE);
        List<Object> params = new ArrayList<>();

        params.add(procedimiento.trim().toUpperCase());

        MsgCreacion resultado = jdbcClient.sql(sb.toString()).params(params).query(MsgCreacion.class).single();

        return resultado.getNroMensaje();
    }

    public String EnviarMensaje(MsgXnearParam msgParam){
        String resu = "";
        StringBuilder sb = new StringBuilder(ENVIAR_MENSAJE);
        List<Object> params = new ArrayList<>();

        params.add(msgParam.getNroMensaje());
        params.add(msgParam.getProcedimiento());
        params.add(msgParam.getEtapa());
        params.add(msgParam.getPrivacidad());
        params.add(msgParam.getUrgencia());
        params.add(msgParam.getEncriptado());
        params.add(msgParam.getReferencia());
        params.add(msgParam.getRolOrigen());
        params.add(msgParam.getRolOrigen());
        params.add(msgParam.getCarpetaDestino());
        params.add(msgParam.getEmpCon());
        params.add(msgParam.getEmpOrg());
        params.add(msgParam.getEmpDest());
        params.add(msgParam.getTexton());

        resu = jdbcClient.sql(sb.toString()).params(params).query(String.class).single();

        return resu;
    }

    public String GrabarMensaje(MsgXnearParam msgParam){
        String resu = "";
        StringBuilder sb = new StringBuilder(GRABAR_MENSAJE);
        List<Object> params = new ArrayList<>();

        params.add(msgParam.getNroMensaje());
        params.add(msgParam.getProcedimiento());
        params.add(msgParam.getEtapa());
        params.add(msgParam.getPrivacidad());
        params.add(msgParam.getUrgencia());
        params.add(msgParam.getEncriptado());
        params.add(msgParam.getReferencia());
        params.add(msgParam.getRolOrigen());
        params.add(msgParam.getRolOrigen());
        params.add(msgParam.getEmpCon());
        params.add(msgParam.getEmpOrg());
        params.add(msgParam.getTexton());

        resu = jdbcClient.sql(sb.toString()).params(params).query(String.class).single();

        return resu;
    }

    public String FinalizarMensaje(long nroMensaje, String rolActual){
        String resu = "";
        StringBuilder sb = new StringBuilder(FINALIZAR_MENSAJE);
        List<Object> params = new ArrayList<>();

        params.add(nroMensaje);
        params.add(rolActual);

        resu = jdbcClient.sql(sb.toString()).params(params).query(String.class).single();

        return resu;
    }

    public String DepurarMensaje(long nroMensaje, String rolActual, String rolDestino){
        String resu = "";
        StringBuilder sb = new StringBuilder(DEPURAR_MENSAJE);
        List<Object> params = new ArrayList<>();

        params.add(nroMensaje);
        params.add(rolActual);
        params.add(rolDestino);

        resu = jdbcClient.sql(sb.toString()).params(params).query(String.class).single();

        return resu;
    }

    public static final String SEL_NRO_MENSAJE ="{call xpro_crear(1, ?) } ";

    public static final String ENVIAR_MENSAJE = "{call xpro_enviar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) } ";

    public static final String GRABAR_MENSAJE = "{ call xpro_guardar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) } ";

    public static final String FINALIZAR_MENSAJE = "{ call xpro_finalizar(?, ?, 1} ";

    public static final String DEPURAR_MENSAJE = "{ call xpro_depurar(?, ?, ?} ";
}
