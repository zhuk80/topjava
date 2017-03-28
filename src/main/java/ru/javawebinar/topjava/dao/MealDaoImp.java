package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by Evgeniy on 26.03.2017.
 */
public class MealDaoImp implements MealDao {

    private AtomicInteger atomicId = new AtomicInteger(1);
    private Map<Integer, Meal> meals = new ConcurrentHashMap<Integer, Meal>();

    public MealDaoImp() {
        meals.put(atomicId.get(), new Meal(getAtomicId().getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        meals.put(atomicId.get(), new Meal(getAtomicId().getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        meals.put(atomicId.get(), new Meal(getAtomicId().getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        meals.put(atomicId.get(), new Meal(getAtomicId().getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        meals.put(atomicId.get(), new Meal(getAtomicId().getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        meals.put(atomicId.get(), new Meal(getAtomicId().getAndIncrement(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public AtomicInteger getAtomicId() {
        return atomicId;
    }

    public Meal getById(int id) {
        return meals.get(id);
    }


    public void delete(int id) {
        meals.remove(id);
    }

    public void update(Meal meal) {
        meals.replace(meal.getId(), meal);
    }

    public void add(Meal meal) {

        meals.put(atomicId.get() - 1, meal);
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> list = new ArrayList<>();
        list.addAll(meals.values());
        return list;
    }

}
