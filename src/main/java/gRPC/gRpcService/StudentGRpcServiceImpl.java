package gRPC.gRpcService;


import com.studentAppicationGRpc.stubs.student.StudentGRpcRequest;
import com.studentAppicationGRpc.stubs.student.StudentGRpcResponse;
import com.studentAppicationGRpc.stubs.student.StudentGRpcServiceGrpc;
import gRPC.gRpcDao.StudentGRpcDao;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import gRPC.gRpcDomain.StudentGRpc;

import java.lang.reflect.Type;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentGRpcServiceImpl extends StudentGRpcServiceGrpc.StudentGRpcServiceImplBase {

    private static final Logger logger = Logger.getLogger(StudentGRpcServiceImpl.class.getName());

    //Data access object that fetches data from the db and returns it
    private StudentGRpcDao studentGRpcDao = new StudentGRpcDao();

    @Override
    public void getStudentInfo(StudentGRpcRequest request, StreamObserver<StudentGRpcResponse> responseObserver) {
        int studentId = (int) request.getStudentId();// the student ID should be passed with the request message

        try{
            StudentGRpc studentGRpc = studentGRpcDao.findById(studentId);

            System.out.println("Student object " + studentGRpc.getStudentMail());
            System.out.println("Student object " + studentGRpc.getStudentMail().getClass());
            System.out.println("Student object " + studentGRpc.getStudentMail().getClass().getName());




            StudentGRpcResponse studentGRpcResponse = StudentGRpcResponse.newBuilder()
                    .setStudentId(studentId)
                    .setStudentName(studentGRpc.getStudentName())
                    .setStudentMail(studentGRpc.getStudentMail())
                    .build();

            System.out.println("Student repsone object: \n"+ studentGRpcResponse);

            responseObserver.onNext(studentGRpcResponse);
            responseObserver.onCompleted();
        }catch (NoSuchElementException e){
            logger.log(Level.SEVERE, "NO STUDENT FOUND WITH THE STUDENT ID: "+studentId);

            // If some error occurs we sent an error with the following status which is not_found
            responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
        }
    }
}
