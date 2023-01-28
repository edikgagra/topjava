package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumByDay = new HashMap<>();
        Map<LocalDate, List<UserMeal>> mealByDay = new HashMap<>();
        List<UserMealWithExcess> result = new ArrayList<>();

        for (UserMeal meal : meals) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            mealByDay.computeIfAbsent(mealDate, localDate -> new ArrayList<>());
            mealByDay.merge(mealDate, Collections.singletonList(meal), (oldValue, newValue) -> {
                oldValue.addAll(newValue);
                return oldValue;
            });
            sumByDay.merge(mealDate, meal.getCalories(), Integer::sum);
        }

        for (Map.Entry<LocalDate, List<UserMeal>> mealForDay : mealByDay.entrySet()) {
            boolean isExcess = sumByDay.getOrDefault(mealForDay.getKey(), 0) > caloriesPerDay;
            for (UserMeal userMeal : mealForDay.getValue()) {
                if (TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                    result.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(),
                            userMeal.getCalories(), isExcess));
                }
            }
        }

        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final Map<LocalDate, IntSummaryStatistics> sumByDay = meals.stream().collect(Collectors.groupingBy(m -> m.getDateTime().toLocalDate(), Collectors.summarizingInt(UserMeal::getCalories)));
        return meals.stream()
                .map(m -> {
                    boolean isExcess = sumByDay.get(m.getDateTime().toLocalDate()).getSum() > caloriesPerDay;
                    return new UserMealWithExcess(m.getDateTime(), m.getDescription(), m.getCalories(), isExcess);
                })
                .filter(m -> TimeUtil.isBetweenHalfOpen(m.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }
}
