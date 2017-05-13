package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.AbstractMealController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by Evgeniy on 09.05.2017.
 */
@Controller
@RequestMapping(value = "/meals")
public class MealController extends AbstractMealController {

    public MealController(MealService service) {
        super(service);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String meals(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String meals(HttpServletRequest request) {
        super.create(new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))));
        return "redirect:meals";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String mealCreate(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "meal";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String mealGet(@PathVariable int id, Model model) {
        System.out.println(model.toString());
        model.addAttribute("meal", super.get(id));
        return "meal";
    }

    @RequestMapping(value = "/update/meals", method = RequestMethod.POST)
    public String mealUpdate(HttpServletRequest request) {
        super.update(new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.parseInt(request.getParameter("calories"))),
                Integer.parseInt(request.getParameter("id")));
        return "redirect:/meals";
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String mealDelete(@PathVariable int id) {
        super.delete(id);
        return "redirect:/meals";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String mealFilter(HttpServletRequest request, Model model) {
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }
}
