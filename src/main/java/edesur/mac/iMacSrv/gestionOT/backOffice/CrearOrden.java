package edesur.mac.iMacSrv.gestionOT.backOffice;

import edesur.mac.iMacSrv.xnear.model.internal.MsgCreacion;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EnableTransactionManagement
public class CrearOrden {
    private final JdbcClient jdbcClient;
    public CrearOrden(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Transactional
    public String getNroOrden(String tipo, String area){
        StringBuilder sb = new StringBuilder(SEL_NUMAO);
        List<Object> params = new ArrayList<>();
        String sNroOrden="";
        long iNroOrden;

        params.add(tipo.trim().toUpperCase());
        params.add(area.trim().toUpperCase());

        Optional<String> sValor = jdbcClient.sql(sb.toString()).params(params).query(String.class).optional();




        return sNroOrden;
    }


    public static final String SEL_NUMAO = "SELECT numero FROM numao " +
            "WHERE tipo_orden = ? " +
            "AND area = ? ";

    public static final String INS_NUMAO = "INSERT INTO numao ( " +
            "area, tipo_orden, numero " +
            ") VALUES ( ?, ?, ? ) ";

    public static final String UPD_NUMAO = "UPDATE numao SET " +
            "numero = ? " +
            "WHERE area = ? " +
            "AND tipo_orden = ? ";
}
