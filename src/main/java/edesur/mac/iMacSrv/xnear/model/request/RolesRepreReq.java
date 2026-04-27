package edesur.mac.iMacSrv.xnear.model.request;

import jakarta.validation.constraints.NotEmpty;

public record RolesRepreReq(
        @NotEmpty
        String rol
) {
    public boolean tengoRol() {
        return rol != null;
    }
}
