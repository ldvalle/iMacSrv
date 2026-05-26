package edesur.mac.iMacSrv.gestionOT.backOffice;

import edesur.mac.iMacSrv.gestionOT.beans.DataClienteManRet;
import edesur.mac.iMacSrv.gestionOT.beans.getMotivosOT;
import edesur.mac.iMacSrv.gestionOT.model.request.EnviaManserReq;
import edesur.mac.iMacSrv.gestionOT.model.response.FeedBackRes;

import edesur.mac.iMacSrv.gestionOT.model.response.ClienteOTRes;

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
