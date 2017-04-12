package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static org.junit.Assert.*;

/**
 * Created by Evgeniy on 12.04.2017.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(1, USER_ID);
        MATCHER.assertEquals(MEAL1, meal);
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(mealList, service.getAll(USER_ID));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(2, USER_ID);
        mealList.remove(1);
        MATCHER.assertCollectionEquals(mealList, service.getAll(USER_ID));
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        mealList.remove(5);
        mealList.remove(4);
        mealList.remove(3);
        List<Meal> betweenDates = service.getBetweenDates(LocalDate.of(2015, 5, 30), LocalDate.of(2015, 5, 30), USER_ID);
        MATCHER.assertCollectionEquals(mealList, betweenDates);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        mealList.remove(5);
        mealList.remove(4);
        mealList.remove(3);
        mealList.remove(2);
        mealList.remove(0);
        List<Meal> betweenDateTimes = service.getBetweenDateTimes(LocalDateTime.of(2015, 5, 30, 10, 0), LocalDateTime.of(2015, 5, 30, 16, 0), USER_ID);
        MATCHER.assertCollectionEquals(mealList, betweenDateTimes);
    }



    /*@Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testSave() throws Exception {

    }*/
}