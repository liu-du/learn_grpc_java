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

        System.out.println("serving a unary request by returning one response.");
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void greetManyTimes(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        System.out.print("serving a unary request by streaming multiple response ");
        try {

            for (int i = 0; i < 10; i++) {
                System.out.print(".");
                GreetResponse response = GreetResponse.newBuilder()
                        .setResult("Hello " + request.getGreeting().getFirstName() + " " + request.getGreeting().getLastName() + " " + i)
                        .build();

                responseObserver.onNext(response);
                Thread.sleep(500L);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println();
            responseObserver.onCompleted();
        }
    }

    @Override
    public StreamObserver<GreetRequest> longGreet(StreamObserver<GreetResponse> responseObserver) {
        StreamObserver<GreetRequest> requestObserver = new StreamObserver<GreetRequest>() {
            String result = "Hello ";

            @Override
            public void onNext(GreetRequest value) {
                // client sends a message
                System.out.println("received a streaming request.");
                result += value.getGreeting().getFirstName() + " " + value.getGreeting().getLastName() + " ";
            }

            @Override
            public void onError(Throwable t) {
                // client sends an error

            }

            @Override
            public void onCompleted() {
                // client is down
                System.out.println("client told server it's done.");
                responseObserver.onNext(
                        GreetResponse.newBuilder().setResult(result).build()
                );
                responseObserver.onCompleted();
            }
        };

        return requestObserver;
    }

    @Override
    public StreamObserver<GreetRequest> greetEveryOne(StreamObserver<GreetResponse> responseObserver) {
        StreamObserver<GreetRequest> requestObserver = new StreamObserver<GreetRequest>() {

            @Override
            public void onNext(GreetRequest value) {
                // client sends a message
                System.out.println("receiving a streaming request.");
                System.out.println("sending   a streaming response.");
                responseObserver.onNext(
                        GreetResponse.newBuilder()
                                .setResult("Hello " + value.getGreeting().getFirstName() + " " + value.getGreeting().getLastName() + " "
                        ).build()
                );
            }

            @Override
            public void onError(Throwable t) {
                // client sends an error
            }

            @Override
            public void onCompleted() {
                // client is done
                System.out.println("client told server it's done.");
                responseObserver.onCompleted();
            }
        };
        return requestObserver;
    }
}
