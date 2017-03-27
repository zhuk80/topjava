package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDaoImp;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Evgeniy on 26.03.2017.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private MealDaoImp mealDaoImp;

    public MealServlet() {
        this.mealDaoImp = new MealDaoImp();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to meals");

        String action = request.getParameter("action");

        if ("edit".equalsIgnoreCase(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            Meal meal = mealDaoImp.getById(userId);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/edit.jsp").forward(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            mealDaoImp.delete(userId);
            response.sendRedirect("meals");
        } //else if ("new".equalsIgnoreCase(action)) {
        //request.getRequestDispatcher("/add.jsp").forward(request, response);
        //}
        else {
            generateMainView(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to meals");

        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("userId"));
        String description = request.getParameter("description");

        String dateTime = request.getParameter("dateTime");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTimeFormatted = LocalDateTime.parse(dateTime, formatter);

        int calories = Integer.parseInt(request.getParameter("calories"));

        if (id == 0) {
            mealDaoImp.add(new Meal(dateTimeFormatted, description, calories));
            response.sendRedirect("meals");
        } else {
            Meal meal = mealDaoImp.getById(id);
            meal.setDescription(description);
            meal.setCalories(calories);
            meal.setDateTime(dateTimeFormatted);
            mealDaoImp.update(meal);
            response.sendRedirect("meals");
        }
    }

    private void generateMainView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Meal> list = new ArrayList<Meal>();
        list.addAll(MealDaoImp.meals.values());
        request.setAttribute("Meals", MealsUtil.getFilteredWithExceeded(list, LocalTime.MIN, LocalTime.MAX, 2000));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}

