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

import com.alexboriskin.university.dao.University;
import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Professor;

@WebServlet(urlPatterns = "/listprofessors.html")
public class ProfessorsListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LogManager.getLogger();
    private static final boolean PROFESSOR = false;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        try {
            University university = new University();
            Set<Professor> allProfessors = university.getAllProfessors()
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
