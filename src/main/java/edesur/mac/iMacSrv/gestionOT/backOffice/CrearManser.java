package edesur.mac.iMacSrv.gestionOT.backOffice;

import edesur.mac.iMacSrv.gestionOT.beans.DataClienteManRet;
import edesur.mac.iMacSrv.gestionOT.beans.getMotivosOT;
import edesur.mac.iMacSrv.gestionOT.model.request.EnviaManserReq;
import edesur.mac.iMacSrv.gestionOT.model.response.FeedBackRes;
import edesur.mac.iMacSrv.gestionOT.model.response.ClienteOTRes;

import edesur.mac.iMacSrv.xnear.model.internal.rolMac;
import edesur.mac.iMacSrv.xnear.model.internal.MsgXnearParam;
import edesur.mac.iMacSrv.xnear.beans.ValidacionRol;

import org.springframework.jdbc.core.simple.JdbcClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


        //-- Crear el Manser y luego generar OT

        return dataOut;
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
