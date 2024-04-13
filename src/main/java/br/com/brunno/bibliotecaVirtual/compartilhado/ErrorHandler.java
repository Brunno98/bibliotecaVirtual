package br.com.brunno.bibliotecaVirtual.compartilhado;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(BindException.class)
    public ProblemDetail handle(BindException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Uma ou mais validacoes falharam");
        problemDetail.setProperty("globalErrors", e.getGlobalErrors());
        problemDetail.setProperty("fieldErrors", e.getFieldErrors());
        return problemDetail;
    }

}
