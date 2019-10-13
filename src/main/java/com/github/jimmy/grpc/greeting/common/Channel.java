package com.github.jimmy.grpc.greeting.common;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public abstract class Channel {
    public static ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
            .usePlaintext()
            .build();
}
