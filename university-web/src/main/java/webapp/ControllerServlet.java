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

@WebServlet(urlPatterns = "/controller.html")
public class ControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger log = LogManager.getLogger();
    private static final String LIST_PROFESSORS = "/listprofessors.html";
    private static final String LIST_GROUPS = "/grouplist.html";
    private static final String LIST_STUDENTS = "/studentslist.html";
    private static final boolean STUDENT = true;
    private static final boolean PROFESSOR = false;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String forwardToURL = "";
        String action = (String) request.getAttribute("action");
        if (action == null) {
            action = "grouplist";
        }

        if (action.equalsIgnoreCase("groupslist")) {
            forwardToURL = LIST_GROUPS;
        } else if (action.equalsIgnoreCase("studentslist")) {
            forwardToURL = LIST_STUDENTS + "?action=list";
            request.setAttribute("group", request.getParameter("group"));
            request.setAttribute("action", "list");
        } else if (action.equalsIgnoreCase("professorslist")) {
            forwardToURL = LIST_PROFESSORS + "?action=list";
        } else {
            forwardToURL = LIST_GROUPS;
        }

        try {
            RequestDispatcher view = request.getRequestDispatcher(forwardToURL);
            view.forward(request, response);
        } catch (ServletException e) {
            log.error(forwardToURL + " forward error: " + e);
        }
    }
}