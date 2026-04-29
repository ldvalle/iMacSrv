package edesur.mac.iMacSrv.gestionOT.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextonRes {
    int pagina;
    String texton;
/*
    void setTexton(String texton){
        if(texton.isEmpty()){
            this.texton=null;
        }else{
            String sLineaNva = "";
            String sLineaOri = texton;
            sLineaNva = sLineaOri.replaceAll("\\r\\n", " ");
            sLineaNva = sLineaNva.replaceAll("\\t", " ");

            this.texton=sLineaNva.trim();
        }
    }

    String getTexton(){
        if(this.texton.isEmpty()){
            return null;
        }else{
            String sLineaNva = "";
            String sLineaOri = this.texton;
            sLineaNva = sLineaOri.replaceAll("\\r\\n", " ");
            sLineaNva = sLineaNva.replaceAll("\\t", " ");

            return sLineaNva.trim();
        }
    }
*/

}
