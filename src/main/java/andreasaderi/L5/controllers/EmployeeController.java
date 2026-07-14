package andreasaderi.L5.controllers;

import andreasaderi.L5.entities.Employee;
import andreasaderi.L5.exceptions.ValidationException;
import andreasaderi.L5.payloads.EmployeeDTO;
import andreasaderi.L5.payloads.SetEmployeeRoleDTO;
import andreasaderi.L5.services.EmployeeService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<Employee> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return employeeService.findAll(page, size);
    }


    @GetMapping("/{employeeId}")
    public Employee findById(@PathVariable UUID employeeId) {
        return employeeService.findById(employeeId);
    }

    @PutMapping("/{employeeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Employee findByIdAndUpdate(@PathVariable UUID employeeId, @RequestBody @Validated EmployeeDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return employeeService.findByIdAndUpdate(employeeId, body);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID employeeId) {
        employeeService.findByIdAndDelete(employeeId);
    }

    @PatchMapping("/{employeeId}/avatar")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Employee updateProfilePic(@PathVariable UUID employeeId, @RequestParam("profile_picture") MultipartFile file) {
        return employeeService.updateProfilePic(employeeId, file);
    }

    @PatchMapping("/me/avatar")
    public Employee updateOwnProfilePic(@AuthenticationPrincipal Employee employee, @RequestParam("profile_picture") MultipartFile file) {
        return employeeService.updateProfilePic(employee.getEmployeeId(), file);
    }

    @GetMapping("/me")
    public Employee getOwnProfile(@AuthenticationPrincipal Employee employee) {
        return employeeService.findById(employee.getEmployeeId());
    }

    @PatchMapping("/{employeeId}/role")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Employee setEmployeeRole(@PathVariable UUID employeeId, @RequestBody @Validated SetEmployeeRoleDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return employeeService.setEmployeeRole(employeeId, body);
    }

}
