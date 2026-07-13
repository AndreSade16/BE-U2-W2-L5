package andreasaderi.L5.services;

import andreasaderi.L5.entities.Employee;
import andreasaderi.L5.exceptions.UnauthorizedException;
import andreasaderi.L5.payloads.LoginDTO;
import andreasaderi.L5.payloads.LoginResponseDTO;
import andreasaderi.L5.security.JWTTools;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JWTTools jwtTools;
    EmployeeService employeeService;

    public AuthService(EmployeeService employeeService, JWTTools jwtTools) {
        this.employeeService = employeeService;
        this.jwtTools = jwtTools;
    }

    public LoginResponseDTO checkLoginAndGenerateToken(LoginDTO body) {
        Employee employee = employeeService.findByEmail(body.email());

        if (!employee.getPassword().equals(body.password())) throw new UnauthorizedException("Wrong credentials.");

        return new LoginResponseDTO(jwtTools.generateToken(employee));
    }
}
