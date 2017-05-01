package webapp;

import java.io.IOException;
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
import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Professor;

@WebServlet(urlPatterns = "/listprofessors.html")
public class ProfessorsListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LogManager.getLogger();
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        try {
            ApplicationContext context = ApplicationContextProvider.getInstance();
            UniversityDao universityDao  = (UniversityDao) context.getBean("universityDao");
            
            Set<Professor> allProfessors = universityDao.getProfessorsSet()
                    .getMembers();
            request.setAttribute("professors", allProfessors);
        } catch (DAOException e) {
            log.error("SQL database error: " + e);
        }

        RequestDispatcher view = request
                .getRequestDispatcher("/WEB-INF/listprofessors.jsp");
        try {
            view.forward(request, response);
        } catch (ServletException e) {
            log.error("listprofessors.jsp forward error: " + e);
        }

    }
}
