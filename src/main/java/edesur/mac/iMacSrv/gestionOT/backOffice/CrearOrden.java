package edesur.mac.iMacSrv.gestionOT.backOffice;

import edesur.mac.iMacSrv.gestionOT.utils.StringTools;
import edesur.mac.iMacSrv.gestionOT.model.internal.OrdenDTO;
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
        String sNvoNroOrden="";
        long iNroOrden;

        StringTools uStr = new StringTools();

        params.add(tipo.trim().toUpperCase());
        params.add(area.trim().toUpperCase());

        Optional<String> sValor = jdbcClient.sql(sb.toString()).params(params).query(String.class).optional();

        if(sValor.isEmpty()){
            sNroOrden = area.trim() + "000001";
            sNvoNroOrden = area.trim() + "000002";

            params.add(area);
            params.add(tipo);
            params.add(sNvoNroOrden);
            try {
                jdbcClient.sql(INS_NUMAO).params(params).update();
            }catch (Exception e){
                System.out.println(e.getMessage());
                sNroOrden="X";
            }

        }else{
            String sNro= sValor.toString().substring(5);
            iNroOrden = Long.parseLong(sNro);
            sNroOrden = sNro;
            iNroOrden++;
            sNvoNroOrden = area + uStr.padLeftZeros( Long.toString(iNroOrden),6);

            params.add(sNvoNroOrden);
            params.add(area);
            params.add(tipo);
            try {
                jdbcClient.sql(UPD_NUMAO).params(params).update();
            }catch (Exception e){
                System.out.println(e.getMessage());
                sNroOrden="X";
            }

        }
        return sNroOrden;
    }

    public boolean insertaOrden(OrdenDTO reg){
        List<Object> params = new ArrayList<>();

        params.add(reg.getTipo_orden());
        params.add(reg.getNumero_orden());
        params.add(reg.getMensaje_xnear());
        params.add(reg.getSucursal());
        params.add(reg.getArea_emisora());
        params.add(reg.getRol_usuario());
        params.add(reg.getTema());
        params.add(reg.getTrabajo());
        params.add(reg.getNumero_cliente());

        try {
            jdbcClient.sql(INS_ORDEN).params(params).update();
        }catch (Exception e){
            System.out.println("ERROR al insertar en ORDEN para Mensaje " + reg.getSfc_nro_orden() + "\n" + e.getMessage());
            return false;
        }

        return true;
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

    public static final String INS_ORDEN = "INSERT INTO orden ( " +
            "tipo_orden, " +
            "numero_orden, " +
            "mensaje_xnear, " +
            "servidor, " +
            "sucursal, " +
            "area_emisora, " +
            "fecha_inicio, " +
            "ident_etapa, " +
            "term_dir, " +
            "area_ejecutora, " +
            "rol_usuario, " +
            "tema, " +
            "trabajo, " +
            "numero_cliente " +
            ") VALUES ( ?, ?, ?, 1, ?, ?, " +
            "CURRENT, 'RQ', ?, ?, ?, ?, ?, ? ) ";

}
