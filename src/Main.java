import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filePath = "data/real_stocks - Monthly.csv";

        Map<String, ArrayList<StockPrice>> priceRecords = new HashMap<>();

        try (Scanner reader = new Scanner(new File(filePath))) {
            if (!reader.hasNextLine()) {
                System.out.println("The CSV file is empty.");
                return;
            } 

            reader.nextLine(); // This skips Symbol, Date, Close labels;

            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] variables = line.split(",");
                String symbol = variables[0];
                LocalDate date = LocalDate.parse(variables[1]);
                Double close = Double.parseDouble(variables[2]);
                StockPrice record = new StockPrice(symbol, date, close);
                if (!priceRecords.containsKey(symbol)) {
                    priceRecords.put(symbol, new ArrayList<>());
                }
                priceRecords.get(symbol).add(record);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find: " + filePath);
            return;
        }

        ArrayList<String> symbols = new ArrayList<>(priceRecords.keySet());
        for (String symbol : symbols) {
            ArrayList<StockPrice> records = priceRecords.get(symbol);
            LocalDate firstDate = records.get(0).getDate();
            LocalDate lastDate = records.get(records.size() - 1).getDate();
            System.out.println("History: " + firstDate + " to " + lastDate);
            System.out.printf("Total return: %.2f%%%n", StockStats.calculateTotalReturn(records));
            System.out.printf("Monthly volatility: %.2f%%%n", StockStats.calculateVolatility(records));
            System.out.printf("Recent return: %.2f%%%n", StockStats.recentReturn(records));

        }
    }
}