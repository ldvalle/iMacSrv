package edesur.mac.iMacSrv.gestionOT.utils;

public class Mapeos {

    public String getCodigoMotivo(String sProced){
        String sCodigo="";
        switch (sProced){
            case "INCORPORACION":
                sCodigo = "OTMOSO";
                break;
            case "RETCLI":
                sCodigo ="OTMORE";
                break;
            case "MANSER":
                sCodigo="OTMOMA";
                break;
            case "M_SEGEN":
                sCodigo = "";
                break;
            default:
                System.out.println("Procedimiento de OT invalido " + sProced);
                System.exit(1);
                break;
        }

        System.out.println("Procedimiento [" + sProced + "] codigo [" + sCodigo + "]");
        return sCodigo;
    }

}
