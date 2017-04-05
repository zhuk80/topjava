package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */

@Controller
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            mealRestController = appCtx.getBean(MealRestController.class);
        }
        //MealServlet.this.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                LOG.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = action.equals("create") ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            case "all":
            default:
                LOG.info("getAll");
                request.setAttribute("meals",
                        MealsUtil.getWithExceeded(mealRestController.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            if ((dateFrom == null && timeFrom == null && dateTo == null && timeTo == null) || (Objects.equals(dateFrom, "") && Objects.equals(timeFrom, "") && Objects.equals(dateTo, "") && Objects.equals(timeTo, ""))) {
                response.sendRedirect("meals");
            }
            else {
                LocalDate dateFromConverted = Objects.equals(dateFrom, "") ? LocalDate.MIN : LocalDate.parse(dateFrom);
                LocalDate dateToConverted = Objects.equals(dateTo, "") ? LocalDate.MAX : LocalDate.parse(dateTo);
                LocalTime timeFromConverted = Objects.equals(timeFrom, "") ? LocalTime.MIN : LocalTime.parse(timeFrom);
                LocalTime timeToConverted = Objects.equals(timeTo, "") ? LocalTime.MAX : LocalTime.parse(timeTo);

                LocalDateTime dateTimeFrom = LocalDateTime.of(dateFromConverted, timeFromConverted);
                LocalDateTime dateTimeTo = LocalDateTime.of(dateToConverted, timeToConverted);

                List<MealWithExceed> withExceeded = MealsUtil.getWithExceeded(mealRestController.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY);

                if (Objects.equals(dateFrom, "") && Objects.equals(dateTo, "")) {
                    request.setAttribute("meals", mealRestController.getFilteredByTime(withExceeded, timeFromConverted, timeToConverted));
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                }else {
                    request.setAttribute("meals", mealRestController.getFilteredByDates(withExceeded, dateTimeFrom, dateTimeTo));
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                }
            }
        }
        else {
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));

            LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            mealRestController.save(meal);
            response.sendRedirect("meals");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
