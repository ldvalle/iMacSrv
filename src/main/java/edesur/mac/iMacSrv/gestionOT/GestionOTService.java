package edesur.mac.iMacSrv.gestionOT;

import edesur.mac.iMacSrv.gestionOT.model.request.MotivosOTsReq;
import edesur.mac.iMacSrv.gestionOT.model.response.ClienteOTRes;
import edesur.mac.iMacSrv.gestionOT.model.response.MotivosOTsRes;
import edesur.mac.iMacSrv.gestionOT.model.request.NroClienteReq;
import edesur.mac.iMacSrv.gestionOT.model.response.ProcesoPendienteRes;
import edesur.mac.iMacSrv.gestionOT.model.response.MedidorClienteRes;

import edesur.mac.iMacSrv.gestionOT.beans.DataClienteManRet;
import edesur.mac.iMacSrv.gestionOT.beans.MedidorClienteManRet;
import edesur.mac.iMacSrv.gestionOT.beans.getMotivosOT;

import edesur.mac.iMacSrv.gestionOT.utils.Mapeos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.session.JdbcSessionDataSourceScriptDatabaseInitializer;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.IntStream;

@Slf4j
@Service
public class GestionOTService {
    private final JdbcClient jdbcClient;
    public GestionOTService(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public ClienteOTRes getDataCliente(NroClienteReq busqueda){
        DataClienteManRet miSrv = new DataClienteManRet(jdbcClient);
        ClienteOTRes resu = miSrv.getDataCliente(busqueda.nroCliente());

        return resu;
    }

    public MedidorClienteRes getMedidorClienteManRet(NroClienteReq busqueda){
        MedidorClienteManRet miSrv = new MedidorClienteManRet(jdbcClient);
        MedidorClienteRes resu = miSrv.getMedidorClienteManRet(busqueda.nroCliente());

        return resu;
    }

    public List<MotivosOTsRes> getMotivosOT(MotivosOTsReq busqueda){
        getMotivosOT miSrv = new getMotivosOT(jdbcClient);
        List<MotivosOTsRes> resu = miSrv.getMotivos(busqueda.procedimiento());
        return resu;
    }
}
