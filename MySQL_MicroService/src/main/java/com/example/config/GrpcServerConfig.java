package com.example.config;

import com.example.grpc.UtilizatorServiceImpl;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcServerConfig {

    @Autowired
    private UtilizatorServiceImpl utilizatorService;

    @Value("${grpc.server.port:9090}")
    private int grpcPort;

    @Bean
    public Server grpcServer() throws Exception {
        Server server = ServerBuilder.forPort(grpcPort)
                .addService((BindableService) utilizatorService)
                .build()
                .start();

        System.out.println("gRPC Server started, listening on port " + server.getPort());
        return server;
    }
}
