package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    List<Meal> findAll(Integer userId, Sort sort);

    @Query("Select meal FROM Meal meal WHERE meal.id=:id AND meal.user.id=:userId")
    Meal getOne(Integer id, Integer userId);

    //@Transactional
    //@Override
    //@Query("DELETE FROM Meal meal WHERE meal.id=:id AND meal.user.id=:userId")
    //Meal save(Meal meal);

    /*@Transactional
    @Override
    @Query("DELETE FROM Meal meal WHERE meal.id=:id")
    void delete(@Param("id") Integer id);*/

    @Transactional
    @Query("DELETE FROM Meal meal WHERE meal.id=:id AND meal.user.id=:userId")
    int delete(@Param("id") Integer id, @Param("userId") Integer userId);

    Meal getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);

    @Transactional
    @Query("DELETE FROM Meal meal WHERE meal.id=:id AND meal.user.id=:userId")
    Meal save(@Param("meal") Meal meal, @Param("userId") Integer userId);
}
