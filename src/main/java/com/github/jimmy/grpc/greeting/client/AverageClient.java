package com.github.jimmy.grpc.greeting.client;

import com.github.jimmy.grpc.greeting.common.Channel;
import com.proto.arithmetic.AverageServiceGrpc;
import com.proto.arithmetic.Double;
import com.proto.arithmetic.Number;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class AverageClient extends Channel {
    static AverageServiceGrpc.AverageServiceStub client = AverageServiceGrpc.newStub(channel);

    public static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {

        StreamObserver<Double> responseObserver = new StreamObserver<Double>() {
            @Override
            public void onNext(Double value) {
                System.out.println("Received server response:");
                System.out.println(value.getNumber());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                System.out.println("server notify completion");
                latch.countDown();
            }
        };

        StreamObserver<Number> requestObserver = client.average(responseObserver);

        requestObserver.onNext(Number.newBuilder().setNumber(1).build());
        requestObserver.onNext(Number.newBuilder().setNumber(2).build());
        requestObserver.onNext(Number.newBuilder().setNumber(3).build());
        requestObserver.onNext(Number.newBuilder().setNumber(4).build());
        requestObserver.onCompleted();

        try {
            latch.await(3L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
