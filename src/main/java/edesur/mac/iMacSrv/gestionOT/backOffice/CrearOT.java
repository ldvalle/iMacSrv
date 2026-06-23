package edesur.mac.iMacSrv.gestionOT.backOffice;

import edesur.mac.iMacSrv.gestionOT.model.response.ClienteOTRes;
import edesur.mac.iMacSrv.gestionOT.model.response.MotivosOTsRes;
import edesur.mac.iMacSrv.gestionOT.utils.StringTools;
import edesur.mac.iMacSrv.gestionOT.model.internal.OrdenDTO;
import edesur.mac.iMacSrv.gestionOT.model.internal.OTMacDTO;
import edesur.mac.iMacSrv.gestionOT.model.internal.OTHisevenDTO;
import edesur.mac.iMacSrv.gestionOT.model.internal.OTMacSapDTO;
import edesur.mac.iMacSrv.gestionOT.model.internal.MedidDTO;
import edesur.mac.iMacSrv.gestionOT.model.internal.PrecintosDTO;


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

    public boolean insertOtHiseven(long nroOrden, long nroCliente){

        try {
            jdbcClient.sql(INS_OT_HISEVEN).params(nroOrden, nroCliente).update();
        }catch (Exception e){
            System.out.println("ERROR al insertar en OT_HISEVEN para orden nro. " + nroOrden + "\n" + e.getMessage());
            return false;
        }
        return true;
    }

    private MedidDTO getMedidorManRet(long nroCliente, int estadoCliente){
        MedidDTO regMed=null;
        List<Object> params = new ArrayList<>();

        params.add(nroCliente);

        if(estadoCliente==0){
            StringBuilder sb = new StringBuilder(SEL_MEDID_ACTIVO);
            regMed = jdbcClient.sql(sb.toString()).params(params).query(MedidDTO.class).single();
        }else{
            StringBuilder sb = new StringBuilder(SEL_MEDID_NOACTIVO);
            regMed = jdbcClient.sql(sb.toString()).params(params).query(MedidDTO.class).single();
        }

        return regMed;
    }

    private List<PrecintosDTO> getPrecintos(MedidDTO regMed){
        StringBuilder sb = new StringBuilder(SEL_PRECINTOS);
        List<Object> params = new ArrayList<>();

        params.add(regMed.getNumero_cliente());
        params.add(regMed.getNumero_medidor());
        params.add(regMed.getMarca_medidor());
        params.add(regMed.getNumero_cliente());

        List<PrecintosDTO> resu = jdbcClient.sql(sb.toString()).params(params).query(PrecintosDTO.class).list();

        return resu;
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

    private final static String INS_OT_HISEVEN = "INSERT INTO ot_hiseven ( " +
            "ots_nro_orden, " +
            "ots_numero_cliente, " +
            "ots_status, " +
            "ots_fecha, " +
            "ots_observac, " +
            "ots_fecha_proc " +
            ") VALUES ( " +
            "?,?, " +
            "'INIC', " +
            "CURRENT, " +
            "'INICIADA', " +
            "CURRENT ) ";

    private final static String SEL_MEDID_ACTIVO = "SELECT numero_cliente, numero_medidor, marca_medidor, modelo_medidor " +
            "FROM medid " +
            "WHERE numero_cliente = ? " +
            "AND estado = 'I' ";

    private final static String SEL_MEDID_NOACTIVO = "SELECT m1.numero_cliente, m1.numero_medidor, m1.marca_medidor, m1.modelo_medidor " +
            "FROM medid m1 " +
            "WHERE m1.numero_cliente = ? " +
            "AND m1.fecha_ult_insta = (SELECT MAX(m2.fecha_ult_insta) FROM medid m2 " +
            "   WHERE m2.numero_cliente = m1.numero_cliente) ";


    private final static String SEL_PRECINTOS = "SELECT serie_insta serie_pre, numero_insta nro_pre, codigo_ubicacion cod_ubic " +
            "FROM sellos " +
            "WHERE numero_cliente = ? " +
            "AND numero_medidor = ? " +
            "AND marca_medidor  = ?' " +
            "AND estado_insta = '6' " +
            "AND codigo_ubicacion in (3,4, 7) " +
            "UNION " +
            "SELECT e.serie serie_pre, e.numero_precinto nro_pre, '0' cod_ubic " +
            "FROM prt_precintos e " +
            "WHERE e.numero_cliente = ? " +
            "AND e.estado_actual = '08' " +
            "AND e.fecha_estado = ( SELECT MAX(e2.fecha_estado) " +
            "        FROM prt_precintos e2 " +
            "        WHERE e.numero_cliente = e2.numero_cliente " +
            "        AND e.estado_actual = e2.estado_actual ) ";
}
