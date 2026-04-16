package divEmpresas.controller;

import divEmpresas.core.security.TokenService;
import divEmpresas.dto.security.LoginDTO;
import divEmpresas.dto.security.TokenDTO;
import divEmpresas.dto.user.UsuarioRequestDTO;
import divEmpresas.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final UserService userService;

    @PostMapping("/salvar-usuario")
    public ResponseEntity<?> salvarUser(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO)
    {
        return ResponseEntity.ok(this.userService.salvarUsuario(usuarioRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDTO loginDTO)
    {
        return ResponseEntity.ok(this.userService.login(loginDTO));
    }
}
