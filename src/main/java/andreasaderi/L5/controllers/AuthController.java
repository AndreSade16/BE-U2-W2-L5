package andreasaderi.L5.controllers;

import andreasaderi.L5.exceptions.ValidationException;
import andreasaderi.L5.payloads.LoginDTO;
import andreasaderi.L5.payloads.LoginResponseDTO;
import andreasaderi.L5.services.AuthService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) throw new ValidationException("Error with login fields validation.");

        return authService.checkLoginAndGenerateToken(body);

    }

}
