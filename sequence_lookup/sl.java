import java.io.*;
import java.util.*;

public class sl {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++) {
            String[] inputString = br.readLine().split(" ");
            Integer[] input = new Integer[inputString.length];
            for (int j = 0; j < inputString.length; j++) input[j] = Integer.parseInt(inputString[j]);
            //System.out.println(Arrays.toString(input));

            String[] queryString = br.readLine().split(" ");
            Integer[] query = new Integer[queryString.length];
            for (int j = 0; j < queryString.length; j++) query[j] = Integer.parseInt(queryString[j]);
            //System.out.println(Arrays.toString(query));

            for (int j = 1; j < query.length; j++) {
                if (j < query.length - 1) System.out.print(input[query[j]+1] + " ");
                else System.out.print(input[query[j]+1]);
            }
            System.out.println();
        }
    }
}
