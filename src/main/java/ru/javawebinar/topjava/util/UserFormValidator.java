package ru.javawebinar.topjava.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.User;

import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;

/**
 * Created by evgeniy on 05.06.2017.
 */
@Component
public class UserFormValidator implements Validator {

    private final UserService service;

    @Autowired
    public UserFormValidator(UserService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz) || User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof User) {
            User user = (User) target;
            User byEmail = null;
            try {
                byEmail = service.getByEmail(user.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (byEmail != null && !user.isNew() && !user.getId().equals(byEmail.getId())) {
                throw new DataIntegrityViolationException("User with this email already present in application");
            }
        }
        else if (target instanceof UserTo) {
            UserTo userTo = (UserTo) target;
            User byEmail = null;
            try {
                byEmail = service.getByEmail(userTo.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (byEmail != null) {
                if (!userTo.isNew() && !userTo.getId().equals(byEmail.getId())) {
                    throw new DataIntegrityViolationException("User with this email already present in application");
                }
            }
            if (byEmail != null && userTo.isNew() && AuthorizedUser.safeGet() != null && !service.getByEmail(userTo.getEmail()).getId().equals(AuthorizedUser.id())) {
                throw new DataIntegrityViolationException("User with this email already present in application");
            }
        }
    }
}
