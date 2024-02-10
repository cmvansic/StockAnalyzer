import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Date;

public class StockAnalyzer implements StockAnalyzerInterface {

    @Override
    public ArrayList<AbstractStock> loadStockData(File file) throws FileNotFoundException {


        // ArrayList to store the data
        ArrayList<AbstractStock> stocks = new ArrayList<>();

        String line;
        String csvSplit = ",";
        boolean isFirstLine = true;  // Flag to skip the header row

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;  // Skip the header row
                }

                String[] data = line.split(csvSplit);

                // Assuming the CSV columns are in the order: symbol, timestamp, open, high, low, close, volume
                long timestamp = convertToTimestamp(data[0]);

                Stock stock = new Stock(data[0], timestamp, Double.parseDouble(data[2]),
                        Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]),
                        Double.parseDouble(data[6]));

                stocks.add(stock);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stocks;
    }

    private long convertToTimestamp(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(dateString);
            return date.getTime() / 1000;  // Convert to seconds
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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
