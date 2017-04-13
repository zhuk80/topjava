package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;


    public static final Meal MEAL1 = new Meal(1, LocalDateTime.of(2015,5,30,9,0), "Breakfast", 500);
    public static final Meal MEAL2 = new Meal(2, LocalDateTime.of(2015,5,30,14,0), "Dinner", 1000);
    public static final Meal MEAL3 = new Meal(3, LocalDateTime.of(2015,5,30,19,0), "Lunch", 510);
    public static final Meal MEAL4 = new Meal(4, LocalDateTime.of(2015,5,31,9,0), "Breakfast", 500);
    public static final Meal MEAL5 = new Meal(5, LocalDateTime.of(2015,5,31,14,0), "Dinner", 1000);
    public static final Meal MEAL6 = new Meal(6, LocalDateTime.of(2015,5,31,19,0), "Lunch", 500);

    public static final Meal MEAL7 = new Meal(7, LocalDateTime.of(2015,5,31,14,0), "AdminDinner", 1000);
    public static final Meal MEAL8 = new Meal(8, LocalDateTime.of(2015,5,31,19,0), "AdminLunch", 500);

    public List<Meal> mealList = new ArrayList<>();
    {
        mealList.add(MEAL1);
        mealList.add(MEAL2);
        mealList.add(MEAL3);
        mealList.add(MEAL4);
        mealList.add(MEAL5);
        mealList.add(MEAL6);
        mealList.add(MEAL7);
        mealList.add(MEAL8);
    }

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getCalories(), actual.getCalories()) &&
                    Objects.equals(expected.getDateTime(), actual.getDateTime()) &&
                    Objects.equals(expected.getDescription(), actual.getDescription()) &&
                    Objects.equals(expected.getId(), actual.getId())));

}