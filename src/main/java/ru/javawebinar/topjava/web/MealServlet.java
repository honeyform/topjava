package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAOMemoryImpl;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Honey on 27.03.2017.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    public static final String INSERT_OR_EDIT  = "/meal.jsp";
    public static final String PAGE_MEALS = "/meals.jsp";

    private MealDao dao = new MealDAOMemoryImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String forward="";
        String action = request.getParameter("action");
        String idStr = request.getParameter("id");

        if (action.equalsIgnoreCase("edit")){
            if (idStr != null && !idStr.equals("")){
                Integer id = Integer.parseInt(idStr);
                Meal meal = dao.getMealById(id);
                if (meal != null){
                    forward = INSERT_OR_EDIT ;
                    request.setAttribute(INSERT_OR_EDIT , meal);
                }
            }
        } else if (action.equalsIgnoreCase("delete")){
            if (idStr != null && !idStr.equals("")){
                Integer id = Integer.parseInt(idStr);
                dao.delete(id);
                forward = "meals?action=listMeals";
            }
        } else if (action.equalsIgnoreCase("listMeals")){
            LOG.debug("redirect to meals");

            List<MealWithExceed> meals = UserMealsUtil.getFilteredWithExceeded(dao.listMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("meals", meals);

            forward = PAGE_MEALS;
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer idFromRequest = Integer.valueOf(request.getParameter("id"));
        Meal meal;
        if (idFromRequest == null) {
            meal = new Meal(0, null, null, 0);
        } else {
            LocalDateTime dateTime = (LocalDateTime) request.getAttribute("dateTime");
            meal = new Meal(idFromRequest, dateTime, request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        }

        dao.updateOrInsertMeal(meal);
        RequestDispatcher view = request.getRequestDispatcher(PAGE_MEALS);
        List<MealWithExceed> meals = UserMealsUtil.getFilteredWithExceeded(dao.listMeals(), LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("meals", meals);
        view.forward(request, response);

//        Meal meal = new Meal( ? 0 : idFromRequest, );
//        meal.setFirstName(request.getParameter("firstName"));
//        meal.setLastName(request.getParameter("lastName"));
//        try {
//            Date dob = new SimpleDateFormat("MM/dd/yyyy").parse(request.getParameter("dob"));
//            user.setDob(dob);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        user.setEmail(request.getParameter("email"));
//        String userid = request.getParameter("userid");
//        if(userid == null || userid.isEmpty())
//        {
//            dao.addUser(user);
//        }
//        else
//        {
//            user.setUserid(Integer.parseInt(userid));
//            dao.updateUser(user);
//        }
//        RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
//        request.setAttribute("users", dao.getAllUsers());
//        view.forward(request, response);
    }
}
