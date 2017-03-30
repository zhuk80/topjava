package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;


/**
 * Created by Evgeniy on 26.03.2017.
 */
public interface MealDao {

    Meal getById(int id);

    void delete(int id);

    List<Meal> getAll();

    Meal save(Meal meal);
}
