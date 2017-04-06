package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealService {

    Meal get(int userId, int id);

    void delete(int userId, int id);

    Meal save(Meal meal);

    List<MealWithExceed> getFiltered(int id, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);

    List<MealWithExceed> getAll(int id);
}