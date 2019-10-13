package com.github.jimmy.grpc.greeting.client;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {
    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        GreetServiceGrpc.GreetServiceBlockingStub syncClient = GreetServiceGrpc.newBlockingStub(channel);


        GreetRequest request = GreetRequest.newBuilder()
                .setGreeting(
                    Greeting.newBuilder()
                            .setFirstName("Dwyane")
                            .setLastName("Wade")
                            .build()
                ).build();

        System.out.println(syncClient.greet(request).getResult());

    }
}
