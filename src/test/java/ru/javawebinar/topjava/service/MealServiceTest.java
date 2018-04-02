package ru.javawebinar.topjava.service;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    public static List<String> testResults = new ArrayList<>();

    @Autowired
    private MealService service;

    @Rule
    public ServiceTestRule rule = new ServiceTestRule(testResults);

    @ClassRule
    public static final ExternalResource resource = new ExternalResource() {
        @Override
        protected void after() {
            testResults.forEach(System.out::println);
            System.out.println();
        }
    };

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID).stream().peek(meal -> meal.setUser(null)).collect(Collectors.toList()),
                MEAL6, MEAL5, MEAL4, MEAL3, MEAL2
        );
    }

    @Test
    public void deleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(MEAL1_ID, 1);
    }

    @Test
    public void save() throws Exception {
        Meal created = getCreated();
        service.create(created, USER_ID);
        created.setUser(null);
        assertMatch(service.getAll(USER_ID).stream().peek(meal -> meal.setUser(null)).collect(Collectors.toList()),
                created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1
        );
    }

    @Test
    public void get() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        actual.setUser(null);
        assertMatch(actual, ADMIN_MEAL1);
    }

    @Test
    public void getNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        Meal actual = service.get(MEAL1_ID, USER_ID);
        updated.setUser(null);
        actual.setUser(null);
        assertMatch(actual, updated);
    }

    @Test
    public void updateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID).stream()
                .peek(meal -> meal.setUser(null)).collect(Collectors.toList()), MEALS);
    }

    @Test
    public void getBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID).stream()
                .peek(meal -> meal.setUser(null)).collect(Collectors.toList()), MEAL3, MEAL2, MEAL1);
    }
}