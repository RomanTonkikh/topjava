package ru.javawebinar.topjava.storage;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealMapStorage implements Storage<Meal> {
    public static final int CALORIES_PER_DAY = 2000;
    private static final Logger log = getLogger(MealMapStorage.class);
    private static final AtomicInteger counter = new AtomicInteger(0);
    public static List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Полдник", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );
    private final Map<Integer, Meal> storage = new HashMap<>();

    {
        meals.forEach(this::add);
    }

    @Override
    public void delete(int id) {
        log.info("delete");
        storage.remove(id);
    }

    @Override
    public Meal add(Meal meal) {
        log.info("add");
        if (meal.getId() == null) {
            meal.setId(counter.incrementAndGet());
            storage.put(meal.getId(), meal);
            return meal;
        }
        return storage.compute(meal.getId(), (id, replaceableMeal) -> meal);
    }

    @Override
    public Meal get(int id) {
        log.info("get");
        return storage.get(id);
    }

    @Override
    public List<Meal> getAll() {
        log.info("getAll");
        return new ArrayList<>(storage.values());
    }
}
