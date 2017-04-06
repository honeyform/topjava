package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    @Autowired
    private MealRestController restController;
    private ConfigurableApplicationContext ctx;

    private final static String MEALS_URL = "meals";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ctx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        restController = ctx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String userId = request.getParameter("userId");

        if (userId != null) {
            AuthorizedUser.setId(Integer.parseInt(userId));
            response.sendRedirect(MEALS_URL);
            return;
        }

        if (id != null) {
            // meal editing logic
            Meal meal = new Meal(AuthorizedUser.getId(), (id == null || id.isEmpty()) ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));

            LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);

            if (meal.isNew()) {
                restController.create(meal);
            } else {
                restController.update(meal);
            }
            response.sendRedirect(MEALS_URL);
            return;
        } else {
            // filter logic
            String beginDateStr = request.getParameter("beginDate");
            LOG.info("beginDate=" + beginDateStr);
            String beginTimeStr = request.getParameter("beginTime");
            LOG.info("beginTime=" + beginTimeStr);
            String endDateStr = request.getParameter("endDate");
            LOG.info("endDate=" + endDateStr);
            String endTimeStr = request.getParameter("endTime");
            LOG.info("endTime=" + endTimeStr);

            try {
                LocalDate beginDate = beginDateStr.isEmpty() ? LocalDate.MIN :
                        LocalDate.parse(beginDateStr, DateTimeUtil.DATE_FORMATTER);
                LocalTime beginTime = beginTimeStr.isEmpty() ? LocalTime.MIN :
                        LocalTime.parse(beginTimeStr, DateTimeUtil.TIME_FORMATTER);

                LocalDate endDate = endDateStr.isEmpty() ? LocalDate.MAX :
                        LocalDate.parse(endDateStr, DateTimeUtil.DATE_FORMATTER);
                LocalTime endTime = endTimeStr.isEmpty() ? LocalTime.MAX :
                        LocalTime.parse(endTimeStr, DateTimeUtil.TIME_FORMATTER);

                request.setAttribute("meals",
                        restController.getFiltered(beginDate, endDate, beginTime, endTime));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);

            } catch (Exception e) {
                LOG.error(e.toString());
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
//                int userId = getId(request);
                LOG.info("Delete {}", id);
                restController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                Meal meal = action.equals("create") ?
                        new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        restController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            case "all":
            default:
                LOG.info("getAll");
                    request.setAttribute("meals", restController.getAll());
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

}
