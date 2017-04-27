package webapp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alexboriskin.university.dao.University;
import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Group;

@WebServlet(urlPatterns = "/grouplist.html")
public class GroupsListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        try {
            University university = new University();
            Set<Group> groups = university.getAllGroups();
            Map<String, Integer> groupsMap = new HashMap<>();

            for (Group current : groups) {
                int groupSize = current.getStudents().size();
                if (groupSize > 0) {
                    groupsMap.put(current.getName(), groupSize);
                }
            }

            request.setAttribute("groups", groupsMap);
        } catch (DAOException e) {
            log.error("SQL database error: " + e);
        }

        RequestDispatcher view = request
                .getRequestDispatcher("/WEB-INF/listgroups.jsp");
        try {
            view.forward(request, response);
        } catch (ServletException e) {
            log.error("listgroups.jsp forward error: " + e);
        }

    }
}
