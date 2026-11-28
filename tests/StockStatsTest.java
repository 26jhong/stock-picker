import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class StockStatsTest {
    private static final double TOLERANCE = 0.000001;

    private ArrayList<StockPrice> createPrices(double[] prices) {
        ArrayList<StockPrice> records = new ArrayList<>();
        LocalDate start = LocalDate.of(2024, 1, 1);
        for (int i = 0; i < prices.length; i++) {
            records.add(new StockPrice("AAA", start.plusYears(4L * i), prices[i]));
        }
        return records;
    }
    
    @Test
    public void totalReturnIsCorrect() {
        double[] prices = {100, 121};

        double actual = StockStats.calculateTotalReturn(createPrices(prices));

        assertEquals(21.0, actual, 0.000001);
    }

    @Test
    public void annualizedReturnIsCorrect() {
        // 2020 to 2024 is exactly four years using days / 365.25.
        double[] prices = {100, 146.41};

        double actual = StockStats.calculateAnnualizedReturn(createPrices(prices));

        assertEquals(10.0, actual, 0.000001);
    }

    @Test
    public void volatilityIsCorrect() {
        // Period returns are +10% and -10%.
        double[] prices = {100, 110, 99};

        double actual = StockStats.calculateVolatility(createPrices(prices));

        assertEquals(10.0, actual, 0.000001);
    }

    @Test
    public void totalReturnRejectsTooFewRecords() {
        double[] prices = {100};
        ArrayList<StockPrice> stocks = createPrices(prices);

        assertThrows(IllegalArgumentException.class, () -> StockStats.calculateTotalReturn(stocks));
    }

    @Test
    public void annualizedReturnRejectsTooFewRecords() {
        double[] prices = {100};
        ArrayList<StockPrice> stocks = createPrices(prices);

        assertThrows(IllegalArgumentException.class, () -> StockStats.calculateAnnualizedReturn(stocks));
    }

    @Test
    public void volatilityRejectsTooFewRecords() {
        double[] prices = {100, 110};
        ArrayList<StockPrice> stocks = createPrices(prices);

        assertThrows(IllegalArgumentException.class, () -> StockStats.calculateVolatility(stocks));
    }

    
}
