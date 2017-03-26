package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by Evgeniy on 26.03.2017.
 */
public class MealDaoImp implements MealDao {

    private static AtomicInteger atomicId = new AtomicInteger(1);


    public static AtomicInteger getAtomicId() {
        return atomicId;
    }

    public static List<Meal> mealsTmp = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );
    public static final List<Meal> meals = new CopyOnWriteArrayList<>(mealsTmp);
    

    public static Meal getById(int id) {

        Meal result = null;

        for (Meal meal : meals) {
            if (meal.getId() == id) result = meal;
        }
        return result;
    }

    public static void delete(int id) {
        meals.remove(getById(id));
    }

    public static void update(int id, String description, int calories, LocalDateTime dateTime) {
        Meal meal = getById(id);
        meal.setDescription(description);
        meal.setCalories(calories);
        meal.setDateTime(dateTime);
    }

    public static void add(String description, int calories, LocalDateTime dateTime) {
        meals.add(new Meal(dateTime, description, calories));
    }

}
