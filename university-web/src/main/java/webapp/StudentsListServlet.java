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
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.alexboriskin.university.dao.ApplicationContextProvider;
import com.alexboriskin.university.dao.GroupDao;
import com.alexboriskin.university.dao.StudentDao;
import com.alexboriskin.university.dao.StudentService;
import com.alexboriskin.university.domain.Group;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/studentslist.html")
public class StudentsListServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }
        
        System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ:  " + action);

        if (action.equalsIgnoreCase("list")) {

            ApplicationContext context = ApplicationContextProvider
                    .getInstance();
            GroupDao groupDao = (GroupDao) context.getBean("groupDao");

            String groupName = request.getParameter("group");
            Group group = groupDao.getByName(groupName);
            request.setAttribute("students", group.getStudents());
            request.setAttribute("group", group.getName());

            RequestDispatcher view = request
                    .getRequestDispatcher("/WEB-INF/liststudents.jsp");
            try {
                view.forward(request, response);
            } catch (ServletException e) {
                log.error("liststudents.jsp forward error: " + e);
            }
        } else if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String group = request.getParameter("group");
            ApplicationContext context = ApplicationContextProvider
                    .getInstance();
            StudentService studentService = (StudentService) context.getBean("studentService");
           
                studentService.deleteById(id);

            RequestDispatcher view = request
                    .getRequestDispatcher("/controller.html?action=studentslist");
            request.setAttribute("group", group);
            try {
                view.forward(request, response);
            } catch (ServletException e) {
                log.error("/controller.html forward error: " + e);
            }
        }
    }
}
