package edesur.mac.iMacSrv.gestionOT.backOffice;

import edesur.mac.iMacSrv.gestionOT.beans.DataClienteManRet;
import edesur.mac.iMacSrv.gestionOT.beans.getMotivosOT;
import edesur.mac.iMacSrv.gestionOT.model.request.EnviaManserReq;
import edesur.mac.iMacSrv.gestionOT.model.response.FeedBackRes;
import edesur.mac.iMacSrv.gestionOT.model.response.ClienteOTRes;
import edesur.mac.iMacSrv.gestionOT.model.internal.OrdenDTO;
import edesur.mac.iMacSrv.gestionOT.backOffice.CrearOrden;

import edesur.mac.iMacSrv.xnear.model.internal.rolMac;
import edesur.mac.iMacSrv.xnear.model.internal.MsgXnearParam;

import edesur.mac.iMacSrv.xnear.beans.ValidacionRol;
import edesur.mac.iMacSrv.xnear.backOffice.MensajeXnear;

import org.springframework.jdbc.core.simple.JdbcClient;
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
    private FeedBackRes GrabarManser(EnviaManserReq regRQ, MsgXnearParam regXn, OrdenDTO regOrden, String viajaSAP){
        FeedBackRes resu = new FeedBackRes();

        resu.setCodResultado("KO");
        resu.setMensaje("Operacion MANSER no iniciada");

        //-- Grabar la Orden

        //-- Grabar tabla RETCLI

        //-- Grabar OT_MAC

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
}
