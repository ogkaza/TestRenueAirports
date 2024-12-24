import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Application {
    public static void main(String[] args) {

        if (args.length != 8 || !args[0].equals("--data") || !args[2].equals("--indexed-column-id") || !args[4].equals("--input-file") || !args[6].equals("--output-file")) {
            System.out.println("Не предоставлены аргументы в терминале");
            return;
        }

        String dataFilePath = args[1];
        int indexedColumnId = Integer.parseInt(args[3]);
        String inputFilePath = args[5];
        String outputFilePath = args[7];

        Reader2 rd = new Reader2(dataFilePath, indexedColumnId);
        System.out.println("Время инициализации: " + rd.getInitTime() + " мс");

        JSONObject jsonOutput = new JSONObject();
        jsonOutput.put("initTime", rd.getInitTime());

        // массив где будут результатф поиска
        JSONArray resultsArray = new JSONArray();

        try {
            List<String> searchStrings = Files.readAllLines(Paths.get(inputFilePath));

            for (String s : searchStrings) {
                s = s.toLowerCase().trim(); // пробелы убрал

                SearchResult result = rd.search(s);

                JSONObject searchResult = new JSONObject();
                searchResult.put("search", s);
                searchResult.put("result", new JSONArray(result.getMatches()));
                searchResult.put("time", result.getSearchTimeMillis());

                resultsArray.put(searchResult);
            }

            jsonOutput.put("result", resultsArray);

            try (FileWriter file = new FileWriter(outputFilePath)) {
                file.write(jsonOutput.toString(4)); // чтоб в файле отступы были
                System.out.println("JSON-файл успешно создан или перезаписан: " + outputFilePath);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении или записи файла: " + e.getMessage());
        }
    }
}