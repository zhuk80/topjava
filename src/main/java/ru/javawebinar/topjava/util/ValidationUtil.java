package ru.javawebinar.topjava.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.javawebinar.topjava.HasId;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.util.exception.ValidationException;

import java.util.List;

public class ValidationUtil {
    private ValidationUtil() {
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }

    public static void checkIdConsistent(HasId bean, int id) {
//      http://stackoverflow.com/a/32728226/548473
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalArgumentException(bean + " must be with id=" + id);
        }
    }

    public static ResponseEntity<String> getErrorResponse(BindingResult result) {
        StringBuilder sb = new StringBuilder();
        result.getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("<br>"));
        return new ResponseEntity<>(sb.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /*public static void gerErrorCodesMessages(BindingResult result) {
        List<FieldError> errors = result.getFieldErrors();
        String errorString = "";
        for (FieldError error : errors) {
            errorString = errorString + error.getField() + " " + error.getDefaultMessage() + "<br>";
        }
        throw new ValidationException(errorString);
    }*/

    public static void gerErrorCodesMessagesRest(BindingResult result) {
        List<FieldError> errors = result.getFieldErrors();
        String errorString = "";
        for (FieldError error : errors) {
            errorString = errorString + error.getField() + " " + error.getDefaultMessage() + "; ";
        }
        throw new ValidationException(errorString);
    }

    //    http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}