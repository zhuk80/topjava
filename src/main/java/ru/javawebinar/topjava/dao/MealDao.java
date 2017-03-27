package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;


/**
 * Created by Evgeniy on 26.03.2017.
 */
public interface MealDao {

    Meal getById(int id);

    void delete(int id);

    void update(Meal meal);

    void add(Meal meal);
}
