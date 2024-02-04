import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class StockAnalyzerTest {

	// Inheritance

	@Test
	public void stockAnalyzerInheritance ( ) {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		if ( !( stockAnalyzer instanceof StockAnalyzerInterface ) ) {
			fail( "FAIL: StockAnalyzer does not implement StockAnalyzerInterface" );
		}
	}

	@Test
	public void stockInheritance ( ) {
		Stock stock = new Stock ("NONE", 12345678L, 1.2, 3.4, 1.2, 2.3, 567.0 );
		if ( !( stock instanceof AbstractStock ) ) {
			fail( "FAIL: Stock does not extend AbstractStock" );
		}
	}

	// Loading from data file
	@Test
	public void loadStockData1 ( ) {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		File file = new File( "AAPL.csv" );
		int items = 251;
		try {
			ArrayList<AbstractStock> list = stockAnalyzer.loadStockData ( file );
			if ( list.size () != items ) {
				fail( String.format ( "FAIL: loadStockData( %s ) loaded %d items, should be %d.", file, list.size (), items ) );
			}
		} catch ( FileNotFoundException e ) {
			fail( "FAIL: FileNotFound " + file );
		}
	}

	@Test
	public void loadStockData2 ( ) {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		File file = new File( "MSFT.csv" );
		int items = 251;
		try {
			ArrayList<AbstractStock> list = stockAnalyzer.loadStockData ( file );
			if ( list.size () != items ) {
				fail( String.format ( "FAIL: loadStockData( %s ) loaded %d items, should be %d.", file, list.size (), items ) );
			}
		} catch ( FileNotFoundException e ) {
			fail( "FAIL: FileNotFound " + file );
		}
	}

	@Test
	public void loadStockData3 ( ) {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		File file = new File( "GOOG.csv" );
		int items = 251;
		try {
			ArrayList<AbstractStock> list = stockAnalyzer.loadStockData ( file );
			if ( list.size () != items ) {
				fail( String.format ( "FAIL: loadStockData( %s ) loaded %d items, should be %d.", file, list.size (), items ) );
			}
		} catch ( FileNotFoundException e ) {
			fail( "FAIL: FileNotFound " + file );
		}
	}

	@Test
	public void loadStockData4 ( ) {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		File file = new File( "AMZN.csv" );
		int items = 251;
		try {
			ArrayList<AbstractStock> list = stockAnalyzer.loadStockData ( file );
			if ( list.size () != items ) {
				fail( String.format ( "FAIL: loadStockData( %s ) loaded %d items, should be %d.", file, list.size (), items ) );
			}
		} catch ( FileNotFoundException e ) {
			fail( "FAIL: FileNotFound " + file );
		}
	}

	@Test
	public void loadStockData5 ( ) {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		File file = new File( "SPCE.csv" );
		int items = 190;
		try {
			ArrayList<AbstractStock> list = stockAnalyzer.loadStockData ( file );
			if ( list.size () != items ) {
				fail( String.format ( "FAIL: loadStockData( %s ) loaded %d items, should be %d.", file, list.size (), items ) );
			}
		} catch ( FileNotFoundException e ) {
			fail( "FAIL: FileNotFound " + file );
		}
	}

	@Test
	public void loadStockData6 ( ) {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		File file = new File( "GOLD.csv" );
		try {
			ArrayList<AbstractStock> list = stockAnalyzer.loadStockData ( file );
			fail( "FAIL: No FileNotFoundException " + file );
		} catch ( FileNotFoundException e ) {
			// Success: Expected result
		}
	}

	// Listing all loaded stocks
	@Test
	public void listStocks1 ( ) {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		ArrayList<AbstractStock> list = stockAnalyzer.listStocks ();
		if ( !list.isEmpty () ) {
			fail( "FAIL: Stocks list should be empty " + list );
		}
	}

	// Listing all loaded stocks
	@Test
	public void listStocks2 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "AAPL.csv") );
		ArrayList<AbstractStock> list = stockAnalyzer.listStocks ();
		int items = 251;
		if ( list.size () != items ) {
			fail( String.format ( "FAIL: listStocks( ) contained %d items, expected %d.", list.size (), items ) );
		}
	}

	@Test
	public void listStocks3 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "AAPL.csv") );
		stockAnalyzer.loadStockData ( new File( "SPCE.csv") );
		ArrayList<AbstractStock> list = stockAnalyzer.listStocks ();
		int items = 251 + 190;
		if ( list.size () != items ) {
			fail( String.format ( "FAIL: listStocks( ) contained %d items, expected %d.", list.size (), items ) );
		}
	}

	@Test
	public void listStocks4 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "AAPL.csv") );
		stockAnalyzer.loadStockData ( new File( "SPCE.csv") );
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		ArrayList<AbstractStock> list = stockAnalyzer.listStocks ();
		int items = 251 + 190 + 251;
		if ( list.size () != items ) {
			fail( String.format ( "FAIL: listStocks( ) contained %d items, expected %d.", list.size (), items ) );
		}
	}


	// List by symbol
	@Test
	public void listBySymbol1 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		ArrayList<AbstractStock> list = stockAnalyzer.listBySymbol ( "NONE" );
		int items = 0;
		if ( list.size () != items ) {
			fail( String.format ( "FAIL: listBySymbol( ) contained %d items, expected %d.", list.size (), items ) );
		}
	}

	@Test
	public void listBySymbol2 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		ArrayList<AbstractStock> list = stockAnalyzer.listBySymbol ( "GOOG" );
		int items = 251;
		if ( list.size () != items ) {
			fail( String.format ( "FAIL: listBySymbol( ) contained %d items, expected %d.", list.size (), items ) );
		}
	}

	@Test
	public void listBySymbol3 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		stockAnalyzer.loadStockData ( new File( "SPCE.csv") );
		ArrayList<AbstractStock> list = stockAnalyzer.listBySymbol ( "SPCE" );
		int items = 190;
		if ( list.size () != items ) {
			fail( String.format ( "FAIL: listBySymbol( ) contained %d items, expected %d.", list.size (), items ) );
		}
	}

	@Test
	public void listBySymbol4 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "SPCE.csv") );
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		ArrayList<AbstractStock> list = stockAnalyzer.listBySymbol ( "SPCE" );
		int items = 190;
		if ( list.size () != items ) {
			fail( String.format ( "FAIL: listBySymbol( ) contained %d items, expected %d.", list.size (), items ) );
		}
	}

	@Test
	public void listBySymbol5 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "AAPL.csv") );
		stockAnalyzer.loadStockData ( new File( "SPCE.csv") );
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		ArrayList<AbstractStock> list = stockAnalyzer.listBySymbol ( "SPCE" );
		int items = 190;
		if ( list.size () != items ) {
			fail( String.format ( "FAIL: listBySymbol( ) contained %d items, expected %d.", list.size (), items ) );
		}
	}

	// List by symbols and date range
	@Test
	public void listBySymbolDates1 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "AAPL.csv") );
		stockAnalyzer.loadStockData ( new File( "SPCE.csv") );
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		LocalDate startDate = LocalDate.of( 2019, 1, 1);
		LocalDate endDate = LocalDate.of(2019,12,31);
		ArrayList<AbstractStock> list = stockAnalyzer.listBySymbolDates ( "SPCE", startDate, endDate );
		int items = 190;
		if ( list.size () != items ) {
			fail( String.format ( "FAIL: listBySymbolDates( ) contained %d items, expected %d.", list.size (), items ) );
		}
	}

	@Test
	public void listBySymbolDates2 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "AAPL.csv") );
		stockAnalyzer.loadStockData ( new File( "SPCE.csv") );
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		LocalDate startDate = LocalDate.of(2019,1,1);
		LocalDate endDate = LocalDate.of(2019,4,30);
		ArrayList<AbstractStock> list = stockAnalyzer.listBySymbolDates ( "SPCE", startDate, endDate );
		int items = 36;
		if ( list.size () != items ) {
			fail( String.format ( "FAIL: listBySymbolDates( ) contained %d items, expected %d.", list.size (), items ) );
		}
	}

	@Test
	public void listBySymbolDates3 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "AAPL.csv") );
		stockAnalyzer.loadStockData ( new File( "SPCE.csv") );
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		LocalDate startDate = LocalDate.of(2019,1,1);
		LocalDate endDate = LocalDate.of(2019,1,31);
		ArrayList<AbstractStock> list = stockAnalyzer.listBySymbolDates ( "SPCE", startDate, endDate );
		int items = 0;
		if ( list.size () != items ) {
			fail( String.format ( "FAIL: listBySymbolDates( ) contained %d items, expected %d.", list.size (), items ) );
		}
	}

	// Average High
	@Test
	public void averageHigh1 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "AAPL.csv") );
		stockAnalyzer.loadStockData ( new File( "SPCE.csv") );
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		String symbol = "SPCE";
		LocalDate startDate = LocalDate.of(2019,1,1);
		LocalDate endDate = LocalDate.of(2019,1,31);
		try {
			double result = stockAnalyzer.averageHigh ( symbol, startDate, endDate );
			fail ( "FAIL: averageHigh returned " + result + " expected no stocks found " + symbol );
		} catch ( StockNotFoundException e ) {
			// SUCCESS Expected result
		}
	}

	private boolean fuzzyEquals( double a, double b, double fuzz ) {
		return Math.abs(a - b) < fuzz;
	}

	@Test
	public void averageHigh2 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "AAPL.csv") );
		stockAnalyzer.loadStockData ( new File( "SPCE.csv") );
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		String symbol = "SPCE";
		LocalDate startDate = LocalDate.of(2019,1,1);
		LocalDate endDate = LocalDate.of(2019,12,31);
		try {
			double result = stockAnalyzer.averageHigh ( symbol, startDate, endDate );
			double expected = 10.215532;
			if ( !fuzzyEquals( result, expected, 0.0001) ) {
				fail ( String.format (
						  "FAIL: averageHigh( %s, %s, %s ) returned %f, expected %f.",
						  symbol, startDate, endDate, result, expected ) );
			}
		} catch ( StockNotFoundException e ) {
			fail ( "FAIL: averageHigh no stocks found " + symbol );
		}
	}

	@Test
	public void averageHigh3 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "AAPL.csv") );
		stockAnalyzer.loadStockData ( new File( "SPCE.csv") );
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		String symbol = "SPCE";
		LocalDate startDate = LocalDate.of(2019,8,1);
		LocalDate endDate = LocalDate.of(2019,11,30);
		try {
			double result = stockAnalyzer.averageHigh ( symbol, startDate, endDate );
			double expected = 10.204543;
			if ( !fuzzyEquals( result, expected, 0.0001) ) {
				fail ( String.format (
						  "FAIL: averageHigh( %s, %s, %s ) returned %f, expected %f.",
						  symbol, startDate, endDate, result, expected ) );
			}
		} catch ( StockNotFoundException e ) {
			fail ( "FAIL: averageHigh no stocks found " + symbol );
		}
	}

	// Average Low
	@Test
	public void averageLow1 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "AAPL.csv") );
		stockAnalyzer.loadStockData ( new File( "SPCE.csv") );
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		String symbol = "SPCE";
		LocalDate startDate = LocalDate.of(2019,1,1);
		LocalDate endDate = LocalDate.of(2019,1,31);
		try {
			double result = stockAnalyzer.averageLow ( symbol, startDate, endDate );
			fail ( "FAIL: averageLow returned " + result + " expected no stocks found " + symbol );
		} catch ( StockNotFoundException e ) {
			// SUCCESS Expected result
		}
	}

	@Test
	public void averageLow2 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "AAPL.csv") );
		stockAnalyzer.loadStockData ( new File( "SPCE.csv") );
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		String symbol = "SPCE";
		LocalDate startDate = LocalDate.of(2019,1,1);
		LocalDate endDate = LocalDate.of(2019,12,31);
		try {
			double result = stockAnalyzer.averageLow ( symbol, startDate, endDate );
			double expected = 10.041947;
			if ( !fuzzyEquals( result, expected, 0.0001) ) {
				fail ( String.format (
						  "FAIL: averageLow( %s, %s, %s ) returned %f, expected %f.",
						  symbol, startDate, endDate, result, expected ) );
			}
		} catch ( StockNotFoundException e ) {
			fail ( "FAIL: averageLow no stocks found " + symbol );
		}
	}

	@Test
	public void averageLow3 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "AAPL.csv") );
		stockAnalyzer.loadStockData ( new File( "SPCE.csv") );
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		String symbol = "SPCE";
		LocalDate startDate = LocalDate.of(2019,8,1);
		LocalDate endDate = LocalDate.of(2019,11,30);
		try {
			double result = stockAnalyzer.averageLow ( symbol, startDate, endDate );
			double expected = 9.968229;
			if ( !fuzzyEquals( result, expected, 0.0001) ) {
				fail ( String.format (
						  "FAIL: averageLow( %s, %s, %s ) returned %f, expected %f.",
						  symbol, startDate, endDate, result, expected ) );
			}
		} catch ( StockNotFoundException e ) {
			fail ( "FAIL: averageLow no stocks found " + symbol );
		}
	}

	// Average Volume
	@Test
	public void averageVolume1 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "AAPL.csv") );
		stockAnalyzer.loadStockData ( new File( "SPCE.csv") );
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		String symbol = "SPCE";
		LocalDate startDate = LocalDate.of(2019,1,1);
		LocalDate endDate = LocalDate.of(2019,1,31);
		try {
			double result = stockAnalyzer.averageVolume ( symbol, startDate, endDate );
			fail ( "FAIL: averageVolume returned " + result + " expected no stocks found " + symbol );
		} catch ( StockNotFoundException e ) {
			// SUCCESS Expected result
		}
	}

	@Test
	public void averageVolume2 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "AAPL.csv") );
		stockAnalyzer.loadStockData ( new File( "SPCE.csv") );
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		String symbol = "SPCE";
		LocalDate startDate = LocalDate.of(2019,1,1);
		LocalDate endDate = LocalDate.of(2019,12,31);
		try {
			double result = stockAnalyzer.averageVolume ( symbol, startDate, endDate );
			double expected = 1449001.578947;
			if ( !fuzzyEquals( result, expected, 0.0001) ) {
				fail ( String.format (
						  "FAIL: averageVolume( %s, %s, %s ) returned %f, expected %f.",
						  symbol, startDate, endDate, result, expected ) );
			}
		} catch ( StockNotFoundException e ) {
			fail ( "FAIL: averageVolume no stocks found " + symbol );
		}
	}

	@Test
	public void averageVolume3 ( ) throws FileNotFoundException {
		StockAnalyzer stockAnalyzer = new StockAnalyzer ();
		stockAnalyzer.loadStockData ( new File( "AAPL.csv") );
		stockAnalyzer.loadStockData ( new File( "SPCE.csv") );
		stockAnalyzer.loadStockData ( new File( "GOOG.csv") );
		String symbol = "SPCE";
		LocalDate startDate = LocalDate.of(2019,8,1);
		LocalDate endDate = LocalDate.of(2019,11,30);
		try {
			double result = stockAnalyzer.averageVolume ( symbol, startDate, endDate );
			double expected = 1722938.571429;
			if ( !fuzzyEquals( result, expected, 0.0001) ) {
				fail ( String.format (
						  "FAIL: averageVolume( %s, %s, %s ) returned %f, expected %f.",
						  symbol, startDate, endDate, result, expected ) );
			}
		} catch ( StockNotFoundException e ) {
			fail ( "FAIL: averageVolume no stocks found " + symbol );
		}
	}

}