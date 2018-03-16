package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final Meal USER_MEAL_1 = new Meal(START_SEQ + 7, LocalDateTime.parse("2015-05-31T20:00"), "Ужин", 510);
    public static final Meal USER_MEAL_2 = new Meal(START_SEQ + 6, LocalDateTime.parse("2015-05-31T13:00"), "Обед", 1000);
    public static final Meal USER_MEAL_3 = new Meal(START_SEQ + 5, LocalDateTime.parse("2015-05-31T10:00"), "Завтрак", 500);
    public static final Meal USER_MEAL_4 = new Meal(START_SEQ + 4, LocalDateTime.parse("2015-05-30T20:00"), "Ужин", 500);
    public static final Meal USER_MEAL_5 = new Meal(START_SEQ + 3, LocalDateTime.parse("2015-05-30T13:00"), "Обед", 1000);
    public static final Meal USER_MEAL_6 = new Meal(START_SEQ + 2, LocalDateTime.parse("2015-05-30T10:00"), "Завтрак", 500);

    public static final Meal ADMIN_MEAL_1 = new Meal(START_SEQ + 9, LocalDateTime.parse("2015-06-01T21:00"), "Админ ужин", 1500);
    public static final Meal ADMIN_MEAL_2 = new Meal(START_SEQ + 8, LocalDateTime.parse("2015-06-01T14:00"), "Админ ланч", 510);

    public static List<Meal> getUserMeals() {
        return Arrays.asList(USER_MEAL_1, USER_MEAL_2, USER_MEAL_3, USER_MEAL_4, USER_MEAL_5, USER_MEAL_6);
    }
    public static List<Meal> getAdminMeals() {
        return Arrays.asList(ADMIN_MEAL_1, ADMIN_MEAL_2);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user_id");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields().isEqualTo(expected);
    }
}
