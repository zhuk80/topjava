package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserFormValidator;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.web.user.AbstractUserController;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class RootController extends AbstractUserController {

    @Autowired
    private UserFormValidator userFormValidator;

    @Autowired
    public RootController(UserService service) {
        super(service);
    }

    @GetMapping("/")
    public String root(Locale locale) {
        return "redirect:meals";
    }

    //    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String users(Locale locale) {
        return "users";
    }

    @GetMapping(value = "/login")
    public String login(Locale locale) {
        return "login";
    }

    @GetMapping("/meals")
    public String meals(Locale locale) {
        return "meals";
    }

    @GetMapping("/profile")
    public String profile(Locale locale) {
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status, Locale locale) {
        if (result.hasErrors()) {
            return "profile";
        } else {
            userFormValidator.validate(userTo, result);
            super.update(userTo, AuthorizedUser.id());
            AuthorizedUser.get().update(userTo);
            status.setComplete();
            return "redirect:meals";
        }
    }

    @GetMapping("/register")
    public String register(ModelMap model, Locale locale) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model, Locale locale) {
        User byEmail = null;
        try {
            byEmail = super.getByMail(userTo.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (byEmail != null && byEmail.getEmail().equals(userTo.getEmail())) throw new DataIntegrityViolationException(result.getFieldError().getField() + " " + result.getFieldError().getDefaultMessage());
        if (result.hasErrors()) {
            model.addAttribute("register", true);
            return "profile";
        } else {
            userFormValidator.validate(userTo, result);
            super.create(UserUtil.createNewFromTo(userTo));
            status.setComplete();
            return "redirect:login?message=app.registered&username=" + userTo.getEmail();
        }
    }
}
