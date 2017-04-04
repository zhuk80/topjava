package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    //for Spring xml configuration
    public void setRepository(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(Meal meal) {
        if (repository.get(meal.getId()).isNew() || meal.getUserId() != AuthorizedUser.id())
            throw new NotFoundException("No such meal for current user");
        return repository.save(meal, AuthorizedUser.id());
    }

    @Override
    public boolean delete(int id) throws NotFoundException {
        if (repository.get(id).getUserId() != AuthorizedUser.id())
            throw new NotFoundException("No such meal for current user");
        return repository.delete(id);
    }

    @Override
    public Meal get(int id) throws NotFoundException {
        if (repository.get(id).getUserId() != AuthorizedUser.id())
            throw new NotFoundException("No such meal for current user");
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getFilteredByDates(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        return repository.getFilteredByDates(dateTimeFrom, dateTimeTo);
    }

    @Override
    public List<Meal> getAll() {
        ArrayList<Meal> userMealList = new ArrayList<>();
        for (Meal meal : repository.getAll()) {
            if (meal.getUserId().equals(AuthorizedUser.id())) {
                userMealList.add(meal);
            }
        }
        return userMealList;
    }
}