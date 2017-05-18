package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.MealTestData.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Evgeniy on 16.05.2017.
 */
public class MealRestControllerTest extends AbstractControllerTest {

    @Test
    public void testGet() throws Exception {
        ResultActions result = mockMvc.perform(get("/rest/meals/100002"))
                .andDo(print())
                .andExpect(status().isOk());

        Meal meal = JsonUtil.readValue(result.andReturn().getResponse().getContentAsString(), Meal.class);
        MATCHER.assertEquals(MEAL1, meal);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/rest/meals/100002").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAll() throws Exception {
        ResultActions result = mockMvc.perform(get("/rest/meals"))
                .andDo(print())
                .andExpect(status().isOk());
        List<MealWithExceed> meals = JsonUtil.readValues(result.andReturn().getResponse().getContentAsString(), MealWithExceed.class);
        ModelMatcher<MealWithExceed> matcher = ModelMatcher.of(MealWithExceed.class);
        matcher.assertCollectionEquals(MealsUtil.getWithExceeded(MEALS, 2000), meals);
    }

    @Test
    public void testCreate() throws Exception {
        Meal meal = new Meal(of(2015, Month.MAY, 31, 20, 0), "CreatedMeal", 500);
        ResultActions result = mockMvc.perform(put("/rest/meals").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(meal)))
                .andExpect(status().isCreated());
        Meal createdMeal = JsonUtil.readValue(result.andReturn().getResponse().getContentAsString(), Meal.class);
        meal.setId(100010);
        MATCHER.assertEquals(meal, createdMeal);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal meal = new Meal(of(2015, Month.MAY, 31, 20, 0), "UpdatedMeal", 500);
        meal.setId(100002);
        mockMvc.perform(put("/rest/meals/100002").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(meal)))
                .andExpect(status().isOk());

        ResultActions result = mockMvc.perform(get("/rest/meals/100002"))
                .andDo(print())
                .andExpect(status().isOk());

        Meal updatedMeal = JsonUtil.readValue(result.andReturn().getResponse().getContentAsString(), Meal.class);
        MATCHER.assertEquals(meal, updatedMeal);
    }

    @Test
    public void testGetBetween() throws Exception {
        ResultActions result = mockMvc.perform(get("/rest/meals/getBetween?start=2015-05-31T09:00&end=2015-05-31T21:00"))
                .andDo(print())
                .andExpect(status().isOk());
        List<MealWithExceed> meals = JsonUtil.readValues(result.andReturn().getResponse().getContentAsString(), MealWithExceed.class);
        ModelMatcher<MealWithExceed> matcher = ModelMatcher.of(MealWithExceed.class);
        matcher.assertCollectionEquals(MealsUtil.getWithExceeded(Arrays.asList(MEAL6, MEAL5, MEAL4), 2000), meals);

    }
}