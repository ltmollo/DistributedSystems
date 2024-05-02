import json

from handler import call_method


def get_service():
    obj = "calculator.AdvancedCalculator"
    method = "describe"

    call_method(method, None, obj)


def get_operation_result():
    operation = input("operation: ")

    try:
        numbers = [int(num) for num in input("numbers: ").split()]
    except ValueError:
        print("Invalid input. Please enter integers separated by spaces.")
        return

    method = "calculator.AdvancedCalculator/ComplexOperation"
    args = {"optype": operation, "args": numbers}
    args_json = json.dumps(args)

    call_method(method, args_json)


def find_prime_numbers():
    try:
        numbers = [int(num) for num in input("numbers: ").split()]
    except ValueError:
        print("Invalid input. Please enter integers separated by spaces.")
        return

    method = "calculator.AdvancedCalculator/FindPrimeNumbers"
    args = {"numbers": numbers}
    args_json = json.dumps(args)

    call_method(method, args_json)


def find_fibonacci_number():
    try:
        number = int(input("number: "))
    except ValueError:
        print("Invalid input. Please enter integers separated by spaces.")
        return

    args = {"number": number}
    args_json = json.dumps(args)
    method = "calculator.AdvancedCalculator/FindFibonacciNumber"

    call_method(method, args_json)

    return

def main():

    while True:
        print("Enter Command: describe, operation, prime, fibonacci, stop")
        command = input(">>> ")
        if command == "describe":
            get_service()
        elif command == "operation":
            get_operation_result()
        elif command == "prime":
            find_prime_numbers()
        elif command == "fibonacci":
            find_fibonacci_number()
        elif command == "stop":
            break
        else:
            print("Invalid command.")
        print("--------------")


if __name__ == '__main__':
    main()
