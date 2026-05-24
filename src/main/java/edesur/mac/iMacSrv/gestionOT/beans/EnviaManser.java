package edesur.mac.iMacSrv.gestionOT.beans;

import edesur.mac.iMacSrv.gestionOT.model.request.EnviaManserReq;
import edesur.mac.iMacSrv.gestionOT.model.response.FeedBackRes;

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

        if(dataIn.nroMensaje() > 0){
            //-- Generar OT para caso Preexistente
        }else{
            //-- Validar Cliente
            //-- Validar Motivo
            //-- Validar Proceso Pendiente
            //-- Crear el Manser y luego generar OT
        }

        return dataOut;
    }
}
