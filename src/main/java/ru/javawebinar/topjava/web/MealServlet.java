package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
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
    private MealDao mealDao;

    public MealServlet() {
        this.mealDao = new MealDaoInMemory();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to meals");

        String action = request.getParameter("action");

        if ("edit".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealDao.getById(id);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/mealEditor.jsp").forward(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            mealDao.delete(id);
            response.sendRedirect("meals");
        } else if ("new".equalsIgnoreCase(action)) {
            Meal meal = new Meal(0, LocalDateTime.now(), "", 0);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/mealEditor.jsp").forward(request, response);
        } else {
            request.setAttribute("Meals", MealsUtil.getFilteredWithExceeded(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("forward to meals");

        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        String description = request.getParameter("description");

        String dateTime = request.getParameter("dateTime");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTimeFormatted = LocalDateTime.parse(dateTime, formatter);

        int calories = Integer.parseInt(request.getParameter("calories"));

        mealDao.save(new Meal(id, dateTimeFormatted, description, calories));
        response.sendRedirect("meals");
    }
}

