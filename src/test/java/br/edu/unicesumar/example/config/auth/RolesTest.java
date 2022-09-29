package br.edu.unicesumar.example.config.auth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.unicesumar.backend.config.auth.Roles;

class RolesTest {

    @Test
    void whenRolesHasCompositeRolesThenTxpectedGetCompositeRolesRecursivelyTest() {

        Assertions.assertThat(Roles.ROLE_USER.getCompositeRoles())
                .isEmpty();

        Assertions.assertThat(Roles.ROLE_COMPANY.getCompositeRoles())
                .hasSize(1)
                .containsExactlyInAnyOrder(Roles.ROLE_USER);

        Assertions.assertThat(Roles.ROLE_ADMIN.getCompositeRoles())
                .hasSize(2)
                .containsExactlyInAnyOrder(Roles.ROLE_USER, Roles.ROLE_COMPANY);
    }

}
