package andreasaderi.L5.services;

import andreasaderi.L5.entities.Employee;
import andreasaderi.L5.exceptions.EmailAlreadyInUseException;
import andreasaderi.L5.exceptions.NotFoundException;
import andreasaderi.L5.exceptions.UsernameAlreadyInUseException;
import andreasaderi.L5.payloads.EmployeeDTO;
import andreasaderi.L5.repositories.EmployeeRepository;
import com.cloudinary.Cloudinary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final Cloudinary fileUploader;

    public EmployeeService(EmployeeRepository employeeRepository, Cloudinary fileUploader) {
        this.employeeRepository = employeeRepository;
        this.fileUploader = fileUploader;
    }

    public Page<Employee> findAll(int page, int size) {
        if (size <= 0) size = 10;
        if (size > 30) size = 30;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable);
    }

    public Employee save(EmployeeDTO body) {
        if (employeeRepository.existsByEmail(body.email())) throw new EmailAlreadyInUseException(body.email());
        if (employeeRepository.existsByUsername(body.username()))
            throw new UsernameAlreadyInUseException(body.username());
        Employee newEmployee = new Employee(body.username(), body.name(), body.surname(), body.email());
        return employeeRepository.save(newEmployee);
    }

    public Employee findById(UUID employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(employeeId));
    }
}
