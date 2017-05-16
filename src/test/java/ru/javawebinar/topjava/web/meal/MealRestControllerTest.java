package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.Month;

import static java.time.LocalDateTime.of;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static ru.javawebinar.topjava.MealTestData.*;
/**
 * Created by Evgeniy on 16.05.2017.
 */
public class MealRestControllerTest extends AbstractControllerTest{

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/rest/meals/delete/100002").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/rest/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                //How to add ModelAndView: java.lang.AssertionError: No ModelAndView found
                .andExpect(content().string("[{\"id\":100007,\"dateTime\":\"2015-05-31T20:00:00\",\"description\":\"Ужин\",\"calories\":510,\"exceed\":true},{\"id\":100006,\"dateTime\":\"2015-05-31T13:00:00\",\"description\":\"Обед\",\"calories\":1000,\"exceed\":true},{\"id\":100005,\"dateTime\":\"2015-05-31T10:00:00\",\"description\":\"Завтрак\",\"calories\":500,\"exceed\":true},{\"id\":100004,\"dateTime\":\"2015-05-30T20:00:00\",\"description\":\"Ужин\",\"calories\":500,\"exceed\":false},{\"id\":100003,\"dateTime\":\"2015-05-30T13:00:00\",\"description\":\"Обед\",\"calories\":1000,\"exceed\":false},{\"id\":100002,\"dateTime\":\"2015-05-30T10:00:00\",\"description\":\"Завтрак\",\"calories\":500,\"exceed\":false}]"));
    }

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(put("/rest/meals/create").contentType(MediaType.APPLICATION_JSON).content("{\"id\":null,\"dateTime\":\"2015-05-31T20:00:00\",\"description\":\"TestMeal\",\"calories\":510}"))
                //How to add ModelAndView: java.lang.AssertionError: No ModelAndView found
                //.andExpect(model().attribute("meals", hasSize(7)))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":100010,\"dateTime\":\"2015-05-31T20:00:00\",\"description\":\"TestMeal\",\"calories\":510,\"user\":null}"));

    }

    @Test
    public void testUpdate() throws Exception {
        mockMvc.perform(put("/rest/meals/update/100002").contentType(MediaType.APPLICATION_JSON).content("{\"id\":100002,\"dateTime\":\"2015-05-31T20:00:00\",\"description\":\"TestMeal\",\"calories\":510}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get("/rest/meals/getBetween/2015-05-31T09:00/2015-05-31T21:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":100007,\"dateTime\":\"2015-05-31T20:00:00\",\"description\":\"Ужин\",\"calories\":510,\"exceed\":true},{\"id\":100006,\"dateTime\":\"2015-05-31T13:00:00\",\"description\":\"Обед\",\"calories\":1000,\"exceed\":true},{\"id\":100005,\"dateTime\":\"2015-05-31T10:00:00\",\"description\":\"Завтрак\",\"calories\":500,\"exceed\":true}]"));
    }
}