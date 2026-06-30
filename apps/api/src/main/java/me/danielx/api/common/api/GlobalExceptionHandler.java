package me.danielx.api.common.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.net.URI;
import java.time.Instant;
import me.danielx.api.products.ProductNotFoundException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ProductNotFoundException.class)
  public ProblemDetail handleProductNotFoundException(
      ProductNotFoundException exception, HttpServletRequest request) {
    ProblemDetail problem =
        ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());

    problem.setTitle("Request failed");
    addCommonProperties(problem, request);

    return problem;
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ProblemDetail handleResponseStatusException(
      ResponseStatusException exception, HttpServletRequest request) {
    ProblemDetail problem =
        ProblemDetail.forStatusAndDetail(exception.getStatusCode(), exception.getReason());

    problem.setTitle("Request failed");
    addCommonProperties(problem, request);

    return problem;
  }

  @ExceptionHandler({
    ConstraintViolationException.class,
    MethodArgumentNotValidException.class,
    HandlerMethodValidationException.class
  })
  public ProblemDetail handleValidationException(Exception exception, HttpServletRequest request) {
    ProblemDetail problem =
        ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Request validation failed");

    problem.setTitle("Invalid request");
    addCommonProperties(problem, request);

    return problem;
  }

  @ExceptionHandler({PropertyReferenceException.class, InvalidDataAccessApiUsageException.class})
  public ProblemDetail handleInvalidRequestParameterException(
      Exception exception, HttpServletRequest request) {
    ProblemDetail problem =
        ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid request parameter");

    problem.setTitle("Invalid request");
    addCommonProperties(problem, request);

    return problem;
  }

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleUnexpectedException(Exception exception, HttpServletRequest request) {
    ProblemDetail problem =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");

    problem.setTitle("Internal server error");
    addCommonProperties(problem, request);

    return problem;
  }

  private void addCommonProperties(ProblemDetail problem, HttpServletRequest request) {
    problem.setInstance(URI.create(request.getRequestURI()));
    problem.setProperty("timestamp", Instant.now());
  }
}
