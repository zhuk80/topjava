package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;


/**
 * Created by Evgeniy on 26.03.2017.
 */
public interface MealDao {

    static Meal getById(int id) {
        return null;
    }

    static void delete(int id) {
    }

    static void update(int id, String description, int calories, LocalDateTime dateTime) {
    }

    static void add(String description, int calories, LocalDateTime dateTime) {
    }
}
