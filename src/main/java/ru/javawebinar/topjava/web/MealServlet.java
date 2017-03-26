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


/**
 * Created by Evgeniy on 26.03.2017.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to meals");

        String action = request.getParameter("action");

        if ("edit".equalsIgnoreCase(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            Meal meal = MealDaoImp.getById(userId);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/edit.jsp").forward(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            MealDaoImp.delete(userId);
            response.sendRedirect("meals");
        } else if ("new".equalsIgnoreCase(action)) {
            request.getRequestDispatcher("/add.jsp").forward(request, response);
        } else {
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
            MealDaoImp.add(description, calories, dateTimeFormatted);
            response.sendRedirect("meals");
        } else {
            MealDaoImp.update(id, description, calories, dateTimeFormatted);
            response.sendRedirect("meals");
        }
    }

    private void generateMainView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("Meals", MealsUtil.getFilteredWithExceeded(MealDaoImp.meals, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}

