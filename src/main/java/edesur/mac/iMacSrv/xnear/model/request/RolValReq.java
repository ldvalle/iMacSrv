package edesur.mac.iMacSrv.xnear.model.request;

import jakarta.validation.constraints.NotEmpty;

public record RolValReq(
        @NotEmpty
        String rol,
        String password
) { }
