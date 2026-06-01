package edesur.mac.iMacSrv.gestionOT.backOffice;

import edesur.mac.iMacSrv.gestionOT.model.response.ClienteOTRes;
import edesur.mac.iMacSrv.gestionOT.utils.StringTools;
import edesur.mac.iMacSrv.gestionOT.model.internal.OrdenDTO;
import edesur.mac.iMacSrv.gestionOT.model.internal.OTMacDTO;
import edesur.mac.iMacSrv.gestionOT.model.internal.OTHisevenDTO;
import edesur.mac.iMacSrv.gestionOT.model.internal.OTMacSapDTO;


import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CrearOT {
    private final JdbcClient jdbcClient;
    public CrearOT(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public boolean insertOtMac(OTMacDTO reg){
        List<Object> params = new ArrayList<>();

        params.add(reg.getOt_numero_cliente());
        params.add(reg.getOt_mensaje_xnear());
        params.add(reg.getOt_proced());
        params.add(reg.getOt_envia_sap());
        params.add(reg.getOt_sucursal_padre());
        params.add(reg.getOt_sucursal());
        params.add(reg.getOt_sector());
        params.add(reg.getOt_zona());
        params.add(reg.getOt_corr_ruta());
        params.add(reg.getOt_tipo_traba());
        params.add(reg.getOt_area_interloc());
        params.add(reg.getOt_motivo());
        params.add(reg.getOt_rol_ejecuta());
        params.add(reg.getOt_area_ejecuta());
        params.add(reg.getOt_potencia());
        params.add(reg.getOt_tension());
        params.add(reg.getOt_acometida());
        params.add(reg.getOt_toma());
        params.add(reg.getOt_conexion());
        params.add(reg.getOt_fecha_vto());

        try {
            jdbcClient.sql(INS_OT_MAC).params(params).update();
        }catch (Exception e){
            System.out.println("ERROR al insertar en ORDEN para Mensaje " + reg.getOt_mensaje_xnear() + "\n" + e.getMessage());
            return false;
        }

        return true;
    }

    public long getUltOt(long nroMensaje){
        StringBuilder sb = new StringBuilder(SEL_ULT_OT);
        long nroOrden=0;

        try {
            nroOrden = jdbcClient.sql(sb.toString()).params(nroMensaje).query(long.class).single();

        }catch (Exception e){
            System.out.println("ERROR al buscar última OT para Mensaje " + nroMensaje + "\n" + e.getMessage());
            return -1;
        }
        return nroOrden;
    }


    private final static String INS_OT_MAC = "INSERT INTO ot_mac ( " +
            "ot_numero_cliente, " +
            "ot_mensaje_xnear, " +
            "ot_proced, " +
            "ot_envia_sap, " +
            "ot_estado, " +
            "ot_fecha_est, " +
            "ot_status, " +
            "ot_fecha_status, " +
            "ot_fecha_inicio, " +
            "ot_sucursal_padre, " +
            "ot_sucursal, " +
            "ot_sector, " +
            "ot_zona, " +
            "ot_corr_ruta, " +
            "ot_tipo_traba, " +
            "ot_area_interloc, " +
            "ot_motivo, " +
            "ot_rol_ejecuta, " +
            "ot_area_ejecuta, " +
            "ot_potencia, " +
            "ot_tension, " +
            "ot_acometida, " +
            "ot_toma, " +
            "ot_conexion, " +
            "ot_fecha_vto " +
            ")VALUES( " +
            "?,?,?,?, " +
            "'C',CURRENT,'INIC',CURRENT,CURRENT, " +
            "?,?,?,?,?,?,?,?, " +
            "?,?,?,?,?,?,?,?) ";

    private final static String SEL_ULT_OT = "SELECT MAX(ot_nro_orden) FROM ot_mac WHERE ot_mensaje_xnear = ? ";
}
