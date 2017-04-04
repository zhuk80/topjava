package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Controller
public class MealRestController implements MealService {

    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    @Qualifier("mealServiceImpl")
    @Autowired
    private MealService service;

    @Override
    public Meal save(Meal meal) {
        return service.save(meal);
    }

    @Override
    public boolean delete(int id) throws NotFoundException {
        return service.delete(id);
    }

    @Override
    public Meal get(int id) throws NotFoundException {
        return service.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return service.getAll();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        String type = request.getParameter("type");
        String dateFrom = request.getParameter("dateFrom");
        String timeFrom = request.getParameter("timeFrom");
        String dateTo = request.getParameter("dateTo");
        String timeTo = request.getParameter("timeTo");
        //Надо добавить нормальную проверку на валидность даты и времени
        if ("filtered".equalsIgnoreCase(type))
        {
            if ((dateFrom == null && timeFrom == null && dateTo == null && timeTo == null) || (dateFrom == "" && timeFrom == "" && dateTo == "" && timeTo == "")) {
                response.sendRedirect("meals");
            }
            else {
                LocalDateTime dateTimeFrom = LocalDateTime.parse(dateFrom + 'T' + timeFrom);
                LocalDateTime dateTimeTo = LocalDateTime.parse(dateTo + 'T' + timeTo);
                request.setAttribute("meals", MealsUtil.getWithExceeded(getFilteredByDates(dateTimeFrom, dateTimeTo), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
            }
        }
        else {
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));

            LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            save(meal);
            response.sendRedirect("meals");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                LOG.info("Delete {}", id);
                delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = action.equals("create") ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            case "all":
            default:
                LOG.info("getAll");
                request.setAttribute("meals",
                        MealsUtil.getWithExceeded(getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    @Override
    public Collection<Meal> getFilteredByDates(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        return service.getFilteredByDates(dateTimeFrom, dateTimeTo);
    }
}