package br.com.desafiobackend.picpay.transacoes.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class Handler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException e){
        List<String> erros= new ArrayList<>();
        BindingResult allErros = e.getBindingResult();
        allErros.getFieldErrors().stream()
                .map(msg-> "campo: "+msg.getField()+" "+msg.getDefaultMessage())
                .forEach(erros::add);
        allErros.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .forEach(erros::add);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }
}
