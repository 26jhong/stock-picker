
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<StockPrice> stocks = new ArrayList<>();
        Map<String, ArrayList<StockPrice>> priceRecords = new HashMap<>();
        System.out.println(new File("data/stocks.csv").getAbsolutePath());
        try(Scanner reader = new Scanner(new File("data/stocks.csv"))) {
            reader.nextLine();
            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] variables = line.split(",");
                String symbol = variables[0];
                LocalDate date = LocalDate.parse(variables[1]);
                Double close = Double.parseDouble(variables[2]);
                stocks.add(new StockPrice(symbol, date, close));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find data/stocks.csv");
        }
        for (int i = 0; i < stocks.size(); i++) {
            StockPrice currentStock = stocks.get(i);
            String symbol = currentStock.getSymbol(); 
            if (!priceRecords.containsKey(symbol)) {
                priceRecords.put(symbol, new ArrayList<>());
            }
            priceRecords.get(symbol).add(currentStock);
        }
        for (String symbol : priceRecords.keySet()) {
            ArrayList<StockPrice> current = priceRecords.get(symbol);
            current.sort(Comparator.comparing((StockPrice s) -> s.getDate()));
        }
        for (String symbol : priceRecords.keySet()) {
            ArrayList<StockPrice> current = priceRecords.get(symbol);
            double totalReturn = StockStats.calculateTotalReturn(current);
            System.out.printf("%s total return: %.2f%%%n", symbol, totalReturn);
            System.out.println(symbol + ": " + current.size() + " records ");
            for(int i = 0; i < current.size(); i++) {
                String dateString = current.get(i).getDate().toString();
                System.out.println("Date " + i + ": " + dateString);
            }

        }
        System.out.println(stocks.size());
    }
}