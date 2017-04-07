package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal, AuthorizedUser.id());
    }

    @Override
    public boolean delete(int id) throws NotFoundException {
        return repository.delete(id);
    }

    @Override
    public Meal get(int id) throws NotFoundException {
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        ArrayList<Meal> userMealList = new ArrayList<>();
        for (Meal meal : repository.getAll(userId)) {
            if (meal.getUserId().equals(AuthorizedUser.id())) {
                userMealList.add(meal);
            }
        }
        return userMealList;
    }

    @Override
    public Collection<MealWithExceed> getFilteredByDates(List<MealWithExceed> mealWithExceeds, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        return repository.getFilteredByDates(mealWithExceeds, dateTimeFrom, dateTimeTo);
    }

    @Override
    public Collection<MealWithExceed> getFilteredByTime(List<MealWithExceed> mealWithExceeds, LocalTime timeFromConverted, LocalTime timeToConverted) {
        return repository.getFilteredByTime(mealWithExceeds, timeFromConverted, timeToConverted);
    }
}