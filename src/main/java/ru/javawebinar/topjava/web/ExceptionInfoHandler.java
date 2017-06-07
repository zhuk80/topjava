package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static Logger LOG = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false);
    }

    /*@ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class})
    @ResponseBody
    public ModelAndView conflict(HttpServletRequest req, Exception e) {

        DataIntegrityViolationException exception = new DataIntegrityViolationException("User with this email already present in application");
        LOG.error("Exception at request " + req.getRequestURL(), e);
        ModelAndView mav = new ModelAndView("exception/exception");
        mav.addObject("exception", exception);

        // Interceptor is not invoked, put userTo
        AuthorizedUser authorizedUser = AuthorizedUser.safeGet();
        if (authorizedUser != null) {
            mav.addObject("userTo", authorizedUser.getUserTo());
        }
        return mav;

        //DataIntegrityViolationException exception = new DataIntegrityViolationException("User with this email already present in application");
        //return logAndGetErrorInfo(req, exception, true);
    }*/

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class})
    @ResponseBody
    public ErrorInfo conflict(HttpServletRequest req, Exception e) {
        DataIntegrityViolationException exception = new DataIntegrityViolationException("User with this email already present in application");
        return logAndGetErrorInfo(req, exception, true);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ErrorInfo conflict(HttpServletRequest req, ValidationException e) {
        return logAndGetErrorInfo(req, e, true);
    }

    /*@ResponseStatus(value = HttpStatus.CONFLICT)  // 422
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ErrorInfo conflict(HttpServletRequest req, ConstraintViolationException e) {
        return logAndGetErrorInfo(req, e, true);
    }*/

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ErrorInfo conflict(HttpServletRequest req, BindException e) {
        return logAndGetErrorInfoAjax(req, e, true);
    }


    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorInfo conflict(HttpServletRequest req, MethodArgumentNotValidException e) {
        return logAndGetErrorInfo(req, e, true);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true);
    }

    private static ErrorInfo logAndGetErrorInfoAjax(HttpServletRequest req, Exception e, boolean logException) {

        ResponseEntity<String> errorResponse = null;
        if (e instanceof BindException) {
            errorResponse = ValidationUtil.getErrorResponse((BindException)e);
        }
        if (e instanceof MethodArgumentNotValidException) {
            errorResponse = ValidationUtil.getErrorResponse(((MethodArgumentNotValidException) e).getBindingResult());
        }

        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            LOG.error("Exception at request " + req.getRequestURL(), rootCause);
        } else {
            LOG.warn("Exception at request " + req.getRequestURL() + ": " + rootCause.toString());
        }
        ValidationException ex = new ValidationException(errorResponse.getBody());
        return new ErrorInfo(req.getRequestURL(), ex);
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            LOG.error("Exception at request " + req.getRequestURL(), rootCause);
        } else {
            LOG.warn("Exception at request " + req.getRequestURL() + ": " + rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL(), rootCause);
    }
}