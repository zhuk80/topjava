package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DataJpaUserRepositoryImpl implements UserRepository {
    private static final Sort SORT_NAME_EMAIL = new Sort("name", "email");

    @Autowired
    private CrudUserRepository crudRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public User save(User user) {
        return crudRepository.save(user);
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return crudRepository.findOne(id);
    }

    @Override
    public User getByEmail(String email) {
        return crudRepository.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return crudRepository.findAll(SORT_NAME_EMAIL);
    }

    public User getWithMeals(int id)
    {
        List<Meal> mealList = em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("userId", id)
                .getResultList();
        User user = get(id);
        user.setMeals(mealList.isEmpty() ? null : mealList);
        return user;
    }
}
