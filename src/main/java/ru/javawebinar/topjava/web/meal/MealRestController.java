package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal get(int id) {
        LOG.info("get " + id);
        return service.get(AuthorizedUser.getId(), id);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(AuthorizedUser.getId(), id);
    }

    public Meal create(Meal meal) {
        LOG.info("create " + meal);
//        meal.isNew();
//        check(meal);
        return service.save(meal);
    }

    public List<MealWithExceed> getFiltered(LocalDate startDate, LocalDate endDate,
                                            LocalTime startTime, LocalTime endTime) {
        LOG.info("getFiltered");
        return service.getFiltered(AuthorizedUser.getId(), startDate, endDate, startTime, endTime);
    }

    public void update(Meal meal) {
        LOG.info("update " + meal);
        service.save(meal);
    }

    public List<MealWithExceed> getAll() {
        LOG.info("getAll");
        return service.getAll(AuthorizedUser.getId());
    }
}