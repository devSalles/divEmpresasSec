package divEmpresas.controller;

import divEmpresas.dto.security.LoginDTO;
import divEmpresas.dto.user.*;
import divEmpresas.entity.Usuario;
import divEmpresas.service.AuthService;
import divEmpresas.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UsuarioController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/salvar-usuario")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<?> salvarUser(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO)
    {
        return ResponseEntity.ok(this.userService.salvarUsuario(usuarioRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDTO loginDTO)
    {
        return ResponseEntity.ok(this.authService.login(loginDTO));
    }

    @PutMapping("/atualizar-admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> atualizarPerfilAdmin(@PathVariable Long id, @Valid @RequestBody UsuarioAdminUpdateDTO adminUpdateDTO, @AuthenticationPrincipal Usuario userLogado)
    {
        return ResponseEntity.ok(this.userService.atualizarAdmin(id,adminUpdateDTO,userLogado));
    }

    @PutMapping("/atualizar-gerente/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<?> atualizarPerfilGerente(@PathVariable Long id, @Valid @RequestBody UsuarioGerenteUpdateDTO gerenteUpdateDTO, @AuthenticationPrincipal Usuario userLogado)
    {
        return ResponseEntity.ok(this.userService.atualizarGerente(id,gerenteUpdateDTO,userLogado));
    }

    @PutMapping("/atualizar-subordinado/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER')")
    public ResponseEntity<?> atualizarSubordinado(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateRequestDTO usuarioUpdateDTO, @AuthenticationPrincipal Usuario userLogado)
    {
        return ResponseEntity.ok(this.userService.atualizarUsuario(id,usuarioUpdateDTO,userLogado));
    }

    @GetMapping("/buscar-proprio-perfil/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> buscarProprioPerfil(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado)
    {
        return ResponseEntity.ok(this.userService.buscarID(id,usuarioLogado.getOrganizacao().getId()));
    }

    @GetMapping("/listar-todos")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> listarRegistros(@AuthenticationPrincipal Usuario usuarioLogado)
    {
        return ResponseEntity.ok(this.userService.listarTodos(usuarioLogado));
    }

    @DeleteMapping("/deletar-colaborador/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<?> deletarRegistro(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado)
    {
        return ResponseEntity.ok(this.userService.deletarUsuario(id,usuarioLogado));
    }
}
