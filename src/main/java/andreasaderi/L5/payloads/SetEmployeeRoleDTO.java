package andreasaderi.L5.payloads;

import andreasaderi.L5.entities.Role;
import jakarta.validation.constraints.NotNull;

public record SetEmployeeRoleDTO(
        @NotNull(message = "Role can't be null or blank")
        Role role
) {
}
