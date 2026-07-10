package andreasaderi.L5.services;

import andreasaderi.L5.entities.Employee;
import andreasaderi.L5.exceptions.EmailAlreadyInUseException;
import andreasaderi.L5.exceptions.FileNotSupportedException;
import andreasaderi.L5.exceptions.NotFoundException;
import andreasaderi.L5.exceptions.UsernameAlreadyInUseException;
import andreasaderi.L5.payloads.EmployeeDTO;
import andreasaderi.L5.repositories.EmployeeRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
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

    public Employee findByIdAndUpdate(UUID employeeId, EmployeeDTO body) {
        Employee found = findById(employeeId);
        if (!found.getEmail().equals(body.email())) {
            if (employeeRepository.existsByEmail(body.email())) throw new EmailAlreadyInUseException(body.email());
        }
        if (employeeRepository.existsByUsername(body.username()))
            throw new UsernameAlreadyInUseException(body.username());

        if (Objects.equals(found.getProfilePicUrl(), "https://ui-avatars.com/api/?name=" + found.getName() + "+" + found.getSurname()) && (!Objects.equals(found.getName(), body.name()) || !Objects.equals(found.getSurname(), body.surname()))) {
            found.setProfilePicUrl("https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
        }

        found.setUsername(body.username());
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setEmail(body.email());

        return employeeRepository.save(found);
    }

    public void findByIdAndDelete(UUID employeeId) {
        Employee found = findById(employeeId);
        employeeRepository.delete(found);
    }

    public Employee updateProfilePic(UUID employeeId, MultipartFile file) {
        if (file.getSize() > 10485760) throw new FileNotSupportedException("File's size can't be more than 10MB");
        if (!(Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/png")))
            throw new FileNotSupportedException("Only jpeg or png images allowed");
        Employee employee = findById(employeeId);

        try {
            Map result = fileUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = (String) result.get("secure_url");

            employee.setProfilePicUrl(url);
            employeeRepository.save(employee);

            return employee;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
