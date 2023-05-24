package services;

import department.DepartmentServiceGrpc;
import department.FetchDepartmentRequest;
import department.FetchDepartmentResponse;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.Map;

// Extending gRPC generated code to override method and add custom implementation
public class DepartmentService extends DepartmentServiceGrpc.DepartmentServiceImplBase {
    private final Map<String, String> departmentsDetail = new HashMap<>();

    public DepartmentService() {
        departmentsDetail.put("John", "IT");
        departmentsDetail.put("Mark", "Teaching");
    }

    @Override
    public void getDepartmentByUserName(FetchDepartmentRequest request, StreamObserver<FetchDepartmentResponse> responseObserver) {
        String userName = request.getName();
        String name = "User is not assigned to any department";
        if (departmentsDetail.containsKey(userName)) {
            name = departmentsDetail.get(userName);
        }

        FetchDepartmentResponse response = FetchDepartmentResponse.newBuilder().setName(name).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}