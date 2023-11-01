import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PartitionSolver {

    public boolean canPartitionSubset(List<Integer> nums, List<Integer> subset) {
        int total = 0;// to store the sum of the elements in the array 
        for (int num : nums) {
            total += num;
        }

        // if the sum is odd we cannot do the partition as all the elements in the array are integers
        if (total % 2 != 0) {
            return false;
        }

        int halfSum = total / 2;// we want to have each partition sum  = totalSum/2
        boolean[][] dp = new boolean[nums.size() + 1][halfSum + 1];// to store the solutions of the sub problems 
        dp[0][0] = true;// beacuse we can get a sum of 0 using 0 elements 

        for (int i = 1; i <= nums.size(); i++) {
            for (int j = 0; j <= halfSum; j++) {
                if (nums.get(i - 1) <= j) {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums.get(i - 1)]; // considering the ith element 
                } else {
                    dp[i][j] = dp[i - 1][j];   // not considering the ith element 
                }
            }
        }

        if (!dp[nums.size()][halfSum]) {
            return false;
        }

        // Trace back to find the one partion and the elements left out are in the other/ second partition 
        int currSum = halfSum;
        for (int i = nums.size(); i > 0; i--) {
            if (currSum >= nums.get(i - 1) && dp[i][currSum] && !dp[i - 1][currSum]) {
                subset.add(nums.get(i - 1));
                currSum -= nums.get(i - 1);
            }
        }

        return true;
    }

    public static List<Integer> createRandomList(int size, int targetSum) {
        // generating random list which has a solution that is we can partition the array into two subsets
        Random rand = new Random();
        List<Integer> result = new ArrayList<>();
        int currSum = 0;
        int currSum1 = 0;
        int halfTarget = targetSum / 2;


        for (int i = 0; i < size / 2 - 1; i++) {
            int maxValue = halfTarget - currSum1 - (size - i - 1);
            int randValue = 1 + rand.nextInt(maxValue);
            result.add(randValue);
            currSum1 += randValue;
        }

        result.add(halfTarget - currSum1);

        for (int i = 0; i < size / 2 - 1; i++) {
            int maxValue = halfTarget - currSum - (size - i - 1);
            int randValue = 1 + rand.nextInt(maxValue);
            result.add(randValue);
            currSum += randValue;
        }

        result.add(halfTarget - currSum);


        return result; // this will create the random list of integers as input 
    }

    public static void main(String[] args) {
        int[] sizes = {4, 12, 36, 108, 324};// our input sizes 
        int[] targetSums = {20, 60, 180, 540, 1620}; // our sum of elements of the list with given size 

        PartitionSolver solver = new PartitionSolver();

        for (int i = 0; i < 5; i++) {
            List<Integer> subset = new ArrayList<>(); // hold one of the partition 
            List<Integer> randomList = createRandomList(sizes[i], targetSums[i]); // creating a list of numbers as input which will have a partition for sure 

            long startTime = System.nanoTime();
            boolean canPartition = solver.canPartitionSubset(randomList, subset);// solving 
            long endTime = System.nanoTime();

            System.out.println(endTime - startTime); // calculating the runtime 
            
            /*
            if you want the partition then uncomment the below two lines and run the code 
            for (int num : subset) 
            System.out.print(num + " ");*/
        }
    }
}