package com.github.jimmy.grpc.greeting.server;

import com.proto.greet.Number;
import com.proto.greet.PrimeNumberDecompositionServiceGrpc;
import io.grpc.stub.StreamObserver;

public class PrimeNumberDecompositionServiceImpl extends PrimeNumberDecompositionServiceGrpc.PrimeNumberDecompositionServiceImplBase {
    @Override
    public void primeNumberDecomposition(Number request, StreamObserver<Number> responseObserver) {
        System.out.print("serving PrimeNumberDecomposition reqeust ");
        int x = request.getNumber();
        int k = 2;
        while (x > 1) {
            if (x % k == 0) {
                x = x / k;
                responseObserver.onNext(Number.newBuilder().setNumber(k).build());
                System.out.print(".");
            } else {
                k = k + 1;
            }
        }
        System.out.println();
        responseObserver.onCompleted();
    }
}
