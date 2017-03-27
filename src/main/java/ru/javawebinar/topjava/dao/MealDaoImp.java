package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by Evgeniy on 26.03.2017.
 */
public class MealDaoImp implements MealDao {

    private static AtomicInteger atomicId = new AtomicInteger(1);
    public static final Map<Integer, Meal> meals = new ConcurrentHashMap<Integer, Meal>();

    static {
        meals.put(getAtomicId().get(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        meals.put(getAtomicId().get(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        meals.put(getAtomicId().get(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        meals.put(getAtomicId().get(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        meals.put(getAtomicId().get(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        meals.put(getAtomicId().get(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public static AtomicInteger getAtomicId() {
        return atomicId;
    }

    public Meal getById(int id) {
        return meals.get(id);
    }


    public void delete(int id) {
        meals.remove(id);
    }

    public void update(Meal meal) {
        Meal mealToUpdate = meals.get(meal.getId());
        mealToUpdate.setDateTime(meal.getDateTime());
        mealToUpdate.setCalories(meal.getCalories());
        mealToUpdate.setDescription(meal.getDescription());
    }

    public void add(Meal meal) {
        meals.put(meal.getId(), meal);
    }

}
