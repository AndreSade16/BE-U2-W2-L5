package andreasaderi.L5.security;

import andreasaderi.L5.entities.Employee;
import andreasaderi.L5.exceptions.UnauthorizedException;
import andreasaderi.L5.services.EmployeeService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final JWTTools jwtTools;
    private final EmployeeService employeeService;

    public TokenFilter(JWTTools jwtTools, EmployeeService employeeService) {
        this.jwtTools = jwtTools;
        this.employeeService = employeeService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer "))
            throw new UnauthorizedException("Wrong or missing authorization.");

        String token = auth.replace("Bearer ", "");

        jwtTools.verifyToken(token);

        Employee authenticated = employeeService.findById(jwtTools.getIdFromToken(token));

        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticated, null, authenticated.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
