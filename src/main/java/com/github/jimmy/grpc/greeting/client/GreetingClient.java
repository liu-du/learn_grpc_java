package com.github.jimmy.grpc.greeting.client;

import com.github.jimmy.grpc.greeting.common.Channel;
import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GreetingClient extends Channel {

    private static GreetServiceGrpc.GreetServiceBlockingStub syncClient = GreetServiceGrpc.newBlockingStub(channel);
    private static GreetServiceGrpc.GreetServiceStub asyncClient = GreetServiceGrpc.newStub(channel);

    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {

        // 1. unary call
        GreetRequest request = GreetRequest.newBuilder()
                .setGreeting(
                    Greeting.newBuilder()
                            .setFirstName("Dwyane")
                            .setLastName("Wade")
                            .build()
                ).build();

        System.out.println(syncClient.greet(request).getResult());

        // 2. server streaming
        Iterator<GreetResponse> responses = syncClient.greetManyTimes(request);
        responses.forEachRemaining(response -> System.out.println(response.getResult()));

        // 3. client streaming
        StreamObserver<GreetRequest> requestObserver = asyncClient.longGreet(new StreamObserver<GreetResponse>() {
            @Override
            public void onNext(GreetResponse value) {
                // server send an response
                System.out.println("received response from server:");
                System.out.println(value.getResult());
            }

            @Override
            public void onError(Throwable t) {
                // server send an error

            }

            @Override
            public void onCompleted() {
                // server done
                System.out.println("server told client it's done");
                latch.countDown();

            }
        });

        // stream #1
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("stream message 1");
        requestObserver.onNext(
                GreetRequest.newBuilder()
                        .setGreeting(
                                Greeting.newBuilder()
                                        .setFirstName("Dwyane")
                                        .setLastName("Wade")
                                        .build()
                        ).build()
        );

        // stream #2
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("stream message 2");
        requestObserver.onNext(
                GreetRequest.newBuilder()
                        .setGreeting(
                                Greeting.newBuilder()
                                        .setFirstName("Lebron")
                                        .setLastName("James")
                                        .build()
                        ).build()
        );

        // stream #3
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("stream message 3");
        requestObserver.onNext(
                GreetRequest.newBuilder()
                        .setGreeting(
                                Greeting.newBuilder()
                                        .setFirstName("Kobe")
                                        .setLastName("Bryant")
                                        .build()
                        ).build()
        );

        // tell the server we're done
        System.out.println("streaming completed");
        requestObserver.onCompleted();

        try {
            latch.await(3L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4. client streaming
        System.out.println("Client streaming");

        CountDownLatch latch2 = new CountDownLatch(1);

        StreamObserver<GreetRequest> requestObserver2 = asyncClient.greetEveryOne(new StreamObserver<GreetResponse>() {
            @Override
            public void onNext(GreetResponse value) {
                // server send an response
                System.out.println("received response from server:");
                System.out.println(value.getResult());
            }

            @Override
            public void onError(Throwable t) {
                // server send an error
            }

            @Override
            public void onCompleted() {
                // server done
                System.out.println("server notifies completion");
                latch2.countDown();
            }
        });

        // stream #1
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("stream message 1");
        requestObserver2.onNext(
                GreetRequest.newBuilder()
                        .setGreeting(
                                Greeting.newBuilder()
                                        .setFirstName("Dwyane")
                                        .setLastName("Wade")
                                        .build()
                        ).build()
        );

        // stream #2
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("stream message 2");
        requestObserver2.onNext(
                GreetRequest.newBuilder()
                        .setGreeting(
                                Greeting.newBuilder()
                                        .setFirstName("Lebron")
                                        .setLastName("James")
                                        .build()
                        ).build()
        );

        // stream #3
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("stream message 3");
        requestObserver2.onNext(
                GreetRequest.newBuilder()
                        .setGreeting(
                                Greeting.newBuilder()
                                        .setFirstName("Kobe")
                                        .setLastName("Bryant")
                                        .build()
                        ).build()
        );

        // tell the server we're done
        System.out.println("streaming completed");
        requestObserver2.onCompleted();

        try {
            latch2.await(3L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
