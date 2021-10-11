package gRPC.gRpcService;

import com.google.protobuf.Empty;
import com.teacherGrpc.stubs.teacher.NewTeacher;
import com.teacherGrpc.stubs.teacher.Teacher_Id;
import com.teacherGrpc.stubs.teacher.Teacher;
import com.teacherGrpc.stubs.teacher.TeacherGRpcServiceGrpc;

import gRPC.gRpcDao.TeacherGRpcDao;
import gRPC.gRpcDomain.TeacherGrpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeacherGRpcServiceImpl extends TeacherGRpcServiceGrpc.TeacherGRpcServiceImplBase {

    private static final Logger logger = Logger.getLogger(TeacherGRpcServiceImpl.class.getName());

    //Data access object that fetches data from the db and returns it
    private TeacherGRpcDao teacherGRpcDao = new TeacherGRpcDao();

    @Override
    public void getTeacherById(Teacher_Id request, StreamObserver<Teacher> responseObserver) {
        logger.log(Level.INFO, "getTeacherById method called from "+getClass());
        int teacherId = (int) request.getTeacherId();

        try{
            TeacherGrpc teacherGrpc = teacherGRpcDao.findById(teacherId);


            Teacher teacherGRpcResponse = Teacher.newBuilder()
                    .setTeacherId(teacherId)
                    .setName(teacherGrpc.getName()) //Using Getters from target folder
                    .setAge(teacherGrpc.getAge())
                    .setMail(teacherGrpc.getMail()) //Using Getters from target folder
                    .setSubject(teacherGrpc.getSubject()) //Using Getters from target folder
                    .build();

            //System.out.println("Student repsone object: \n"+ studentGRpcResponse);

            responseObserver.onNext(teacherGRpcResponse); // This send the data to port 8081 so bloom can fetch the data
            responseObserver.onCompleted();
        }catch (NoSuchElementException e){
            logger.log(Level.SEVERE, "No Teacher was found with the ID of: "+teacherId);

            // If some error occurs with status not_found
            responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
        }
    }

    public void getAllTeachers(Empty request, StreamObserver<Teacher> responseObserver) {
        logger.log(Level.INFO, "getAllTeachers method called from "+getClass());
        List<TeacherGrpc> teachers = teacherGRpcDao.findAllTeachers();
        for (TeacherGrpc current: teachers) {
            Teacher teacherGRpcResponse = Teacher.newBuilder()
                    .setTeacherId(current.getTeacherId())
                    .setName(current.getName()) //Using Getters from target folder
                    .setAge(current.getAge())
                    .setMail(current.getMail()) //Using Getters from target folder
                    .setSubject(current.getSubject()) //Using Getters from target folder
                    .build();
            responseObserver.onNext(teacherGRpcResponse); // This send the data to port 8082 so bloom can fetch the data
        }
        responseObserver.onCompleted();
    }

    public void createTeacher(NewTeacher teacher, StreamObserver<Teacher> responseObserver) {
        logger.log(Level.INFO, "createTeacher method called from "+getClass());
        TeacherGrpc teacher1 = teacherGRpcDao.createNewTeacher(teacher);
        Teacher teacherGRpcResponse = Teacher.newBuilder()
                .setTeacherId(teacher1.getTeacherId())
                .setName(teacher1.getName()) //Using Getters from target folder
                .setAge(teacher1.getAge())
                .setMail(teacher1.getMail()) //Using Getters from target folder
                .setSubject(teacher1.getSubject()) //Using Getters from target folder
                .build();
        responseObserver.onNext(teacherGRpcResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteTeacher(Teacher_Id request, StreamObserver<Empty> responseObserver) {
        logger.log(Level.INFO, "deleteTeacher method called from "+getClass());
        int teacherId = (int) request.getTeacherId();
        String deleted = teacherGRpcDao.deleteTeacherDao(teacherId);
        System.out.println(deleted);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateTeacher(Teacher teacher, StreamObserver<Teacher> responseObserver) {
        logger.log(Level.INFO, "updateTeacher method called from "+getClass());
        TeacherGrpc teacher1 = teacherGRpcDao.updateTeacher(teacher);
        responseObserver.onNext(teacher);
        responseObserver.onCompleted();
    }
}
