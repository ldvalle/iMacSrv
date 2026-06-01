package edesur.mac.iMacSrv.gestionOT.backOffice;

import edesur.mac.iMacSrv.gestionOT.beans.DataClienteManRet;
import edesur.mac.iMacSrv.gestionOT.beans.getMotivosOT;
import edesur.mac.iMacSrv.gestionOT.model.request.EnviaManserReq;
import edesur.mac.iMacSrv.gestionOT.model.response.FeedBackRes;
import edesur.mac.iMacSrv.gestionOT.model.response.ClienteOTRes;
import edesur.mac.iMacSrv.gestionOT.model.internal.OrdenDTO;
import edesur.mac.iMacSrv.gestionOT.model.internal.OTMacDTO;
import edesur.mac.iMacSrv.gestionOT.backOffice.CrearOrden;

import edesur.mac.iMacSrv.xnear.model.internal.rolMac;
import edesur.mac.iMacSrv.xnear.model.internal.MsgXnearParam;

import edesur.mac.iMacSrv.xnear.beans.ValidacionRol;
import edesur.mac.iMacSrv.xnear.backOffice.MensajeXnear;

import edesur.mac.iMacSrv.gestionOT.utils.DateTools;
import edesur.mac.iMacSrv.gestionOT.utils.StringTools;

import org.springframework.jdbc.core.simple.JdbcClient;
import java.util.Date;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

