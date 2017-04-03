package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.dao.MealDAOMemoryImpl;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {

//    public static void main(String[] args) {
//        getFilteredWithExceeded(LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
////        .toLocalDate();
////        .toLocalTime();
//    }

    public static List<MealWithExceed>  getFilteredWithExceeded(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
//        List<Meal> mealList = MealDAOMemoryImpl.listMeals();
        Map<LocalDate, Integer> sumCaloriesByDate = mealList.parallelStream()
                .collect(Collectors.groupingBy(um -> um.getDateTime().toLocalDate(), Collectors.summingInt(Meal::getCalories)));

        return mealList.parallelStream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new MealWithExceed(userMeal.getId(), userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories()
                        , sumCaloriesByDate.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
