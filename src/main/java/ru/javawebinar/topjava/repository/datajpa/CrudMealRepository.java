package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Query("Select meal FROM Meal meal WHERE meal.id=:id AND meal.user.id=:userId")
    Meal findOne(@Param("id") Integer id, @Param("userId") Integer userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC")
    List<Meal> getBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal meal WHERE meal.id=:id AND meal.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    List<Meal> findAllByUserIdOrderByDateTimeDesc(int userId);

    @Transactional
    @Override
    Meal save(Meal meal);

    @Query(value = "SELECT meal FROM Meal meal LEFT JOIN FETCH meal.user u where meal.id = :id AND meal.user.id = :userId")
    Meal getWithUser (@Param("id") Integer id, @Param("userId") Integer userId);
}
