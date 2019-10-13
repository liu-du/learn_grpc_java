package com.github.jimmy.grpc.greeting.client;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;

import java.util.Iterator;

public class GreetingClient extends WithChannel {
    public static void main(String[] args) {

        GreetServiceGrpc.GreetServiceBlockingStub syncClient = GreetServiceGrpc.newBlockingStub(channel);

        GreetRequest request = GreetRequest.newBuilder()
                .setGreeting(
                    Greeting.newBuilder()
                            .setFirstName("Dwyane")
                            .setLastName("Wade")
                            .build()
                ).build();

        System.out.println(syncClient.greet(request).getResult());

        Iterator<GreetResponse> responses = syncClient.greetManyTimes(request);
        responses.forEachRemaining(response -> {
            System.out.println(response.getResult());
        });

    }
}
