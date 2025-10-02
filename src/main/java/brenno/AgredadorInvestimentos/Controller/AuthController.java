package brenno.AgredadorInvestimentos.Controller;

import brenno.AgredadorInvestimentos.Controller.Dto.LoginRequest;
import brenno.AgredadorInvestimentos.Controller.Dto.LoginResponse;
import brenno.AgredadorInvestimentos.Entity.User;
import brenno.AgredadorInvestimentos.Service.JwtService;
import brenno.AgredadorInvestimentos.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Operation(summary = "Fazer login", description = "Autentica um usuário e retorna um token JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        
        // Autentica o usuário usando o AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        // Obtém os detalhes do usuário autenticado
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        // Gera o token JWT
        String token = jwtService.generateToken(userDetails);
        
        // Busca o usuário no banco para obter informações adicionais
        Optional<User> user = userService.getUserByEmail(loginRequest.email());
        
        if (user.isPresent()) {
            LoginResponse response = new LoginResponse(
                    token,
                    "Bearer",
                    user.get().getUserId().toString(),
                    user.get().getUsername()
            );
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.badRequest().build();
    }
}
