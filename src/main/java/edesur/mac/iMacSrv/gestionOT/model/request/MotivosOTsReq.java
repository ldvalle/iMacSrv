package edesur.mac.iMacSrv.gestionOT.model.request;

import jakarta.validation.constraints.NotEmpty;

public record MotivosOTsReq(
        @NotEmpty
        String procedimiento
) { }
