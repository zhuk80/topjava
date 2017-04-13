package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.*;

/**
 * Created by Evgeniy on 12.04.2017.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    MealTestData mealTestData;

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
        mealTestData = new MealTestData();
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(1, USER_ID);
        MATCHER.assertEquals(MEAL1, meal);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Meal> mealList = mealTestData.mealList.stream()
                .limit(6)
                .sorted((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()))
                .collect(Collectors.toList());
        MATCHER.assertCollectionEquals(mealList, service.getAll(USER_ID));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(2, USER_ID);
        mealTestData.mealList.remove(1);
        List<Meal> mealList = mealTestData.mealList.stream()
                .limit(5)
                .sorted((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()))
                .collect(Collectors.toList());
        MATCHER.assertCollectionEquals(mealList, service.getAll(USER_ID));
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        mealTestData.mealList.remove(5);
        mealTestData.mealList.remove(4);
        mealTestData.mealList.remove(3);
        List<Meal> mealList = mealTestData.mealList.stream()
                .limit(3)
                .sorted((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()))
                .collect(Collectors.toList());
        List<Meal> betweenDates = service.getBetweenDates(LocalDate.of(2015, 5, 30), LocalDate.of(2015, 5, 30), USER_ID);
        MATCHER.assertCollectionEquals(mealList, betweenDates);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        mealTestData.mealList.remove(5);
        mealTestData.mealList.remove(4);
        mealTestData.mealList.remove(3);
        mealTestData.mealList.remove(2);
        mealTestData.mealList.remove(0);
        List<Meal> mealList = mealTestData.mealList.stream()
                .limit(1)
                .sorted((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()))
                .collect(Collectors.toList());
        List<Meal> betweenDateTimes = service.getBetweenDateTimes(LocalDateTime.of(2015, 5, 30, 10, 0), LocalDateTime.of(2015, 5, 30, 16, 0), USER_ID);
        MATCHER.assertCollectionEquals(mealList, betweenDateTimes);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal meal = new Meal(4, LocalDateTime.of(2015, 5, 30, 9, 0), "Breakfast", 10000);
        service.update(meal, USER_ID);
        mealTestData.mealList.set(3, meal);
        List<Meal> mealList = mealTestData.mealList.stream()
                .limit(6)
                .sorted((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()))
                .collect(Collectors.toList());
        MATCHER.assertCollectionEquals(mealList, service.getAll(USER_ID));
    }

    @Test
    public void testSave() throws Exception {
        Meal meal = new Meal(LocalDateTime.of(2017, 5, 30, 9, 0), "Breakfast", 15);
        service.save(meal, USER_ID);
        mealTestData.mealList.add(0, meal);
        List<Meal> mealList = mealTestData.mealList.stream()
                .limit(7)
                .sorted((o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()))
                .collect(Collectors.toList());
        MATCHER.assertCollectionEquals(mealList, service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteOther() throws Exception {
        service.delete(2, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testGetOther() throws Exception {
        Meal meal = service.get(1, ADMIN_ID);
        MATCHER.assertEquals(MEAL1, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateOther() throws Exception {
        Meal meal = new Meal(4, LocalDateTime.of(2015, 5, 30, 9, 0), "Breakfast", 10000);
        service.update(meal, ADMIN_ID);
    }
}