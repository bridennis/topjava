package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Controller
public class MealRestController extends AbstractMealController {

    @Override
    public Collection<Meal> getAll() {
        return super.getAll(LocalDateTime.MIN, LocalDateTime.MAX);
    }

    @Override
    public List<Meal> getAll(LocalDateTime dateTimeStart, LocalDateTime dateTimeStop) {
        return super.getAll(dateTimeStart, dateTimeStop);
    }

    @Override
    public Meal get(int id) {
        return super.get(id);
    }

    @Override
    public Meal create(Meal meal) {
        return super.create(meal);
    }

    @Override
    public void delete(int id) {
        super.delete(id);
    }

    @Override
    public void update(Meal meal, int id) {
        super.update(meal, id);
    }

}