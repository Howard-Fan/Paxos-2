import java.io.*;
import java.util.*;

/**
 * Created by hyh608 on 4/25/17.
 */
public class findPair {
    private static List<Integer> prices = new ArrayList<>();
    private static Map<Integer, String> items = new HashMap<>();

    private static void readFile(File fin) throws IOException {
        FileInputStream fis = new FileInputStream(fin);

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        while ((line = br.readLine()) != null) {
            String[] temp = line.split(",");
            String itemName = temp[0].trim();
            Integer price = Integer.valueOf(temp[1].trim());
            prices.add(price);
            items.put(price, itemName);
        }

        br.close();
    }

    public static void main(String[] args) {

//        prices.add(5);
//        prices.add(7);
//        prices.add(10);
//        prices.add(14);
//        prices.add(20);
//        prices.add(60);
//
//        items.put(5, "Candy Bar");
//        items.put(7, "Paperback Book");
//        items.put(10, "Detergent");
//        items.put(14, "Headphones");
//        items.put(20, "Earmuffs");
//        items.put(60, "Bluetooth Stereo");
        String fileName = args[0];
        Integer budget = Integer.valueOf(args[1]);
        try {
            readFile(new File(fileName));
        } catch (IOException i) {
            i.printStackTrace();
        }
        int people = 2;
        // int[] budget = {25, 23, 100, 11};
//        for (int i : budget) {
//            System.out.println(gifting(i, people));
//        }
        System.out.println(gifting(budget, people));
    }

    static String gifting(int budget, int people) {

        // Crate a 3D matrix to keep track of the budget, item, and people
        int[][][] dp = new int[prices.size()][budget + 1][people + 1];

        // Initialize the matrix for the first item
        for (int a = 0; a <= budget; a++) {
            for (int c = 1; c <= people; c++) {
                dp[0][a][c] = prices.get(0) > a ? 0 : prices.get(0);
            }
        }

//        for(int b = 0; b < prices.size(); b++) {
//            for(int c = 0; c <= people; c++) {
//                dp[b][0][c] = 0;
//            }
//        }


        // Looping through first the items, then the budget, then the number of people to find the optimal solution
        for (int i = 1; i < prices.size(); i++) {
            for (int j = 0; j <= budget; j++) {
                for (int k = 1; k <= people; k++) {
                    // if the the current item is over the budget, simply get the previous optimal value
                    if (j < prices.get(i)) {
                        dp[i][j][k] = dp[i - 1][j][k];
                    } else {
                        // if the number of items does not exceed the number of people,
                        // then just choose the maximum between picking and not picking the current item
                        if (i < k) {
                            dp[i][j][k] = Math.max(dp[i - 1][j][k], prices.get(i) + dp[i - 1][j - prices.get(i)][k]);
                        } else {
                            // if the number of items exceed the number of people,
                            // Use a priority queue to store all possible N(number of people)-pair combination
                            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.reverseOrder());
                            for (int x = 0; x < i; x++) {
                                priorityQueue.add(dp[x][j - prices.get(x)][k - 1]);
                            }
                            // Make sure the N-pair + current item doesn't exceed our budget
                            int temp = Integer.MAX_VALUE;
                            while (temp > j && !priorityQueue.isEmpty()) {
                                temp = priorityQueue.poll() + prices.get(i);
                            }
                            // set temp to 0 if all possible N-pair + current item exceed our budget
                            // forcing us to select the previous optimal solution
                            temp = temp > j ? 0 : temp;
                            dp[i][j][k] = Math.max(dp[i - 1][j][k], temp);
                        }
                    }
                }
            }
        }

//        System.out.println(Arrays.deepToString(dp).replace("]],", "]],\n"));


        // Use a bottom-up approach to find which items were actually picked
        List<Integer> list = new ArrayList<>(people);
        int column = budget;
        int row = prices.size() - 1;
        // Loop through the 3D matrix on the maximum people (that's the only solution we care about)
        // If the value of the total item under a budget is the same as the the value without current item
        // then we know we didn't pick this item, vice versa
        while (list.size() < people && dp[row][column][people] != 0) {
            if (row == 0 || dp[row][column][people] != dp[row - 1][column][people]) {
                list.add(prices.get(row));
                column -= prices.get(row);
            }
            row--;
        }

        // Transform the list into the desired output
        // If the list doesn't have exactly N items, it is said to be Not Possible
        if (list.size() < people) {
            return "Not Possible";
        } else {
            StringBuilder sb = new StringBuilder();
            for (Integer i : list) {
                sb.append(items.get(i))
                        .append(" ")
                        .append(i)
                        .append(", ");
            }
            return sb.substring(0, sb.length() - 1);
        }
    }
}
