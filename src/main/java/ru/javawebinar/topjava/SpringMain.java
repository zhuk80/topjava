package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.web.meal.MealRestController;


public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {

            System.out.println();
            //System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            //InMemoryMealRepositoryImpl inMemoryMealRepository = (InMemoryMealRepositoryImpl) appCtx.getBean(InMemoryMealRepositoryImpl.class);
            System.out.println();
            //AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            //adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
            System.out.println();
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            //System.out.println(mealRestController.getAll());
        }
    }
}
