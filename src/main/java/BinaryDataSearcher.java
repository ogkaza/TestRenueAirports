import java.util.ArrayList;
import java.util.List;

public class BinaryDataSearcher {
    private double searchTimeMillis;

    public double getSearchTimeMillis() {
        return searchTimeMillis;
    }

    public List<Integer> binarySearch(List<String[]> data, String s) {
        long startTime = System.nanoTime();

        int lenS = s.length();
        List<Integer> matches = new ArrayList<>();

        int left = 0;
        int right = data.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String[] row = data.get(mid);
            String cleanedRow = row[1].replace("\"", "").toLowerCase();

            int compareResult = cleanedRow.substring(0, Math.min(cleanedRow.length(), lenS)).compareTo(s.toLowerCase());

            if (compareResult == 0) {
                matches.add(Integer.valueOf(row[0]));

                int i = mid - 1;
                while (i >= 0 && data.get(i)[1].replace("\"", "").toLowerCase().substring(0, Math.min(data.get(i)[1].length(), lenS)).compareTo(s.toLowerCase()) == 0) {
                    matches.add(Integer.valueOf(data.get(i)[0]));
                    i--;
                }

                i = mid + 1;
                while (i < data.size() && data.get(i)[1].replace("\"", "").toLowerCase().substring(0, Math.min(data.get(i)[1].length(), lenS)).compareTo(s.toLowerCase()) == 0) {
                    matches.add(Integer.valueOf(data.get(i)[0]));
                    i++;
                }

                break;
            } else if (compareResult < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;
        double executionTimeMillis = executionTime / 1000000.0;
        this.searchTimeMillis = executionTimeMillis;

        return matches;
    }
}