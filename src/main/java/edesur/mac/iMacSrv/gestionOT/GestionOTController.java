package edesur.mac.iMacSrv.gestionOT;

import edesur.mac.iMacSrv.gestionOT.model.request.MotivosOTsReq;
import edesur.mac.iMacSrv.gestionOT.model.request.NroClienteReq;
import edesur.mac.iMacSrv.gestionOT.model.request.NroMensajeReq;
import edesur.mac.iMacSrv.gestionOT.model.request.ClienteMensajeReq;
import edesur.mac.iMacSrv.gestionOT.model.response.*;

import edesur.mac.iMacSrv.xnear.XnearController;
import edesur.mac.iMacSrv.xnear.XnearService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/iMacSrv/gestionOT")
public class GestionOTController {
    private static final Logger logger = LoggerFactory.getLogger(GestionOTController.class);
    private final GestionOTService otSrv;

    public GestionOTController(GestionOTService otSrv ){
        this.otSrv = otSrv;
    }

    @PostMapping("/getMotivosOT")
    public List<MotivosOTsRes> getMotivosOT(@RequestBody MotivosOTsReq busqueda) {
        List<MotivosOTsRes> lstMotivos = otSrv.getMotivosOT(busqueda);
        return lstMotivos;
    }

    @PostMapping("/getProcesosPendientes")
    public List<ProcesoPendienteRes> getProcesoPendientes(@RequestBody NroClienteReq busqueda){
        List<ProcesoPendienteRes> lstProcesos = otSrv.getProcesosPendientesCliente(busqueda);
        return lstProcesos;
    }

    @PostMapping("/getDataClienteManRet")
    public ClienteOTRes getDataClienteManRet(@RequestBody NroClienteReq busqueda){
        ClienteOTRes miData = otSrv.getDataCliente(busqueda);
        return miData;
    }

    @PostMapping("/getMedidorClienteManRet")
    public MedidorClienteRes getMedidorClienteManRet(@RequestBody NroClienteReq busqueda){
        MedidorClienteRes miData = otSrv.getMedidorClienteManRet(busqueda);
        return miData;
    }

    @PostMapping("/getCabeceraManRet")
    public DataCabeceraManRet getCabeceraManRet(@RequestBody NroMensajeReq busqueda){
        DataCabeceraManRet miData = otSrv.getDataCabecera(busqueda);
        return miData;
    }

    @PostMapping("/getManserFinal")
    public ManserFinalRes getManserFinal(@RequestBody ClienteMensajeReq busqueda){
        ManserFinalRes miData = otSrv.getManserFinal(busqueda);
        return miData;
    }

    @PostMapping("/getRetcliFinal")
    public RetcliFinalRes getRetcliFinal(@RequestBody ClienteMensajeReq busqueda){
        RetcliFinalRes miData = otSrv.getRetcliFinal(busqueda);
        return miData;
    }

    @PostMapping("/getTexton")
    public List<TextonRes> getTexton(@RequestBody NroMensajeReq busqueda){
        List<TextonRes> miData = otSrv.getTexton(busqueda);
        return miData;
    }

}
