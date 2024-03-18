package com.example.itmoProject.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
//глобальный обработчик перехватывает выловленные исключения, обрабатывает и готовит ответ клиенту
@Slf4j //логгирование
@Order(Ordered.HIGHEST_PRECEDENCE) //спринговая аннотация, выставляет высокий приеоритет обработки исключений
@RestControllerAdvice //обработка исключений и передача клиенту
public class GlobalException {

    @Bean //создается бин
    public ErrorAttributes errorAttributes() { //метод будет создавать атрибуты для обработки исключений
        //и затем отдать пользователю понятное сообщение
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
                return super.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults()
                        .including(ErrorAttributeOptions.Include.MESSAGE));
            }
        };
    }

    @ExceptionHandler(CustomException.class)
    //мы указываем, что здесь ожидаем тип исключения из класса CustomExc, напоминает маппре - мы класс указываем как тип
    public void handleCustomException(HttpServletResponse response, CustomException ex) throws IOException {
        response.sendError(ex.getStatus().value(), ex.getMessage()); //sendError сам выбрасывает исключения
    }

    //ошибка запросов без параметров, которые требуются
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorMessage> handleMissingParams(MissingServletRequestParameterException ex) { //содержит инфу, какой параметр не передали
        String parameter = ex.getParameterName();

        log.error("{} parameter is missing", parameter);
        return ResponseEntity.status(404)//можно передавать код ошибки
                .body(new ErrorMessage(String.format("parameter is missing: %s", parameter)));//тело ответа
    }

    //тот параметр, который ожидается на сервере, передают не в том формате
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleMissingParams(MethodArgumentTypeMismatchException ex) {
        String parameter = ex.getParameter().getParameterName();

        log.error("wrong data for parameter: {}", parameter);
        return ResponseEntity.status(404)
                .body(new ErrorMessage(String.format("wrong data for parameter: %s", parameter)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMissingParams(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(400)
                .body(new ErrorMessage(ex.getMessage()));
    }

}
