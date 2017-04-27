package webapp;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alexboriskin.university.dao.GroupDao;
import com.alexboriskin.university.dao.GroupDaoSqlImpl;
import com.alexboriskin.university.dao.StudentDaoSqlImpl;
import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Group;

@WebServlet(urlPatterns = "/studentslist.html")
public class StudentsListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LogManager.getLogger();
    private static final boolean STUDENT = true;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        if (action.equalsIgnoreCase("list")) {

            try {
                GroupDao groupDao = new GroupDaoSqlImpl();
                String groupName = request.getParameter("group");
                Group group = groupDao
                        .get(groupDao.getID(new Group(groupName)));
                request.setAttribute("students", group.getStudents());
                request.setAttribute("group", group.getName());
            } catch (DAOException e) {
                log.error("SQL database error: " + e);
            }

            RequestDispatcher view = request
                    .getRequestDispatcher("/WEB-INF/liststudents.jsp");
            try {
                view.forward(request, response);
            } catch (ServletException e) {
                log.error("liststudents.jsp forward error: " + e);
            }
        } else if (action.equalsIgnoreCase("delete")) {
            String firstName = request.getParameter("firstname");
            String lastName = request.getParameter("lastname");
            int zip = Integer.parseInt(request.getParameter("zip"));
            String group = request.getParameter("group");
            StudentDaoSqlImpl studentDao = new StudentDaoSqlImpl();
            try {
                synchronized (studentDao) {
                    studentDao.delete(firstName, lastName, zip, STUDENT);
                }
            } catch (DAOException e) {
                log.error("SQL database access exception: " + e);
            }

            RequestDispatcher view = request
                    .getRequestDispatcher("/controller.html");
            request.setAttribute("group", group);
            request.setAttribute("action", "studentslist");
            try {
                view.forward(request, response);
            } catch (ServletException e) {
                log.error("/controller.html forward error: " + e);
            }
        }
    }
}