public class CrearManser {
    private final JdbcClient jdbcClient;
    public CrearManser(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public FeedBackRes CrearManser(EnviaManserReq dataIn){
        ValidacionRol srvRol = new ValidacionRol(jdbcClient);
        FeedBackRes dataOut=null;
        ClienteOTRes dataCliente = null;
        String viajaSAP="";

        //-- Validar Cliente y proceso pendiente
        DataClienteManRet srvCliente = new DataClienteManRet(jdbcClient);
        dataCliente = srvCliente.getDataCliente(dataIn.nroCliente());
        dataOut = ValidaCliente(dataCliente);
        if(dataOut.getCodResultado().equals("KO")){
            return dataOut;
        }

        //-- Validar Motivo
        dataOut.setCodResultado("");
        dataOut.setMensaje("");
        getMotivosOT srvMotivos = new getMotivosOT(jdbcClient);
        dataOut = srvMotivos.ValidaMotivo(dataIn.codMotivo().trim(), "MANSER");
        if(dataOut.getCodResultado().equals("KO")){
            return dataOut;
        }
        viajaSAP=dataOut.getMensaje().trim();

        MsgXnearParam xnearParam = new MsgXnearParam();
        xnearParam.setProcedimiento("MANSER");
        xnearParam.setEtapa("INICIO");

        //-- Obtener datos del rol origen
        rolMac regRolOrigen = srvRol.getDataRol(dataIn.rolOrigen().toUpperCase().trim());
        xnearParam.setRolOrigen(dataIn.rolOrigen().toUpperCase().trim());
        xnearParam.setAreaRolOrigen(regRolOrigen.getArea());

        //-- Obtener carpeta salida
        xnearParam.setCarpetaDestino(srvRol.getCarpetaSalida("MANSER", dataCliente.getSucursal()));

        //-- obtener datos de la carpeta salida
        rolMac regRolDestino = srvRol.getDataRol(xnearParam.getCarpetaDestino());
        xnearParam.setAreaCarpetaDestino(regRolDestino.getArea());

        String sReferencia = "(MANSER) Cliente: " + dataCliente.getNumero_cliente() + "-" + dataCliente.getDv_numero_cliente();
        xnearParam.setReferencia(sReferencia);

        //-- Obtener Nro.Mensaje Xnear
        MensajeXnear srvMsg = new MensajeXnear(jdbcClient);
        xnearParam.setNroMensaje(srvMsg.getNroMensaje("MANSER"));

        //-- Crear la Orden
        CrearOrden srvOrden = new CrearOrden(jdbcClient);
        String sNroOrden = srvOrden.getNroOrden("MAN", xnearParam.getAreaRolOrigen());
        OrdenDTO regOrden = setOrdenDTO(dataIn, xnearParam, sNroOrden);


        //-- Grabar todas las tablas que definen la OT


        return dataOut;
    }

    private OrdenDTO setOrdenDTO(EnviaManserReq regRQ, MsgXnearParam regXn, String sNroOrden ){
        OrdenDTO reg = new OrdenDTO();
        String codMotivo = regRQ.codMotivo();

        reg.setTipo_orden("MAN");
        reg.setIdent_etapa("RQ");
        //reg.setDuracion("0"); es un DATATIME pero al iniciar le graban un string="0". Lo hare en el query.
        reg.setMensaje_xnear(regXn.getNroMensaje());
        reg.setServidor(1);
        reg.setSucursal(regXn.getAreaRolOrigen());
        reg.setArea_emisora(regXn.getAreaRolOrigen());
        //reg.setFecha_inicio(); ponemos un CURRENT en el query.
        reg.setTerm_dir(regXn.getRolOrigen());
        reg.setArea_ejecutora(regXn.getAreaRolOrigen());
        reg.setEstado("INICIO");
        reg.setRol_usuario(regRQ.rolOrigen().trim().toUpperCase());
        reg.setTema(codMotivo.substring(0,4));
        reg.setTrabajo(codMotivo.substring(4));
        reg.setNumero_cliente(regRQ.nroCliente());

        return reg;
    }

    @Transactional
    private FeedBackRes GrabarManser(EnviaManserReq regRQ, MsgXnearParam regXn, OrdenDTO regOrden, String viajaSAP, ClienteOTRes regCliente){
        FeedBackRes resu = new FeedBackRes();

        resu.setCodResultado("KO");
        resu.setMensaje("Operacion MANSER no iniciada");

        //-- Grabar la Orden
        CrearOrden srvOrden = new CrearOrden(jdbcClient);
        if( ! srvOrden.insertaOrden(regOrden)){
            resu.setMensaje("ERROR Fallo el insert en la tabla ORDEN para el cliente " + regOrden.getNumero_cliente());
            return resu;
        }

        //-- Grabar tabla RETCLI
        if(!grabaRetcli(regOrden.getNumero_cliente())){
            resu.setMensaje("ERROR Fallo el insert en la tabla RETCLI para Cliente " + regOrden.getNumero_cliente());
            return resu;
        }

        //-- Grabar OT_MAC
        OTMacDTO regOTMac = setOtMac(regRQ, regCliente, regXn, viajaSAP);
        CrearOT srvOT = new CrearOT(jdbcClient);

        if(!srvOT.insertOtMac(regOTMac)){
            resu.setMensaje("ERROR Fallo el insert en la tabla OT_MAC para Cliente " + regOrden.getNumero_cliente());
            return resu;
        }

        long lNroOrdenOT = srvOT.getUltOt(regXn.getNroMensaje());
        StringTools srvStr = new StringTools();
        String sNroOrdenSAP = "SC" + srvStr.padLeftZeros( Long.toString(lNroOrdenOT), 10);

        //-- Grabar OT_HISEVEN

        //-- Grabar OT_MAC_SAP u OT_MAC_PEND

        //-- Enviar Mensaje Xnear ver si este es afuera de la transaccion



        return resu;
    }


    private FeedBackRes ValidaCliente(ClienteOTRes reg){
        FeedBackRes resuCliente=null;

        resuCliente.setCodResultado("KO");

        if(reg.getCodigoResultado().equals("KO")){
            resuCliente.setMensaje("Cliente no existe o definido en forma en forma incompleta.");
            return resuCliente;
        }

        if(reg.getMan_ret_pendiente().equals("S")){
            resuCliente.setMensaje("Cliente tiene proceso pendiente.");
            return resuCliente;
        }

        resuCliente.setCodResultado("OK");

        return resuCliente;
    }

    private boolean grabaRetcli(long nroCliente ){

        try {
            jdbcClient.sql(INS_RETCLI).params(nroCliente, "C").update();
        }catch (Exception e){
            System.out.println("ERROR al insertar en tabla RETCLI para Cliente " + nroCliente + "\n" + e.getMessage());
            return false;
        }


        return true;
    }

    private OTMacDTO setOtMac(EnviaManserReq regIN, ClienteOTRes regCli, MsgXnearParam regMsg, String viajaSAP){
        OTMacDTO regOTMac = new OTMacDTO();
        String sTrabajo="";
        LocalDate dFechaVto=null;
        String sTension="";

        if(regIN.codTension().equals("M")){
            sTrabajo="SC01";
        }else{
            sTrabajo="SC02";
        }
        if(regIN.fechaVto()!= null){
            LocalDate fechaHoy = LocalDate.now();

            DateTools srvDate = new DateTools();
            dFechaVto=srvDate.sumaDias(fechaHoy, 10);
        }else{
            dFechaVto=regIN.fechaVto();
        }

        if(regIN.codTension().equals("T")){
            sTension="3";
        }else{
            sTension="1";
        }

        regOTMac.setOt_numero_cliente(regCli.getNumero_cliente());
        regOTMac.setOt_mensaje_xnear(regMsg.getNroMensaje());
        regOTMac.setOt_proced("MANSER");
        regOTMac.setOt_envia_sap(viajaSAP);
        regOTMac.setOt_sucursal_padre(regCli.getSuc_padre());
        regOTMac.setOt_sucursal(regCli.getSucursal());
        regOTMac.setOt_sector(regCli.getSector());
        regOTMac.setOt_zona(regCli.getZona());
        regOTMac.setOt_corr_ruta(regCli.getCorrelativo_ruta());
        regOTMac.setOt_tipo_traba(sTrabajo);
        regOTMac.setOt_area_interloc(regMsg.getAreaCarpetaDestino());
        regOTMac.setOt_motivo(regIN.codMotivo());
        regOTMac.setOt_rol_ejecuta(regMsg.getRolOrigen());
        regOTMac.setOt_area_ejecuta(regMsg.getAreaRolOrigen());
        regOTMac.setOt_potencia(regCli.getPotencia_contrato());
        regOTMac.setOt_tension(sTension);
        regOTMac.setOt_acometida(regCli.getAcometida());
        regOTMac.setOt_toma(regCli.getTipo_empalme());
        regOTMac.setOt_conexion(regCli.getTipo_conexion());
        regOTMac.setOt_fecha_vto(dFechaVto);

        return regOTMac;
    }


    private static final String INS_RETCLI = "INSERT retcli (numero_cliente, codigo )VALUES( ?, ? ) ";


}
