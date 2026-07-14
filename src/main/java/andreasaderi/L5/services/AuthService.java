package andreasaderi.L5.services;

import andreasaderi.L5.entities.Employee;
import andreasaderi.L5.exceptions.UnauthorizedException;
import andreasaderi.L5.payloads.LoginDTO;
import andreasaderi.L5.payloads.LoginResponseDTO;
import andreasaderi.L5.security.JWTTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JWTTools jwtTools;
    private final EmployeeService employeeService;
    private final PasswordEncoder bcrypt;


    public AuthService(EmployeeService employeeService, JWTTools jwtTools, PasswordEncoder bcrypt) {
        this.employeeService = employeeService;
        this.jwtTools = jwtTools;
        this.bcrypt = bcrypt;
    }

    public LoginResponseDTO checkLoginAndGenerateToken(LoginDTO body) {
        Employee employee = employeeService.findByEmail(body.email());

        if (!bcrypt.matches(body.password(), employee.getPassword()))
            throw new UnauthorizedException("Wrong credentials.");

        return new LoginResponseDTO(jwtTools.generateToken(employee));
    }
}
