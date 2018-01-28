package chapter3;

public class EratosthenesUsingForEachLoop {

    public static void main(String[] args) {
        int n = 10000;
        int nums[] = new int[n];

        // fill the array
        for (int i = 0; i < n; i++) {
            nums[i] = i;
        }

        double sRoot = Math.sqrt(n);
        for (int i = 2; i < sRoot; i++) {
            // following loop must starts by 2*i.
            // Otherwise, all numbers lower than sqrt(n) will be marked as non-prime number.
            for (int j = 2 * i; j < n; j += i) { // increase by i
                nums[j] = n + 1; // mark as non-prime number
            }
        }
        //mark 0 and 1 as non-prime number manually
        nums[0] = n + 1;
        nums[1] = n + 1;

        for (int i : nums) {
            if (i > n)
                continue;
            System.out.println(i);
        }
    }

}
