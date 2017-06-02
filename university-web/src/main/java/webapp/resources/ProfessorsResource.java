package webapp.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.ApplicationContext;

import com.alexboriskin.university.dao.ApplicationContextProvider;
import com.alexboriskin.university.dao.UniversityDao;
import com.alexboriskin.university.domain.Professor;

@Path("/professors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfessorsResource {

    @GET
    public List<Professor> getProfessorsList() {
        
        ApplicationContext context = ApplicationContextProvider.getInstance();
        UniversityDao universityDao = (UniversityDao) context
                .getBean("universityDao");

        Set<Professor> allProfessors = universityDao.getProfessorsSet()
                .getMembers();
        List<Professor> professorsList = new ArrayList<>(allProfessors);
        
        return professorsList;
    }

}
