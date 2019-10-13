package com.github.jimmy.grpc.greeting.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public abstract class WithChannel {
    static ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext()
            .build();
}
