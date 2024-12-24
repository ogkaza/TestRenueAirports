import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Reader2 {
    private String fileName;
    private int column;
    private List<String[]> filteredAndSortedData;
    private double initTime;

    public double getInitTime() {
        return initTime;
    }

    public Reader2(String fileName, int column) {
        this.fileName = fileName;
        this.column = column;
        this.filteredAndSortedData = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        // инициализирую
        readAndProcessFile(fileName, column);

        long endTime = System.currentTimeMillis();
        this.initTime = endTime - startTime;
    }

    private void readAndProcessFile(String fileName, int column) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // обработка строки
                processLine(line, column);
            }
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }

        // сортировка
        sortData(filteredAndSortedData);
    }

    private void processLine(String line, int column) {
        String[] parts = line.split(",");
        if (parts.length > column - 1) {
            filteredAndSortedData.add(new String[]{parts[0], parts[column - 1]});
        }
    }

    private void sortData(List<String[]> data) {
        data.sort(Comparator.comparing(o -> o[1]));
    }

    public SearchResult search(String s) {
        BinaryDataSearcher searcher = new BinaryDataSearcher();
        List<Integer> matches = searcher.binarySearch(filteredAndSortedData, s);
        double searchTimeMillis = searcher.getSearchTimeMillis();
        return new SearchResult(matches, searchTimeMillis);
    }
}