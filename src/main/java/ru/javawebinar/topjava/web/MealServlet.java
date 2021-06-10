package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MemoryStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private Storage<Meal> storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MemoryStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.debug("doPost");
        request.setCharacterEncoding("UTF-8");
        String strId = request.getParameter("id");
        Meal meal = new Meal((strId.isEmpty() ? null : Integer.parseInt(strId)),
                LocalDateTime.parse((request.getParameter("dateTime"))),
                request.getParameter("description"), Integer.parseInt(request.getParameter("calories")));
        storage.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action == null ? "default" : action) {
            case "edit":
            case "add":
                log.debug("doGet forward to mealEdit.jsp");
                Meal meal = "edit".equals(action) ? storage.get(Integer.parseInt(request.getParameter("id"))) :
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", MealsUtil.CALORIES_PER_DAY);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
                break;
            case "delete":
                log.debug("doGet redirect to meals.jsp");
                storage.delete(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("meals");
                break;
            case "default":
                log.debug("doGet forward to meals.jsp");
                request.setAttribute("mealsTo", MealsUtil.filteredByStreams(storage.getAll(),
                        LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }
}
