package com.example.grpc;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utilizatori.Utilizator;
import utilizatori.UtilizatorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilizatorServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Override
    public void createUser(CreateUserRequest request, StreamObserver<UserResponse> responseObserver) {
        // Map gRPC request to entity
        Utilizator utilizator = new Utilizator();
        utilizator.setEmail(request.getEmail());
        utilizator.setParola(request.getPassword());
        utilizator.setRol(Utilizator.Rol.valueOf(request.getRole().toString()));

        // Save the entity and prepare response
        Utilizator savedUtilizator = utilizatorRepository.save(utilizator);

        UserResponse response = UserResponse.newBuilder()
                .setId(savedUtilizator.getId().intValue())
                .setEmail(savedUtilizator.getEmail())
                .setRole(Role.valueOf(savedUtilizator.getRol().toString()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    @Override
    public void getUserById(UserIdRequest request, StreamObserver<UserResponse> responseObserver) {
        // Fetch user from repository
        Utilizator utilizator = utilizatorRepository.findById((long) request.getId())
                .orElseThrow(() -> new RuntimeException("Utilizator not found"));

        // Map entity to gRPC response
        UserResponse response = UserResponse.newBuilder()
                .setId(utilizator.getId().intValue())
                .setEmail(utilizator.getEmail())
                .setRole(Role.valueOf(utilizator.getRol().toString()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateUser(UpdateUserRequest request, StreamObserver<UserResponse> responseObserver) {
        Utilizator utilizator = utilizatorRepository.findById((long) request.getId())
                .orElseThrow(() -> new RuntimeException("Utilizator not found"));

        // Update user details
        utilizator.setEmail(request.getEmail());
        utilizator.setParola(request.getPassword());
        utilizator.setRol(Utilizator.Rol.valueOf(request.getRole().toString()));

        // Save updated entity and prepare response
        Utilizator updatedUtilizator = utilizatorRepository.save(utilizator);

        UserResponse response = UserResponse.newBuilder()
                .setId(updatedUtilizator.getId().intValue())
                .setEmail(updatedUtilizator.getEmail())
                .setRole(Role.valueOf(updatedUtilizator.getRol().toString()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteUser(UserIdRequest request, StreamObserver<com.google.protobuf.Empty> responseObserver) {
        // Delete user by ID
        utilizatorRepository.deleteById((long) request.getId());
        responseObserver.onNext(com.google.protobuf.Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllUsers(com.google.protobuf.Empty request, StreamObserver<UserListResponse> responseObserver) {
        // Fetch all users and map to gRPC response
        List<UserResponse> users = utilizatorRepository.findAll().stream()
                .map(utilizator -> UserResponse.newBuilder()
                        .setId(utilizator.getId().intValue())
                        .setEmail(utilizator.getEmail())
                        .setRole(Role.valueOf(utilizator.getRol().toString()))
                        .build())
                .collect(Collectors.toList());

        UserListResponse response = UserListResponse.newBuilder()
                .addAllUsers(users)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
