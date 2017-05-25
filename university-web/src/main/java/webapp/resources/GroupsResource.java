package webapp.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.ApplicationContext;

import com.alexboriskin.university.dao.ApplicationContextProvider;
import com.alexboriskin.university.dao.UniversityDao;
import com.alexboriskin.university.domain.Group;

@Path("groups")
public class GroupsResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Integer> getGroupsMap() {

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
        }
        return groupsMap;

    }
}
