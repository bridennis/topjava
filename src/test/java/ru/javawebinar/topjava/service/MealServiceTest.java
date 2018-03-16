package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.now(), "New User meal", 1600);
        Meal created = service.create(newMeal, UserTestData.USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(UserTestData.USER_ID), newMeal, USER_MEAL_1, USER_MEAL_2, USER_MEAL_3, USER_MEAL_4, USER_MEAL_5, USER_MEAL_6);

        newMeal = new Meal(LocalDateTime.now(), "New Admin meal", 1500);
        created = service.create(newMeal, UserTestData.ADMIN_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(UserTestData.ADMIN_ID), newMeal, ADMIN_MEAL_1, ADMIN_MEAL_2);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMealCreate() {
        service.create(new Meal(null, LocalDateTime.parse("2015-05-31T20:00"), "Ужин", 510), UserTestData.USER_ID);
    }

    @Test
    public void delete() {
        service.delete(START_SEQ + 9, UserTestData.ADMIN_ID);
        assertMatch(service.getAll(UserTestData.ADMIN_ID), ADMIN_MEAL_2);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        service.delete(START_SEQ + 100, UserTestData.USER_ID);
    }

    @Test
    public void get() {
        Meal user = service.get(START_SEQ + 7, UserTestData.USER_ID);
        assertMatch(user, USER_MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(START_SEQ + 100, UserTestData.USER_ID);
    }

    @Test
    public void getBetweenDateTimes() {
        assertMatch(service.getBetweenDateTimes(LocalDateTime.parse("2015-06-01T21:00"), LocalDateTime.parse("2015-06-01T21:00"), UserTestData.ADMIN_ID), ADMIN_MEAL_1);
    }

    @Test
    public void update() {
        Meal updated = USER_MEAL_6;
        updated.setDescription("New description");
        updated.setCalories(777);
        service.update(updated, UserTestData.USER_ID);
        assertMatch(service.get(START_SEQ + 2, UserTestData.USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateForeignMeal() {
        service.update(USER_MEAL_4, UserTestData.ADMIN_ID);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(UserTestData.USER_ID);
        assertMatch(all, MealTestData.getUserMeals());
        all = service.getAll(UserTestData.ADMIN_ID);
        assertMatch(all, MealTestData.getAdminMeals());
    }

}