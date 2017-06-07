package ru.javawebinar.topjava.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

/**
 * Created by evgeniy on 05.06.2017.
 */
@Component
public class UserFormValidator implements Validator {

    private final UserService service;

    private final MealService mealService;

    @Autowired
    public UserFormValidator(UserService service, MealService mealService) {
        this.service = service;
        this.mealService = mealService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz) || User.class.equals(clazz) || Meal.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
       /* if (target instanceof User) {
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
        else */if (target instanceof UserTo) {
            UserTo userTo = (UserTo) target;

            if (AuthorizedUser.safeGet() != null) {
                checkIdConsistent(userTo, AuthorizedUser.id());
                try {
                    service.update(userTo);
                } catch (DataIntegrityViolationException e) {
                    //throw new DataIntegrityViolationException("User with this email already present in application");
                    errors.rejectValue("email", "users.emailExists");
                }
            }
            else {
                User user = UserUtil.createNewFromTo(userTo);
                checkNew(user);
                try {
                service.save(user);
                } catch (DataIntegrityViolationException e) {
                    //throw new DataIntegrityViolationException("User with this email already present in application");
                    errors.rejectValue("email", "users.emailExists");
                }
            }


            //AuthorizedUser.get().update(userTo);

            //service.create(UserUtil.createNewFromTo(userTo));
            /*User byEmail = null;
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
            }*/
        }
        else if (target instanceof Meal) {
            Meal meal = (Meal) target;
            List<Meal> meals = mealService.getAll(AuthorizedUser.id());
            if (meal.getDateTime() != null){
                for (Meal existingMeal : meals) {
                    if (meal.getDateTime().equals(existingMeal.getDateTime())) throw new DataIntegrityViolationException("You already has meal with the same date/time");
                }
            }
        }
    }
}
