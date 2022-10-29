package br.edu.unicesumar.backend.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.edu.unicesumar.backend.config.auth.Roles;
import br.edu.unicesumar.backend.domain.enums.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID usuarioId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @JsonProperty(access = Access.READ_ONLY)
    private Set<Roles> roles = new HashSet<>();

    @NotEmpty(message = "Username não pode ser vazio")
    @Column(unique = true, updatable = false)
    private String username;
    
    @NotEmpty(message = "Password não pode ser vazio")
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;
    
    @Email(message = "Email deve ser válido")
    @NotEmpty(message = "Email não pode ser vazio")
    private String email;

    @NotNull(message = "Ativo não pode ser nulo")
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo de Usuário não pode ser nulo")
    private TipoUsuario tipoUsuario;
       
    @NotEmpty(message = "Foto do Usuário não pode ser vazio")
    @Column(columnDefinition="TEXT")
    private String fotoUsuario;
    
    @NotNull(message = "Endereço não pode ser nulo")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "viajante_id")
    private Viajante viajante;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "agencia_id")
    private Agencia agencia;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    @Builder.Default
    private List<Contato> contato = new ArrayList<>();
 
    
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.concat(roles.stream(), roles.stream()
                .map(Roles::getCompositeRoles).flatMap(Set::stream))
                .collect(Collectors.toSet());
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}
