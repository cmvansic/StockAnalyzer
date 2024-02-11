import java.io.*;
import java.time.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;



public class StockAnalyzer implements StockAnalyzerInterface {

    private final ArrayList<AbstractStock> loadedStocks = new ArrayList<>();
    @Override
    public ArrayList<AbstractStock> loadStockData(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + file.getName());
        }

        String fileName = file.getName();
        String stockSymbol = fileName.substring(0, fileName.indexOf('.'));

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

                // Check for null values in the data
                if (hasNullValues(data)) {
                    continue;  // Skip the stock if it has null values
                }

                // Assuming the CSV columns are in the order: symbol, timestamp, open, high, low, close, volume
                LocalDate timestamp = convertToLocalDate(data[0]);  // Assuming timestamp is in the second column

                Stock stock = new Stock(stockSymbol, convertToTimestampFromDate(timestamp), Double.parseDouble(data[1]),
                        Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]),
                        Double.parseDouble(data[6]));

                stocks.add(stock);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Add the loaded stocks to the global list
        loadedStocks.addAll(stocks);

        return stocks;
    }

    private long convertToTimestampFromDate(LocalDate date) {
        ZoneId systemZone = ZoneId.systemDefault();
        ZoneOffset currentOffsetForMyZone = systemZone.getRules().getOffset(Instant.now());
        return date.atTime(16, 0, 0).toInstant(currentOffsetForMyZone).toEpochMilli();
    }

    private LocalDate convertToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    // Helper method to check if the data array contains null values
    private boolean hasNullValues(String[] data) {
        for (String value : data) {
            if (value.equalsIgnoreCase("null")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<AbstractStock> listStocks() {
        return loadedStocks;
    }

    @Override
    public ArrayList<AbstractStock> listBySymbol(String symbol) {
        ArrayList<AbstractStock> result = new ArrayList<>();

        if (listStocks() != null) {
            for (AbstractStock stock : listStocks()) {
                if (stock.getSymbol().equals(symbol)) {
                    result.add(stock);
                }
            }
        }

        return result;
    }

    @Override
    public ArrayList<AbstractStock> listBySymbolDates(String symbol, LocalDate startDate, LocalDate endDate) {
        ArrayList<AbstractStock> result = new ArrayList<>();

        for (AbstractStock stock : listStocks()) {
            if (stock.getSymbol().equals(symbol) && isWithinDateRange(stock.getTimestamp(), startDate, endDate)) {
                result.add(stock);
            }
        }

        return result;
    }

    // Helper method to check if the timestamp is within the specified date range
    private boolean isWithinDateRange(long timestamp, LocalDate startDate, LocalDate endDate) {
        LocalDate stockDate = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
        return !stockDate.isBefore(startDate) && !stockDate.isAfter(endDate);
    }

    @Override
    public double averageHigh(String symbol, LocalDate startDate, LocalDate endDate) throws StockNotFoundException {
        ArrayList<AbstractStock> stocks = listBySymbolDates(symbol, startDate, endDate);

        if (stocks.isEmpty()) { //Switched from just being null to .isEmpty() now works!
            throw new StockNotFoundException(symbol);
        }

        //Calculation and return for the average low for found/loaded stocks.
        double sumHigh = 0;
        for (AbstractStock stock : stocks) {
            sumHigh += stock.getHigh(); // Iterating through the lows
        }

        return sumHigh / stocks.size();
    }

    @Override
    public double averageLow(String symbol, LocalDate startDate, LocalDate endDate) throws StockNotFoundException {
        ArrayList<AbstractStock> stocks = listBySymbolDates(symbol, startDate, endDate);

        if (stocks.isEmpty()) { //Switched from just being null to .isEmpty() now avgTest1 works!
            throw new StockNotFoundException(symbol);
        }

        //Calculation and return for the average low for found/loaded stocks.
        double sumLow = 0;
        for (AbstractStock stock : stocks) {
            sumLow += stock.getLow(); // Iterating through the lows
        }

        return sumLow / stocks.size();
    }

    @Override
    public double averageVolume(String symbol, LocalDate startDate, LocalDate endDate) throws StockNotFoundException {
        ArrayList<AbstractStock> stocks = listBySymbolDates(symbol, startDate, endDate);

        if (stocks.isEmpty()) { //Switched from just being null to .isEmpty() now works!
            throw new StockNotFoundException(symbol);
        }

        //Calculation and return for the average low for found/loaded stocks.
        double sumVol = 0;
        for (AbstractStock stock : stocks) {
            sumVol += stock.getVolume(); // Iterating through the volumes
        }

        return sumVol / stocks.size();
    }
}
