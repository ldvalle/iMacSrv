package edesur.mac.iMacSrv.xnear;

import edesur.mac.iMacSrv.xnear.model.request.RolValReq;

import edesur.mac.iMacSrv.xnear.model.response.rolResponse;
import edesur.mac.iMacSrv.xnear.model.response.ListaRoles;
import edesur.mac.iMacSrv.xnear.model.response.Tarea;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/iMacSrv/xnear")
public class XnearController {
    private static final Logger logger = LoggerFactory.getLogger(XnearController.class);
    private final XnearService xnSrv;

    public XnearController(XnearService xnSrv ){
        this.xnSrv = xnSrv;
    }

    @PostMapping("/ValidarRol")
    public rolResponse valRol(@RequestBody RolValReq busqueda) {
        logger.debug("Valida rol: {}", busqueda.rol());
        rolResponse miRol = xnSrv.validarRol(busqueda);
        logger.debug("Rol: {}", miRol);
        return miRol;
    }

    @PostMapping("/ListaRolRepre")
    public List<ListaRoles> getRolesRepre(@RequestBody RolValReq busqueda){
        List<ListaRoles> miLista = xnSrv.getRolesRepre(busqueda);
        return miLista;
    }

    @PostMapping("/ListaTareas")
    public List<Tarea> getTareas(@RequestBody RolValReq busqueda){
        List<Tarea> miLista = xnSrv.getTareas(busqueda);
        return miLista;
    }
}
