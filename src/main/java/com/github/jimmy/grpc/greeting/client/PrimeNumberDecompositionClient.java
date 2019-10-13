package com.github.jimmy.grpc.greeting.client;

import com.proto.greet.Number;
import com.proto.greet.PrimeNumberDecompositionServiceGrpc;
import com.proto.greet.PrimeNumberDecompositionServiceGrpc.PrimeNumberDecompositionServiceBlockingStub;

public class PrimeNumberDecompositionClient extends WithChannel {
    public static void main(String[] args) {

        PrimeNumberDecompositionServiceBlockingStub client = PrimeNumberDecompositionServiceGrpc.newBlockingStub(channel);

        System.out.print("Prime Number decomposition of 120 is: ");
        client.primeNumberDecomposition(Number.newBuilder().setNumber(120).build())
                .forEachRemaining(number -> {
                    System.out.print(number.getNumber());
                    System.out.print(" ");
                });

        System.out.println();

    }
}
