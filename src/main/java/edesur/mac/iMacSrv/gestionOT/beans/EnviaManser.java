package edesur.mac.iMacSrv.gestionOT.beans;

import edesur.mac.iMacSrv.gestionOT.model.request.EnviaManserReq;
import edesur.mac.iMacSrv.gestionOT.model.response.FeedBackRes;

import edesur.mac.iMacSrv.gestionOT.model.response.ClienteOTRes;

import edesur.mac.iMacSrv.gestionOT.backOffice.CrearManser;

import org.springframework.jdbc.core.simple.JdbcClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnviaManser {
    private final JdbcClient jdbcClient;
    public EnviaManser(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public FeedBackRes EnviaManser(EnviaManserReq dataIn){
        FeedBackRes dataOut=null;
        ClienteOTRes dataCliente = null;
        String viajaSAP="";

        if(dataIn.nroMensaje() > 0){
            //-- Generar OT para caso Preexistente
        }else{
            //Crea el Manser desde cero
            CrearManser srvManser = new CrearManser(jdbcClient);
            dataOut = srvManser.CrearManser(dataIn);
        }

        return dataOut;
    }


}
