package edesur.mac.iMacSrv.xnear.model.request;

import jakarta.validation.constraints.NotEmpty;

public record TareasReq(
        @NotEmpty
        String rol
) { }

