package com.github.jimmy.grpc.greeting.server;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {
    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        GreetResponse response = GreetResponse.newBuilder()
                .setResult("Hello " + request.getGreeting().getFirstName() + " " + request.getGreeting().getLastName())
                .build();

        System.out.println("serving a request.");
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
