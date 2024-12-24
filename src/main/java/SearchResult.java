import java.util.List;

public class SearchResult {
    private List<Integer> matches; // совпадения
    private double searchTimeMillis; // время поиска конкретного запроса

    public SearchResult(List<Integer> matches, double searchTimeMillis) {
        this.matches = matches;
        this.searchTimeMillis = searchTimeMillis;
    }

    public List<Integer> getMatches() {
        return matches;
    }

    public double getSearchTimeMillis() {
        return searchTimeMillis;
    }
}