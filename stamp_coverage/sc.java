import java.util.*;
import java.io.*;

public class sc {
    public static void main(String... args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] instance;
        while((instance = br.readLine().split(" ")).length != 1) {
            int maxStamps = Integer.parseInt(instance[0]);
            int numStamps = Integer.parseInt(instance[1]);
            ArrayList<Integer> denominations = new ArrayList<>();
            int maxStampVal = -1;

            for (int i = 0; i < numStamps; i++) {
                denominations.add(Integer.parseInt(instance[2+i]));
                if (denominations.get(i) > maxStampVal) {
                    maxStampVal = denominations.get(i);
                }
            }

            Collections.sort(denominations);
            //System.out.println(Arrays.toString(denominations.toArray()));

            /* Dynamic programming */
            int[] stamps = new int[maxStamps * maxStampVal + 2];
            Arrays.fill(stamps, 99999999);
            stamps[0] = 0;
            for (int i = 0; i < denominations.size(); i++) {
                int v = denominations.get(i);
                for (int j = v; j < stamps.length; j++) {
                    stamps[j] = Math.min(stamps[j-v] + 1, stamps[j]);
                }
            }

            for (int i = 0; i < stamps.length; i++) {
                if (stamps[i] > maxStamps) {
                    System.out.println(i-1);
                    break;
                }
            }
        }
    }
}
