package com.github.jimmy.grpc.greeting.server;

import com.proto.arithmetic.AverageServiceGrpc;
import com.proto.arithmetic.Double;
import com.proto.arithmetic.Number;
import io.grpc.stub.StreamObserver;

public class AverageServiceImpl extends AverageServiceGrpc.AverageServiceImplBase {

    @Override
    public StreamObserver<Number> average(StreamObserver<Double> responseObserver) {

        StreamObserver<Number> requestStream = new StreamObserver<Number>() {
            int sum = 0;
            int n = 0;

            @Override
            public void onNext(Number value) {
                System.out.println("client streaming a request");
                sum += value.getNumber();
                n++;
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                System.out.println("client notify completion");
                responseObserver.onNext(Double.newBuilder().setNumber((double) sum / (double) n).build());
                responseObserver.onCompleted();
            }
        };

        return requestStream;
    }
}
