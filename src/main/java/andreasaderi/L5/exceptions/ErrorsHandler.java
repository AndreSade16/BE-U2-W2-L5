package andreasaderi.L5.exceptions;


import andreasaderi.L5.payloads.ErrorsDTO;
import andreasaderi.L5.payloads.ErrorsWithListDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorsHandler {

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorsPayload handleFileNotAllowed(FileNotAllowedException ex) {
//        return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
//    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsDTO handleNotFound(NotFoundException exception) {
        return new ErrorsDTO(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorsDTO handleEmailAlreadyInUse(EmailAlreadyInUseException exception) {
        return new ErrorsDTO(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsDTO handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        return new ErrorsDTO(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(TripAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorsDTO handleTripAlreadyExists(TripAlreadyExistsException exception) {
        return new ErrorsDTO(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsDTO unauthorized(UnauthorizedException exception) {
        return new ErrorsDTO(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsDTO handleNoResourceFound(NoResourceFoundException exception) {
        return new ErrorsDTO(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(EmployeeAlreadyHasBookingForThisDateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorsDTO handleEmployeeAlreadyHasBookingForThisDate(EmployeeAlreadyHasBookingForThisDateException exception) {
        return new ErrorsDTO(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(FileNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsDTO handleFileNotSupported(FileNotSupportedException exception) {
        return new ErrorsDTO(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsDTO handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        return new ErrorsDTO(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorsDTO handleUsernameAlreadyInUse(UsernameAlreadyInUseException exception) {
        return new ErrorsDTO(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsDTO handleGenericError(Exception exception) {
        exception.printStackTrace();
        return new ErrorsDTO("Error", LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsWithListDTO handleValidationError(ValidationException ex) {
        return new ErrorsWithListDTO(ex.getMessage(), LocalDateTime.now(), ex.getErrors());
    }


}
