package com.github.jimmy.grpc.greeting.common;

import com.proto.pnd.Number;
import com.proto.pnd.PrimeNumberDecompositionServiceGrpc;
import com.proto.pnd.PrimeNumberDecompositionServiceGrpc.PrimeNumberDecompositionServiceBlockingStub;

public class PrimeNumberDecompositionClient extends Channel {
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
