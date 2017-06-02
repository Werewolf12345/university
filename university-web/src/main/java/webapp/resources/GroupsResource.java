package webapp.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.context.ApplicationContext;

import webapp.models.GroupModel;

import com.alexboriskin.university.dao.ApplicationContextProvider;
import com.alexboriskin.university.dao.GroupDao;
import com.alexboriskin.university.dao.StudentDao;
import com.alexboriskin.university.dao.StudentService;
import com.alexboriskin.university.dao.UniversityDao;
import com.alexboriskin.university.domain.Group;
import com.alexboriskin.university.domain.Student;

@Path("/groups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupsResource {

    @GET
    public List<GroupModel> getGroupsList(@Context UriInfo uriInfo) {

        ApplicationContext context = ApplicationContextProvider.getInstance();
        UniversityDao universityDao = (UniversityDao) context
                .getBean("universityDao");

        Set<Group> groups = universityDao.getGroupsSet();
        List<GroupModel> groupsList = new ArrayList<>();

        for (Group current : groups) {
            current.getStudents();
            int groupSize = current.getStudents().size();
            if (groupSize > 0) {
                GroupModel groupModel = new GroupModel();
                groupModel.setId(current.getId());
                groupModel.setName(current.getName());
                groupModel.setNumberOfStudents(groupSize);
                groupModel.setLink(getUriForGroup(uriInfo, current));
                groupsList.add(groupModel);
            }
        }
        return groupsList;

    }

    @GET
    @Path("/{groupName}")
    public List<Student> getStudentsList(
            @PathParam("groupName") String groupName, @Context UriInfo uriInfo) {

        ApplicationContext context = ApplicationContextProvider.getInstance();
        GroupDao groupDao = (GroupDao) context.getBean("groupDao");

        Group group = groupDao.getByName(groupName);
        List<Student> studentsList = new ArrayList<>(group.getStudents());

        return studentsList;
    }

    @GET
    @Path("/{groupName}/{studentId}")
    public Student getStudent(@PathParam("groupName") String groupName,
            @PathParam("studentId") int studentId, @Context UriInfo uriInfo) {

        ApplicationContext context = ApplicationContextProvider.getInstance();
        StudentDao studentDao = (StudentDao) context.getBean("studentDao");

        Student student = studentDao.get(studentId);
        Group group = studentDao.getGroup(studentId);

        if (!group.getName().equals(groupName)) {
            student = null;
        }
        return student;
    }

    @POST
    @Path("/{groupName}")
    public Response addStudent(@PathParam("groupName") String groupName,
            Student student, @Context UriInfo uriInfo)
            throws URISyntaxException {
        ApplicationContext context = ApplicationContextProvider.getInstance();
        StudentService studentService = (StudentService) context
                .getBean("studentService");

        studentService.saveNew(student, groupName);
        return Response
                .created(new URI(uriInfo.getPath() + "/" + student.getId()))
                .entity(student).build();
    }

    @PUT
    @Path("/{groupName}/{studentId}")
    public Student updateStudent(@PathParam("groupName") String groupName,
            @PathParam("studentId") int studentId, Student student,
            @Context UriInfo uriInfo) {

        ApplicationContext context = ApplicationContextProvider.getInstance();
        StudentService studentService = (StudentService) context
                .getBean("studentService");

        studentService.update(student, groupName, studentId);
        return student;
    }

    @DELETE
    @Path("/{groupName}/{studentId}")
    public void deleteStudent(@PathParam("studentId") int studentId) {

        ApplicationContext context = ApplicationContextProvider.getInstance();
        StudentService studentService = (StudentService) context
                .getBean("studentService");

        studentService.deleteById(studentId);

    }

    private String getUriForGroup(UriInfo uriInfo, Group group) {
        String uri = uriInfo.getBaseUriBuilder().path(GroupsResource.class)
                .path(group.getName()).build().toString();
        return uri;
    }
}
