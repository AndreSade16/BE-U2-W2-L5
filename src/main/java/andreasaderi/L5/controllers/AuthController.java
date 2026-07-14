package andreasaderi.L5.controllers;

import andreasaderi.L5.entities.Employee;
import andreasaderi.L5.exceptions.ValidationException;
import andreasaderi.L5.payloads.EmployeeDTO;
import andreasaderi.L5.payloads.EmployeeResponseDTO;
import andreasaderi.L5.payloads.LoginDTO;
import andreasaderi.L5.payloads.LoginResponseDTO;
import andreasaderi.L5.services.AuthService;
import andreasaderi.L5.services.EmployeeService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public final AuthService authService;
    public final EmployeeService employeeService;

    public AuthController(AuthService authService, EmployeeService employeeService) {
        this.authService = authService;
        this.employeeService = employeeService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) throw new ValidationException("Error with login fields validation.");

        return authService.checkLoginAndGenerateToken(body);

    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponseDTO createEmployee(@RequestBody @Validated EmployeeDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }

        Employee saved = employeeService.save(body);
        return new EmployeeResponseDTO(saved.getEmployeeId());
    }

}
