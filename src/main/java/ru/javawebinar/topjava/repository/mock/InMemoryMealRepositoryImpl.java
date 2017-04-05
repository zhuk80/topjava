package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.UsersUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);


    {
        MealsUtil.MEALS.forEach(meal -> save(meal, UsersUtil.USERS.get(0).getId()));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        //if (repository.get(meal.getId()).isNew() || meal.getUserId() != AuthorizedUser.id())
            //throw new NotFoundException("No such meal for current user");
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        meal.setUserId(userId);
        return repository.put(meal.getId(), meal);
    }


    /*public Meal save(Meal meal) {
        if (repository.get(meal.getId()).isNew() || meal.getUserId() != AuthorizedUser.id())
            throw new NotFoundException("No such meal for current user");
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        return repository.put(meal.getId(), meal);
    }*/

    @Override
    public boolean delete(int id) {
        if (repository.get(id).getUserId() != AuthorizedUser.id())
            throw new NotFoundException("No such meal for current user");
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        if (repository.get(id).getUserId() != AuthorizedUser.id())
            throw new NotFoundException("No such meal for current user");
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        if (repository.values().isEmpty())
            return Collections.emptyList();
        ArrayList<Meal> mealsSorted = new ArrayList<>(repository.values());
        Collections.sort(mealsSorted, (o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        return mealsSorted;
    }

    @Override
    public Collection<MealWithExceed> getFilteredByDates(List<MealWithExceed> mealWithExceeds, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        List<MealWithExceed> filteredMealsByDateTime = new ArrayList<>();
        if (mealWithExceeds.isEmpty())
            return Collections.emptyList();
        for (MealWithExceed meal : mealWithExceeds) {
            if (DateTimeUtil.isBetween(meal.getDateTime(), dateTimeFrom, dateTimeTo)) {
                filteredMealsByDateTime.add(meal);
            }
        }
        return filteredMealsByDateTime;
    }

    @Override
    public Collection<MealWithExceed> getFilteredByTime(List<MealWithExceed> mealWithExceeds, LocalTime timeFromConverted, LocalTime timeToConverted) {
        List<MealWithExceed> filteredMealsByDateTime = new ArrayList<>();
        if (mealWithExceeds.isEmpty())
            return Collections.emptyList();
        for (MealWithExceed meal : mealWithExceeds) {
            if (DateTimeUtil.isBetween(meal.getDateTime().toLocalTime(), timeFromConverted, timeToConverted)) {
                filteredMealsByDateTime.add(meal);
            }
        }
        return filteredMealsByDateTime;
    }
}

