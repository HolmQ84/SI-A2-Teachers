package gRPC.gRpcServer;

import gRPC.gRpcService.TeacherGRpcServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class TeacherGrpcServer {
    private static final Logger logger = Logger.getLogger(TeacherGRpcServiceImpl.class.getName());

    public static void main(String[] args) {
        //Starter server på angivet port
        Server server = ServerBuilder.forPort(8082) // Starts server on port 8081
                .addService(new TeacherGRpcServiceImpl())
                .build();
        try {
            server.start();
            logger.log(Level.INFO, "Teacher Server started on port: 8082");
            server.awaitTermination();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Teacher Server did not start due to a IO Exception.");
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Teacher Server did not start due to a Interrupt.");
        }

    }
}
