import java.io.*;
import java.time.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * This java program takes a stock .csv file, for example "TSLA.csv" and analyzes it through different methods.
 * Date Last Modified 2/12/2024
 *
 * @author Caden VanSickle & Brenden Keahl
 * CS1122 Spring Semester
 *
 * Lab Section L03, Group 76
 *
 */

public class StockAnalyzer implements StockAnalyzerInterface {
    private final ArrayList<AbstractStock> stocksLoaded = new ArrayList<>(); //Used to give a list of stocks for the listStock method

    /**
     *
     * loadStockData method takes in a csv file and stores the information so that it can be ready by other methods.
     * This information is stored by taking the strings of each csv column and splitting the nodes with a comma, then
     * changes the strings to numbers by parsing the data.
     *
     * @param file any stock.csv file.
     *
     * @return ArrayLists with stock data.
     *
     * @throws FileNotFoundException If the file processed doesn't exist, throw this Exception.
     */
    @Override
    public ArrayList<AbstractStock> loadStockData(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + file.getName());
        }

        String fileName = file.getName();
        String stockSymbol = fileName.substring(0, fileName.indexOf('.')); //Changed from substring(0,3) to fix test cases.

        // ArrayList to store the csv data.
        ArrayList<AbstractStock> stocks = new ArrayList<>();

        String line;
        String csvSplit = ",";
        boolean isFirstLine = true;  //Statement that will skip the first column row, so it doesn't get counted in.

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(csvSplit);

                // Check for null values in the data
                if (hasNullValues(data)) {
                    continue;  // Skip the stock if there are any null values within the data.
                }


                LocalDate timestamp = convertToLocalDate(data[0]);  //used to convert the csv date given into a usable timestamp for the program

                Stock stock = new Stock(stockSymbol, convertToTimestampFromDate(timestamp), Double.parseDouble(data[1]),
                        Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]),
                        Double.parseDouble(data[6])); // ^^ Above convertToTimeStampFromDate gives the millisecond value.

                stocks.add(stock);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Add stocks loaded from the loadStockData method to be used for the listStocks method
        stocksLoaded.addAll(stocks); //Bigger scope as well.

        return stocks;
    }

    //You can convert from a String to a LocalDateTime to a millisecond timestamp as follows
    //^ Both helpers split this information to make it easier to understand

    //Change into milli.
    private long convertToTimestampFromDate(LocalDate date) {
        ZoneId systemZone = ZoneId.systemDefault();
        ZoneOffset currentOffsetForMyZone = systemZone.getRules().getOffset(Instant.now());
        return date.atTime(16, 0, 0).toInstant(currentOffsetForMyZone).toEpochMilli();
    }

    //Converting to local date time
    private LocalDate convertToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    // Used to clean up code in loadStockData when looking for null values.
    private boolean hasNullValues(String[] data) {
        for (String value : data) {
            if (value.equalsIgnoreCase("null")) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * Global list of all stocks loaded.
     *
     * @return list of stocks that have been loaded
     */
    @Override
    public ArrayList<AbstractStock> listStocks() {
        return stocksLoaded;
    }

    /**
     *
     * @param symbol STOCK symbol
     * @return a list of all stocks that have been loaded
     */
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

    /**
     *
     * @param symbol STOCK SYMBOL
     * @param startDate START DATE IN GIVEN
     * @param endDate END DATE GIVEN
     * @return a list of stocks with the specified symbol recorded between
     * (and including) a start and end date.
     */
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

    //method to check if the timestamp given exists within the csv file
    private boolean isWithinDateRange(long timestamp, LocalDate startDate, LocalDate endDate) {
        LocalDate stockDate = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
        return !stockDate.isBefore(startDate) && !stockDate.isAfter(endDate);
    }

    /**
     *
     * @param symbol STOCK SYMBOL
     * @param startDate START DATE GIVEN
     * @param endDate END DATE GIVEN
     *
     * @return the average high value of all stocks with the specified symbol
     * recorded between (and including) the start and end dates.
     *
     * @throws StockNotFoundException Thrown when "STOCK".csv isn't found
     */
    @Override
    public double averageHigh(String symbol, LocalDate startDate, LocalDate endDate) throws StockNotFoundException {
        ArrayList<AbstractStock> stocks = listBySymbolDates(symbol, startDate, endDate);

        if (stocks.isEmpty()) { //Switched from just being null to .isEmpty() now works!
            throw new StockNotFoundException(symbol);
        }

        //Calculation and return for the average high for found/loaded stocks.
        double sumHigh = 0;
        for (AbstractStock stock : stocks) {
            sumHigh += stock.getHigh(); // Iterating through the highs
        }

        return sumHigh / stocks.size();
    }

    /**
     *
     * @param symbol STOCK SYMBOL
     * @param startDate GIVEN START DATE
     * @param endDate GIVEN END DATE
     *
     * @return the average low value of all stocks with the specified symbol
     * recorded between (and including) the start and end dates.
     *
     * @throws StockNotFoundException Thrown when "STOCK".csv isn't found
     */

    @Override
    public double averageLow(String symbol, LocalDate startDate, LocalDate endDate) throws StockNotFoundException {
        ArrayList<AbstractStock> stocks = listBySymbolDates(symbol, startDate, endDate);

        if (stocks.isEmpty()) { //Switched from just being null to .isEmpty() now avgTest works!
            throw new StockNotFoundException(symbol);
        }

        //Calculation and return for the average high for found/loaded stocks.
        double sumLow = 0;
        for (AbstractStock stock : stocks) {
            sumLow += stock.getLow(); // Iterating through the lows
        }

        return sumLow / stocks.size();
    }

    /**
     *
     * @param symbol STOCK SYMBOL
     * @param startDate GIVEN START DATE
     * @param endDate GIVEN END DATE
     *
     * @return the average volume value of all stocks with the specified symbol
     * recorded between (and including) the start and end dates.
     *
     * @throws StockNotFoundException Thrown when "STOCK".csv isn't found
     */

    @Override
    public double averageVolume(String symbol, LocalDate startDate, LocalDate endDate) throws StockNotFoundException {
        ArrayList<AbstractStock> stocks = listBySymbolDates(symbol, startDate, endDate);

        if (stocks.isEmpty()) { //Switched from just being null to .isEmpty() now works!
            throw new StockNotFoundException(symbol);
        }

        //Calculation and return for the average volume for found/loaded stocks.
        double sumVol = 0;
        for (AbstractStock stock : stocks) {
            sumVol += stock.getVolume(); // Iterating through the volumes
        }

        return sumVol / stocks.size();
    }
}
