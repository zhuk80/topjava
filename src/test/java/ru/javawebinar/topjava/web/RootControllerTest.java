package ru.javawebinar.topjava.web;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;
import static ru.javawebinar.topjava.MealTestData.*;

public class RootControllerTest extends AbstractControllerTest {

    @Test
    public void testUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(USER.getName()))
                        )
                )));
    }

    @Test
    public void testMeals() throws Exception {
        /*ModelMatcher<MealWithExceed> matcher = new ModelMatcher<>(MealWithExceed.class, new ModelMatcher.Equality<MealWithExceed>() {
            @Override
            public boolean areEqual(MealWithExceed expected, MealWithExceed actual) {
                return true;
            }
        });
        ResultMatcher resultMatcher = matcher.contentListMatcher(MealsUtil.getWithExceeded(MEALS, 2000));*/
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals", hasSize(6)))
                .andExpect(model().attribute("meals", hasItem(
                        allOf(
                                hasProperty("id", is(MEAL1.getId())),
                                hasProperty("dateTime", is(MEAL1.getDateTime())),
                                hasProperty("description", is(MEAL1.getDescription())),
                                hasProperty("calories", is(MEAL1.getCalories()))
                        )
                )));
                //.andExpect(model().attribute("meals", MealsUtil.getWithExceeded(MEALS, 2000))
                //.andExpect(model().attribute("meals", matcher.contentListMatcher(MealsUtil.getWithExceeded(MEALS, 2000))));

    }
}
