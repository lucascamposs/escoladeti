package br.edu.unicesumar.backend.config.auth;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {

//    ROLE_READ_ONLY,
//    ROLE_INTERNAL(ROLE_READ_ONLY),
//    ROLE_TEST(ROLE_INTERNAL),
//    ROLE_ADMIN(ROLE_READ_ONLY, ROLE_INTERNAL, ROLE_TEST);

    ROLE_USER,
    ROLE_COMPANY,
    ROLE_ADMIN(ROLE_USER, ROLE_COMPANY);

    private Roles(Roles... roles) {
        this.compositeRoles = new HashSet<>(Arrays.asList(roles));
    }

    private Set<Roles> compositeRoles = new HashSet<>();

    @Override
    public String getAuthority() {
        return name();
    }

    public Set<Roles> getCompositeRoles() {
        return this.compositeRoles.stream().flatMap(Roles::flatten).collect(Collectors.toSet());
    }

    private static Stream<Roles> flatten(Roles role) {
        return Stream.concat(
                Stream.of(role),
                role.getCompositeRoles().stream().flatMap(Roles::flatten));
    }

}
