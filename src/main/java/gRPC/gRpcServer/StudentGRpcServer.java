package gRPC.gRpcServer;

import gRPC.gRpcService.StudentGRpcServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class StudentGRpcServer {
    private static final Logger logger = Logger.getLogger(StudentGRpcServiceImpl.class.getName());

    public static void main(String[] args) {
        //Starter server på angivet port
        Server server = ServerBuilder.forPort(8081) // Starts server on port 8081
                .addService(new StudentGRpcServiceImpl())
                .build();
        try {
            server.start();
            logger.log(Level.INFO, "RESULT SERVER STARTED ON PORT 8081");
            server.awaitTermination();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "RESULT SERVER DID NOT START");
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "SERVER SHUT DOWN ON INTERRUPTED");
        }

    }
}
