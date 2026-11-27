
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


public class StockStats {
    // Total Return = Overall historical gain/loss
    public static double calculateTotalReturn(ArrayList<StockPrice> stocks) {
        if(stocks.size() < 2) { 
            throw new IllegalArgumentException("At least two price records are required.");
        }
        double firstPrice = stocks.get(0).getClose();
        double lastPrice = stocks.get(stocks.size() - 1).getClose();
        return ((lastPrice - firstPrice) / firstPrice) * 100; 
    }

    // CAGR(compound annual growth rate) 
    // = the constant yearly growth rate that would turn the first price into the last price.
    public static double calculateAnnualizedReturn(ArrayList<StockPrice> stocks) {
        if(stocks.size() < 2) {
            throw new IllegalArgumentException("At least two price records are required.");
        }
        double firstPrice = stocks.get(0).getClose();
        double lastPrice = stocks.get(stocks.size() - 1).getClose();
        LocalDate start = stocks.get(0).getDate();
        LocalDate end = stocks.get(stocks.size() - 1).getDate();
        double yearsBetween = ChronoUnit.DAYS.between(start, end) / 365.25;
        return (Math.pow(lastPrice / firstPrice, 1.0 / yearsBetween) - 1) * 100;
    }
    // Volitality = how much returns flucate 
    public static double calculateVolatility(ArrayList<StockPrice> stocks) {
        if(stocks.size() < 3) {
            throw new IllegalArgumentException("At least two price records are required.");
        }
        ArrayList<Double> returns = new ArrayList<>(); 
        double average = 0;
        double sumSquaredDifference = 0;
        double populationVariance = 0;
        for(int i = 1; i < stocks.size(); i++) {
            double currentPrice = stocks.get(i).getClose();
            double previousPrice = stocks.get(i - 1).getClose();
            double periodReturn = (currentPrice - previousPrice) / previousPrice;
            average += periodReturn;
            returns.add(periodReturn);
        }

        average /= returns.size();

        for(double periodReturn : returns) {
            double difference = periodReturn - average;
            sumSquaredDifference += difference * difference;
        }

        populationVariance = sumSquaredDifference / returns.size();
        return Math.sqrt(populationVariance) * 100;
    }

}
