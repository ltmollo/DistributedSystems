import subprocess
import json


def get_service():
    curl = "./grpcurl.exe"
    server = "localhost:50051"
    plain_text = "-plaintext"
    obj = "calculator.AdvancedCalculator"

    command = [curl, plain_text, server, "describe", obj]
    result = subprocess.run(command, capture_output=True, text=True)

    if result.returncode == 0:
        print("Output:", result.stdout)
    else:
        print("Error:", result.stderr)

    return result.stdout

def get_operation_result(operation, numbers):
    curl = "./grpcurl.exe"
    server = "localhost:50051"
    plain_text = "-plaintext"

    args = {"optype": operation, "args": numbers}
    args_json = json.dumps(args)

    command = [curl, plain_text, "-d", args_json, server, "calculator.AdvancedCalculator/ComplexOperation"]
    result = subprocess.run(command, capture_output=True, text=True)
    if result.returncode == 0:
        print("Output:", result.stdout)
    else:
        print("Error:", result.stderr)
    return result.stdout


def find_prime_numbers(numbers):
    curl = "./grpcurl.exe"  # Ścieżka do programu curl
    server = "localhost:50051"
    plain_text = "-plaintext"

    args = {"numbers": numbers}
    args_json = json.dumps(args)

    command = [curl, plain_text, "-d", args_json, server, "calculator.AdvancedCalculator/FindPrimeNumbers"]
    result = subprocess.run(command, capture_output=True, text=True)

    if result.returncode == 0:
        print("Output:", result.stdout)
    else:
        print("Error:", result.stderr)

    return result.stdout


def find_fibonacci_number(number):
    curl = "./grpcurl.exe"  # Ścieżka do programu curl
    server = "localhost:50051"
    plain_text = "-plaintext"

    args = {"number": number}
    args_json = json.dumps(args)

    command = [curl, plain_text, "-d", args_json, server, "calculator.AdvancedCalculator/FindFibonacciNumber"]
    process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

    while True:
        output = process.stdout.readline()
        if output == '' and process.poll() is not None:
            break
        if output:
            print(output.strip())

    if process.returncode != 0:
        print("Error:", process.stderr)

    process.stdout.close()
    process.stderr.close()

    return

def main():
    get_service()
    operation = "AVG"  # Przykładowa operacja
    numbers = [1, 2, 3]  # Przykładowa lista liczb
    get_operation_result(operation, numbers)
    numbers = [2, 3, 4, 5, 7, 11, 13]  # Przykładowa lista liczb
    find_prime_numbers(numbers)
    find_fibonacci_number(5)

# def main():
#     server_address = 'localhost:50051'
#     channel = grpc.insecure_channel(server_address)
#
#     reflection_db = ProtoReflectionDescriptorDatabase(channel)
#     desc_pool = DescriptorPool(reflection_db)
#
#     service_desc = desc_pool.FindServiceByName("calculator.AdvancedCalculator")
#     method_desc1 = service_desc.FindMethodByName("ComplexOperation")
#     method_desc2 = service_desc.FindMethodByName("FindPrimeNumbers")
#     method_desc3 = service_desc.FindMethodByName("FindFibonacciNumber")
#     services = reflection_db.get_services()
#
#     request_desc = desc_pool.FindMessageTypeByName("calculator.ComplexArithmeticOpArguments")
#
#
#
#     # print(service_desc)
#     # print(method_desc1)
#     # print(method_desc2)
#     # print(method_desc3)
#     # print(services)
#     # print(request_desc)



if __name__ == '__main__':
    main()
