package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import java.util.stream.Collectors;


/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );

        for (UserMealWithExceed userMealWithExceed : getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000)) {
            System.out.println(userMealWithExceed.getDateTime() + " " + userMealWithExceed.getDescription() + " " + userMealWithExceed.getCalories());
        }

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> allCaloriesByDay =
                mealList
                        .stream()
                        .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(meal -> allCaloriesByDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay && TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), true))
                .collect(Collectors.toList());


        /*
        То же, только на циклах:

         Map<LocalDate, Integer> allCaloriesByDay = new HashMap<>();


        for (UserMeal userMeal : mealList) {
            LocalDate currentDate = userMeal.getDateTime().toLocalDate();
            allCaloriesByDay.merge(currentDate, userMeal.getCalories(), (a, b) -> a + b);
        }

        List<UserMealWithExceed> result = new ArrayList<UserMealWithExceed>();

        for (UserMeal userMeal : mealList) {
            LocalTime mealListTime = userMeal.getDateTime().toLocalTime();
            if (TimeUtil.isBetween(mealListTime, startTime, endTime) && allCaloriesByDay1.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay) {
                result.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true));
            }
        }

        return result;
}
         */


    }
}
