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
import org.springframework.context.ApplicationContext;

import com.alexboriskin.university.dao.ApplicationContextProvider;
import com.alexboriskin.university.dao.UniversityDao;
import com.alexboriskin.university.domain.Group;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/grouplist.html")
public class GroupsListServlet extends HttpServlet {
    
    private static final Logger log = LogManager.getLogger();
            
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        ApplicationContext context = ApplicationContextProvider.getInstance();
        UniversityDao universityDao = (UniversityDao) context
                .getBean("universityDao");

        Set<Group> groups = universityDao.getGroupsSet();
        Map<String, Integer> groupsMap = new HashMap<>();

        for (Group current : groups) {
            current.getStudents();
            int groupSize = current.getStudents().size();
            if (groupSize > 0) {
                groupsMap.put(current.getName(), groupSize);
            }

            request.setAttribute("groups", groupsMap);

            RequestDispatcher view = request
                    .getRequestDispatcher("/WEB-INF/listgroups.jsp");
            try {
                view.forward(request, response);
            } catch (ServletException e) {
                log.error("listgroups.jsp forward error: " + e);
            }
        }
    }
}
