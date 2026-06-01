package edesur.mac.iMacSrv.gestionOT.backOffice;

import edesur.mac.iMacSrv.gestionOT.utils.StringTools;
import edesur.mac.iMacSrv.gestionOT.model.internal.OrdenDTO;
import edesur.mac.iMacSrv.gestionOT.model.internal.OTMacDTO;
import edesur.mac.iMacSrv.gestionOT.model.internal.OTHisevenDTO;
import edesur.mac.iMacSrv.gestionOT.model.internal.OTMacSapDTO;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CrearOT {
    private final JdbcClient jdbcClient;
    public CrearOT(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }


}
