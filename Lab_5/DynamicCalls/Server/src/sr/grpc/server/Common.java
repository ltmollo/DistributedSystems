package sr.grpc.server;

public class Common {
    public static boolean isPrime(int number) {
        if (number < 2) return false;
        if (number == 2 || number == 3) return true;
        if (number % 2 == 0 || number % 3 == 0) return false;
        int i = 5;

        while (i * i <= number) {
            if (number % i == 0) return false;
            i += 2;
        }
        return true;
    }

    public static int fibonacciNumber(int number) {

        if (number <= 0) {
            return 0;
        } else if (number == 1 || number == 2) {
            return 1;
        }

        number -= 2;

        int a = 1;
        int b = 1;
        int temp;

        while (number > 0) {
            temp = a;
            a += b;
            b = temp;
            number -= 1;
        }

        return a;
    }
}
