package com.github.jimmy.grpc.greeting.client;

import com.github.jimmy.grpc.greeting.common.Channel;
import com.proto.arithmetic.Number;
import com.proto.arithmetic.PrimeNumberDecompositionServiceGrpc;
import com.proto.arithmetic.PrimeNumberDecompositionServiceGrpc.PrimeNumberDecompositionServiceBlockingStub;

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
