package edesur.mac.iMacSrv.gestionOT.utils;

public class StringTools {
    public String TrimStr(String sCadena){
        return (sCadena!=null) ? sCadena.trim() : null;
    }

    public String getCampo(String sCadena, int iPos, String sC){
        String sCampo="";

        if(sCadena.isEmpty() || sCadena.trim()==""){
            return sCampo;
        }
        String[] vecCadena = sCadena.split(sC);

        if(vecCadena.length < iPos){
            return sCampo;
        }

        sCampo=vecCadena[iPos];

        return sCampo;
    }
}
