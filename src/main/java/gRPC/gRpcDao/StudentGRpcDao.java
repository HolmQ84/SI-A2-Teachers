package gRPC.gRpcDao;

import com.studentAppicationGRpc.stubs.student.Student;
import gRPC.gRpcDomain.StudentGRpc;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.NoSuchElementException;

public class StudentGRpcDao {

    public StudentGRpc findById(int studentId){

        // We use entity managers to manage our two entities.
        // We use the factory design pattern to get the entity manager.
        // Here we should provide the name of the persistence unit that we provided in the persistence.xml file.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("student-applikation");
        EntityManager em = emf.createEntityManager();

        // find() tjekker db på baggrund af studentId
        // for the find method we have to provide our entity class and the id.
        StudentGRpc student = em.find(StudentGRpc.class, studentId);

        // If there is no record found with the provided student id, then we throw a NoSuchElement exception.
        if(student == null){
            throw new NoSuchElementException("404 - No data found with Id: "+studentId);
        }

        // If everything worked fine, return the result.
        return student;
    }

}
