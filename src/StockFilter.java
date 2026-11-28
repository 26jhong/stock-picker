import java.util.Map;
import java.util.ArrayList;
import java.time.LocalDate;

public class StockFilter {
    public static ArrayList<String> filterShortTerm(Map<String, ArrayList<StockPrice>> priceRecords, double minimumRecentReturn) {
        ArrayList<String> matches = new ArrayList<>();
        for (String symbol : priceRecords.keySet()) {
            ArrayList<StockPrice> records = priceRecords.get(symbol);
            if(records.size() < 2) {
                System.out.println(symbol + ": not enough price records");
                continue;
            }
            LocalDate firstDate = records.get(0).getDate();
            LocalDate lastDate = records.get(records.size() - 1).getDate();
            if(!firstDate.isAfter(lastDate.minusMonths(3))) {
                if(StockStats.recentReturn(records) >= minimumRecentReturn) {
                    matches.add(symbol);
                }
            } else {
                System.out.println(symbol + ": not enough three-month history.");
            }
        }
        return matches;
    }

    public static ArrayList<String> filterLongTerm(
            Map<String, ArrayList<StockPrice>> priceRecords,        
            double minimumAnnualizedReturn,
            double maximumVolatility,
            int minimumYears) {
        ArrayList<String> matches = new ArrayList<>();
        for (String symbol : priceRecords.keySet()) {
            ArrayList<StockPrice> records = priceRecords.get(symbol);
            if(records.size() < 3) {
                System.out.println(symbol + ": not enough price records");
                continue;
            }
            LocalDate firstDate = records.get(0).getDate();
            LocalDate lastDate = records.get(records.size() - 1).getDate();
            if(!firstDate.isAfter(lastDate.minusMonths(3))) {
                double annualizedReturn = StockStats.calculateAnnualizedReturn(records);
                double volatility = StockStats.calculateVolatility(records);
                if(annualizedReturn >= minimumAnnualizedReturn && volatility < maximumVolatility) {
                    matches.add(symbol);
                }
            } else {
                System.out.println(symbol + ": not enough history.");
            }
        }
        return matches;
    }
}
