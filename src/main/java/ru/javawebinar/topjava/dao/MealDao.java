package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Honey on 28.03.2017.
 */
public interface MealDao {
    List<Meal> listMeals();

    void delete(Integer id);

    Meal getMealById(Integer id);

    void updateOrInsertMeal(Meal meal);

}
