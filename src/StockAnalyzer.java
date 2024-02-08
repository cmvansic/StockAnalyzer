import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

public class StockAnalyzer implements StockAnalyzerInterface {

    @Override
    public ArrayList<AbstractStock> loadStockData(File file) throws FileNotFoundException {
        return null;
    }

    @Override
    public ArrayList<AbstractStock> listStocks() {
        return null;
    }

    @Override
    public ArrayList<AbstractStock> listBySymbol(String symbol) {
        return null;
    }

    @Override
    public ArrayList<AbstractStock> listBySymbolDates(String symbol, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public double averageHigh(String symbol, LocalDate startDate, LocalDate endDate) throws StockNotFoundException {
        return 0;
    }

    @Override
    public double averageLow(String symbol, LocalDate startDate, LocalDate endDate) throws StockNotFoundException {
        return 0;
    }

    @Override
    public double averageVolume(String symbol, LocalDate startDate, LocalDate endDate) throws StockNotFoundException {
        return 0;
    }
}
