import java.io.*;
import java.util.*;

public class os {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line;
        while ((line = br.readLine()) != null) {
            ArrayList<Integer> numbers = new ArrayList<Integer>();
            String[] nums = line.split(" ");
            for (int i = 0; i < nums.length; i++) numbers.add(Integer.parseInt(nums[i]));
            Collections.sort(numbers);

            if ((line = br.readLine()) == null) throw new IOException();
            String[] query = line.split(" ");
            int[] queries = new int[query.length];
            for (int i = 0; i < query.length; i++) queries[i] = Integer.parseInt(query[i]);

            for (int i = 0; i < queries.length; i++) {
                if (i < queries.length-1) System.out.print(numbers.get(queries[i]) + " ");
                else System.out.print(numbers.get(queries[i]));
            }
            System.out.println();
        }
    }
}
