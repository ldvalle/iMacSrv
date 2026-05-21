package edesur.mac.iMacSrv.gestionOT;

import edesur.mac.iMacSrv.gestionOT.model.request.MotivosOTsReq;
import edesur.mac.iMacSrv.gestionOT.model.request.NroMensajeReq;
import edesur.mac.iMacSrv.gestionOT.model.response.ClienteOTRes;
import edesur.mac.iMacSrv.gestionOT.model.response.MotivosOTsRes;
import edesur.mac.iMacSrv.gestionOT.model.request.NroClienteReq;
import edesur.mac.iMacSrv.gestionOT.model.request.ClienteMensajeReq;
import edesur.mac.iMacSrv.gestionOT.model.request.MensajeProcedimiento;

import edesur.mac.iMacSrv.gestionOT.model.response.ProcesoPendienteRes;
import edesur.mac.iMacSrv.gestionOT.model.response.MedidorClienteRes;
import edesur.mac.iMacSrv.gestionOT.model.response.DataCabeceraManRet;
import edesur.mac.iMacSrv.gestionOT.model.response.ManserFinalRes;
import edesur.mac.iMacSrv.gestionOT.model.response.RetcliFinalRes;
import edesur.mac.iMacSrv.gestionOT.model.response.TextonRes;
import edesur.mac.iMacSrv.gestionOT.model.response.MedidorRetiradoRes;

import edesur.mac.iMacSrv.gestionOT.beans.DataClienteManRet;
import edesur.mac.iMacSrv.gestionOT.beans.MedidorClienteManRet;
import edesur.mac.iMacSrv.gestionOT.beans.getMotivosOT;
import edesur.mac.iMacSrv.gestionOT.beans.getProcesosPendientes;
import edesur.mac.iMacSrv.gestionOT.beans.GetDataManRet;
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

    public List<ProcesoPendienteRes> getProcesosPendientesCliente(NroClienteReq busqueda){
        getProcesosPendientes miSrv = new getProcesosPendientes(jdbcClient);
        List<ProcesoPendienteRes> resu = miSrv.getProcesosPendientes(busqueda.nroCliente());
        return resu;
    }

    public DataCabeceraManRet getDataCabecera(NroMensajeReq busqueda){
        GetDataManRet miSrv = new GetDataManRet(jdbcClient);
        DataCabeceraManRet resu = miSrv.getDataCabecera(busqueda.nroMensaje());
        return resu;
    }

    public ManserFinalRes getManserFinal(ClienteMensajeReq busqueda){
        GetDataManRet miSrv = new GetDataManRet(jdbcClient);
        ManserFinalRes resu = miSrv.getManserFinal(busqueda.nroCliente(), busqueda.nroMensaje());
        return resu;
    }

    public RetcliFinalRes getRetcliFinal(ClienteMensajeReq busqueda){
        GetDataManRet miSrv = new GetDataManRet(jdbcClient);
        RetcliFinalRes resu = miSrv.getRetcliFinal(busqueda.nroCliente(), busqueda.nroMensaje());
        return resu;
    }

    public List<TextonRes> getTexton(NroMensajeReq busqueda){
        GetDataManRet miSrv = new GetDataManRet(jdbcClient);
        List<TextonRes> resu = miSrv.getTexton(busqueda.nroMensaje());
        return resu;
    }

    public MedidorRetiradoRes getMedidorRetirado(ClienteMensajeReq busqueda){
        GetDataManRet miSrv = new GetDataManRet(jdbcClient);
        MedidorRetiradoRes resu = miSrv.getMedidorRetirado(busqueda.nroCliente(), busqueda.nroMensaje());
        return resu;
    }

    public String getObservaTexton(MensajeProcedimiento busqueda){
        GetDataManRet miSrv = new GetDataManRet(jdbcClient);
        String resu = miSrv.getObservaTexton(busqueda.nroMensaje(), busqueda.procedimiento());
        return resu;
    }
}
