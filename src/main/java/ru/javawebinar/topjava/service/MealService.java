package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface MealService {

    Meal save (Meal meal);

    boolean delete (int id) throws NotFoundException;

    Meal get(int id) throws NotFoundException;

    List<Meal> getAll();

    Collection<Meal> getFilteredByDates(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);
}