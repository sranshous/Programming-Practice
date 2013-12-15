import java.io.*;
import java.util.*;

public class ks {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

        int dbNum = 1;
        String line;
        while((line = br.readLine()) != null) {
            int n = Integer.parseInt(line);

            if (n == 0) break;

            ArrayList<ArrayList<Double>> records = new ArrayList<>();   // build the database
            for (int i = 0; i < n; i++) {
                ArrayList<Double> entry = new ArrayList<>();
                readEntryLine(entry);
                records.add(entry);
            }

            int m = Integer.parseInt(br.readLine());

            System.out.println("Database " + dbNum++);
            for (int i = 0; i < m; i++) {
                ArrayList<pair> matches = new ArrayList<>();
                ArrayList<Double> query = new ArrayList<>();
                readEntryLine(query);

                for (int j = 0; j < records.size(); j++) {
                    if (records.get(j).size() != query.size()) continue;

                    //for (int k = 0; k < query.size(); k++) System.out.print(query.get(k) + " ");
                    //System.out.println();
                    //for (int k = 0; k < records.get(j).size(); k++) System.out.print(records.get(j).get(k) + " ");
                    //System.out.println();
                    boolean match = true;
                    double totalDiff = 0.0;
                    for (int k = 0; k < query.size(); k++) {
                        double diff = Math.abs(query.get(k) - records.get(j).get(k));
                        totalDiff += diff;
                        if (diff >= 1) {
                            match = false;
                            break;
                        }
                    }
                    //System.out.println();

                    if (match) matches.add(new pair(totalDiff, j));
                }

                System.out.println("Query " + (i+1) + ": there are " + matches.size() + " matches.");
                Collections.sort(matches);
                int pcount = Math.min(matches.size(), 5);
                for (int j = 0; j < pcount; j++) {
                    if (j < pcount-1) System.out.print(matches.get(j).index + ", ");
                    else System.out.println(matches.get(j).index);
                }
                System.out.println();
            }

        }
    }

    public static void readEntryLine(ArrayList<Double> entry) throws IOException {
        String[] line = br.readLine().split("\\s+");
        //System.out.println(Arrays.toString(line));
        for (int i = 0; i < line.length; i++)
            entry.add(Double.parseDouble(line[i]));

        //for (int i = 0; i < entry.size(); i++) System.out.print(entry.get(i) + " ");
        //System.out.println();
    }
}

class pair implements Comparable<pair> {
    public double value;
    public int index;

    public pair(double value, int index) {
        this.value = value;
        this.index = index;
    }

    public int compareTo(pair p) {
        if (this.value < p.value)
            return -1;
        else if (this.value > p.value)
            return 1;
        return 0;
    }
}
