import grpc
import sys
sys.path.append('./gen')
import calculator_pb2
import calculator_pb2_grpc

def main():
    # 5. Zainicjuj kanał gRPC i utwórz klienta dla usługi
    channel = grpc.insecure_channel('localhost:50051')
    calculator_stub = calculator_pb2_grpc.AdvancedCalculatorStub(channel)

    # 6. Użyj dynamicznie wywoływanych metod, aby przekazać żądania i odebrać odpowiedzi
    sum_args = calculator_pb2.ComplexArithmeticOpArguments(optype=calculator_pb2.SUM, args=[1, 2])
    sum_result = calculator_stub.ComplexOperation(sum_args)
    print("Sum result:", sum_result.res)

    avg_args = calculator_pb2.ComplexArithmeticOpArguments(optype=calculator_pb2.AVG, args=[1, 2])
    avg_result = calculator_stub.ComplexOperation(avg_args)
    print("Subtract result:", avg_result.res)

    # Dla ComplexOperation
    subtract_args = calculator_pb2.ComplexArithmeticOpArguments(optype=calculator_pb2.SUM, args=[1, 2])
    subtract_result = calculator_stub.ComplexOperation(subtract_args)
    print("Subtract result:", subtract_result.res)

if __name__ == '__main__':
    main()
