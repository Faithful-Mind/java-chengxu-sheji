package chapter3;

/**
 * Recursive implementation of Sieve of Eratosthenes. Only use the multiples of
 * prime numbers lower than its square root to sieve composite numbers higher
 * than or equal to its square root, recursively. I think it should be faster
 * than common implementations.
 * 
 * @author Faithful-Mind
 *
 */
public class RecursiveSieveOfEratosthenes {

    public static void main(String[] args) {
        boolean notPrime[] = e1(10000);
        for (int i = 0; i < notPrime.length; i++) {
            if (!notPrime[i])
                System.out.println(i);
        }
    }

    static boolean[] e1(int n) {
        return e2(new boolean[n + 1], n + 1);
    }

    static boolean[] e2(boolean[] notPrime, double n) {
        // ending condition
        if (n <= 4) {
            notPrime[0] = true;
            notPrime[1] = true;
            return notPrime;
        }

        double sRoot = Math.sqrt(n);
        e2(notPrime, sRoot);
        for (int i = 2; i < n; i++) {
            if (!notPrime[i]) { // only use prime number as sieve source
                for (int j = sRoot > i ?
                        (int) (sRoot - sRoot % i + i) // from the lowest i's multiple higher than or equal to sRoot
                        : 2 * i;
                        j < n; j += i) { // increase by i
                    notPrime[j] = true;
                }
            }
        }
        return notPrime;
    }

}
