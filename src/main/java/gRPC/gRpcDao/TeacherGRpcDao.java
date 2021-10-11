package gRPC.gRpcDao;

import com.studentAppicationGRpc.stubs.teacher.Teacher;
import gRPC.gRpcDomain.TeacherGrpc;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

public class TeacherGRpcDao {

    @PersistenceContext
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("teacher-grpc");
    EntityManager em = emf.createEntityManager();

    public TeacherGrpc findById(int teacherId){
        TeacherGrpc teacher = this.em.find(TeacherGrpc.class, teacherId);

        // If there is no record found with the provided student id, then we throw a NoSuchElement exception.
        if(teacher == null){
            throw new NoSuchElementException("404 - No data found with Id: "+teacherId);
        }

        // If everything worked fine, return the result.
        return teacher;
    }

    public List<TeacherGrpc> findAllTeachers() {
        return (List<TeacherGrpc>) this.em.createQuery("select t from " + TeacherGrpc.class.getSimpleName() + " t").getResultList();
    }

    @Transactional
    public Teacher createNewTeacher(Teacher teacher) {
        this.em.persist(teacher);
        return teacher;
    }
}
