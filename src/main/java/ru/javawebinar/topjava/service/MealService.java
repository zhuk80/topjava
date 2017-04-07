package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealService {

    Meal save (Meal meal);

    boolean delete (int id) throws NotFoundException;

    Meal get(int id) throws NotFoundException;

    List<Meal> getAll(int userId);

    Collection<MealWithExceed> getFilteredByDates(List<MealWithExceed> mealWithExceeds, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);

    Collection<MealWithExceed> getFilteredByTime(List<MealWithExceed> mealWithExceeds, LocalTime timeFromConverted, LocalTime timeToConverted);
}