package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {

    Meal save(Meal Meal, Integer userId);

    Meal save(Meal Meal);

    boolean delete(int id);

    Meal get(int id);

    Collection<Meal> getAll();

    Collection<Meal> getFilteredByDates(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);
}
