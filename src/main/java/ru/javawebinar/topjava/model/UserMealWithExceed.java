package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = UserMealsUtil.allCaloriesByDay.get(dateTime.toLocalDate()) > UserMealsUtil.CALORIES_PER_DAY;
    }

    @Override
    public String toString() {
        return dateTime + " " + description + " " + calories + " " + exceed;
    }
}
