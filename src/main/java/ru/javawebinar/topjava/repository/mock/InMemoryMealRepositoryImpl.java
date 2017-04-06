package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        LOG.info("saving meal " + meal + " for user with id ");
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        LOG.info("deleting meal " + id);
        if(id <= 0 || get(userId, id).equals(null)){
            LOG.info("deleting Meal FAILED: there is no such meal or there is no access to this meal for user");
            return false;
        } else {
            repository.remove(id);
            LOG.info("deleting User SUCCEEDED");
            return true;
        }
    }

    @Override
    public Meal get(int userId, int id) {
        LOG.info("getting meal " + id);
        if (repository.get(id).getUserId() != userId){
            LOG.error("getting meal ERROR! User with id " + userId + " can't access meal with id " + id);
            return null;
        }
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        LOG.info("getAll meals for user with id " + userId);
        List<Meal> meals = repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        return meals.isEmpty() ? Collections.emptyList() : meals;
    }
}

