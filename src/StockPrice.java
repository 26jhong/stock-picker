
import java.time.LocalDate;

public class StockPrice {
    private String symbol;
    private LocalDate date;
    private double close;

    public StockPrice(String symbol, LocalDate date, double close) {
        this.symbol = symbol;
        this.date = date;
        this.close = close;
    }

    public String getSymbol() {
        return symbol;
    }
    
    public LocalDate getDate() {
        return date;
    }

    public double getClose() {
        return close;
    }


}