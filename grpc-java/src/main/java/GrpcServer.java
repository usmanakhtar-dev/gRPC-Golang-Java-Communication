import io.grpc.Server;
import io.grpc.ServerBuilder;
import services.DepartmentService;

import java.util.HashMap;
import java.util.Map;

public class GrpcServer {
    private Server server;

    public static void main(String[] args) throws Exception {
        GrpcServer grpcServer = new GrpcServer();
        grpcServer.start();
        grpcServer.blockUntilShutdown();
    }

    private void start() throws Exception {
        int port = 9090;

        // Binding Our Implementations
        server = ServerBuilder.forPort(port)
                .addService(new DepartmentService())
                .build()
                .start();

        System.out.println("Server started on port " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server...");
            GrpcServer.this.stop();
            System.out.println("gRPC server shut down.");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
