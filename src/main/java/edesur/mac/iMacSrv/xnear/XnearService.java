package edesur.mac.iMacSrv.xnear;

import edesur.mac.iMacSrv.xnear.model.request.RolValReq;

import edesur.mac.iMacSrv.xnear.model.response.rolResponse;
import edesur.mac.iMacSrv.xnear.model.response.ListaRoles;
import edesur.mac.iMacSrv.xnear.model.response.Tarea;

import edesur.mac.iMacSrv.xnear.beans.ValidacionRol;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.session.JdbcSessionDataSourceScriptDatabaseInitializer;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.IntStream;

@Slf4j
@Service
public class XnearService {
    private final JdbcClient jdbcClient;

    public XnearService(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public rolResponse validarRol(RolValReq busqueda){
        ValidacionRol miSrv = new ValidacionRol(jdbcClient);
        rolResponse miResu = miSrv.validaRol(busqueda.rol());

        return miResu;
    }

    public List<ListaRoles> getRolesRepre(RolValReq busqueda){
        ValidacionRol miSrv = new ValidacionRol(jdbcClient);
        List<ListaRoles> miResu = miSrv.getRolesRepre(busqueda.rol());

        return miResu;
    }

    public List<Tarea> getTareas(RolValReq busqueda){
        ValidacionRol miSrv = new ValidacionRol(jdbcClient);
        List<Tarea> miResu = miSrv.getTareas(busqueda.rol());

        return miResu;
    }

}
