package edesur.mac.iMacSrv.gestionOT.utils;

import java.util.Date;
import java.util.Calendar;
import java.time.LocalDate;

public class DateTools {

    public LocalDate sumaDias(LocalDate fechaOrigen, int CantDias){
        return fechaOrigen.plusDays(CantDias);
    }

}
