package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
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

    //for tests
    User user = new User(1, "First", "first@mail.ru", "123", Role.ROLE_ADMIN);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, user.getId()));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        meal.setUserId(userId);
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
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
    public Collection<Meal> getFilteredByDates(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        List<Meal> filteredMealsByDateTime = new ArrayList<>();
        if (repository.values().isEmpty())
            return Collections.emptyList();
        for (Meal meal : repository.values()) {
            if (DateTimeUtil.isBetween(meal.getDateTime(), dateTimeFrom, dateTimeTo)) {
                filteredMealsByDateTime.add(meal);
            }
        }
        return filteredMealsByDateTime;
    }
}

