package webapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alexboriskin.university.dao.GroupDao;
import com.alexboriskin.university.dao.GroupDaoSqlImpl;
import com.alexboriskin.university.domain.DAOException;
import com.alexboriskin.university.domain.Group;
import com.alexboriskin.university.domain.Student;

/*
 * Browser sends Http Request to Web Server
 * 
 * Code in Web Server => Input:HttpRequest, Output: HttpResponse
 * JEE with Servlets
 * 
 * Web Server responds with Http Response
 */

//Java Platform, Enterprise Edition (Java EE) JEE6

//Servlet is a Java programming language class 
//used to extend the capabilities of servers 
//that host applications accessed by means of 
//a request-response programming model.

//1. extends javax.servlet.http.HttpServlet
//2. @WebServlet(urlPatterns = "/login.do")
//3. doGet(HttpServletRequest request, HttpServletResponse response)
//4. How is the response created?

@WebServlet(urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Yahoo!!!!!!!!</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("Students list:\\n");

        GroupDao groupDao = new GroupDaoSqlImpl();
        Group groupM93 = null;
        
        try {
            groupM93 = groupDao.get(groupDao.getID(new Group("m93")));
        } catch (DAOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (groupM93 != null) {
            for (Student current : groupM93.getStudents()) {

                out.println(current.getFirstName() + " "
                        + current.getLastName() + "\\n");
            }
        }

        out.println("</body>");
        out.println("</html>");

    }

}
