package gRPC.gRpcDao;

import com.teacherGrpc.stubs.teacher.Teacher;
import gRPC.gRpcDomain.TeacherGrpc;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

public class TeacherGRpcDao {
    private int counter = 6;
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
    public TeacherGrpc createNewTeacher(Teacher teacher) {
        String query = "INSERT INTO teacherGrpc (teacherId, name, age, mail, subject) VALUES (?,?,?,?,?)";
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.createNativeQuery(query)
                .setParameter(1, counter)
                .setParameter(2, teacher.getName())
                .setParameter(3, teacher.getAge())
                .setParameter(4, teacher.getMail())
                .setParameter(5, teacher.getSubject())
                .executeUpdate();
        et.commit();
        counter++;
        return new TeacherGrpc(
                counter,
                teacher.getName(),
                teacher.getAge(),
                teacher.getMail(),
                teacher.getSubject()
        );
    }
}
