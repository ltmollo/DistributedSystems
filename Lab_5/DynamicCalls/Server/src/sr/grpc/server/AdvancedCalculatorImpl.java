package sr.grpc.server;

import io.grpc.stub.StreamObserver;
import sr.grpc.gen.AdvancedCalculatorGrpc.AdvancedCalculatorImplBase;
import sr.grpc.gen.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static io.grpc.Status.INVALID_ARGUMENT;

public class AdvancedCalculatorImpl extends AdvancedCalculatorImplBase {
    @Override
    public void complexOperation(ComplexArithmeticOpArguments request,
                                 StreamObserver<ComplexArithmeticOpResult> responseObserver) {

        if (request.getArgsCount() == 0) {
            responseObserver.onError(INVALID_ARGUMENT.withDescription("Must provide at least 1 arg").
                    asRuntimeException());
            System.out.println("[ComplexOperation]: No agruments");
            return;
        }

        double res = 0;
        switch (request.getOptype()) {
            case SUM:
                for (Double d : request.getArgsList()) res += d;
                break;
            case AVG:
                for (Double d : request.getArgsList()) res += d;
                res /= request.getArgsCount();
                break;
            case MIN:
                res = request.getArgsList().get(0);
                for (Double d : request.getArgsList()) res = Math.min(res, d);
                break;
            case MAX:
                res = request.getArgsList().get(0);
                for (Double d : request.getArgsList()) res = Math.max(res, d);
                break;
            case MULT:
                res = 1;
                for (Double d : request.getArgsList()) res *= d;
                break;
            case UNRECOGNIZED:
                break;
        }

        ComplexArithmeticOpResult result = ComplexArithmeticOpResult.newBuilder().setRes(res).build();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
        System.out.println("[ComplexOperation] finished");
    }

    @Override
    public void findPrimeNumbers(PrimesInputToCheck request,
                                 StreamObserver<PrimesResult> responseObserver) {
        if (request.getNumbersCount() == 0) {
            responseObserver.onError(INVALID_ARGUMENT.withDescription("Must provide at least 1 arg").
                    asRuntimeException());
            System.out.println("[FindPrimeNumbers] No agruments");
            return;
        }

        List<Integer> result = new ArrayList<>();

        request.getNumbersList().stream()
                .filter(Common::isPrime)
                .forEach(result::add);

        PrimesResult primesResult = PrimesResult.newBuilder().addAllPrimes(result).build();
        responseObserver.onNext(primesResult);
        responseObserver.onCompleted();
        System.out.println("[FindPrimeNumbers]: finished");
    }

    @Override
    public void findFibonacciNumber(FibonacciNumber request,
                                    StreamObserver<FibonacciResult> responseObserver) {

        if (request.getNumber() < 1) {
            responseObserver.onError(INVALID_ARGUMENT.withDescription("Number must be > 0").
                    asRuntimeException());
            System.out.println("[FindFibonacciNumber]: Wrong argument");
            return;
        }

        int number = request.getNumber();

        IntStream.range(1, number+1)
                .map(Common::fibonacciNumber)
                .mapToObj(value -> FibonacciResult.newBuilder().setNumber(value).build())
                .forEach(result -> {
                    responseObserver.onNext(result);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        responseObserver.onCompleted();
        System.out.println("[FindFibonacciNumber]: completed");
    }
}
