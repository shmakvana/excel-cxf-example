import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TestApplication {

  public static void main(String[] args) {
    System.out.println("adasd");
    try {
      example003ReadCsvWithRowProcessor();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void example003ReadCsvWithRowProcessor() throws Exception {
    CsvParserSettings parserSettings = new CsvParserSettings();
    parserSettings.setLineSeparatorDetectionEnabled(true);
    RowListProcessor rowProcessor = new RowListProcessor();
    parserSettings.setProcessor(rowProcessor);
    parserSettings.setHeaderExtractionEnabled(true);
    CsvParser parser = new CsvParser(parserSettings);
    parser.parse(getFile("/home/admin1/Desktop/users.csv"));
    String[] headers = rowProcessor.getHeaders();
    List<String[]> rows = rowProcessor.getRows();
    int index = rows.size() - 1;
    String[] strings = rows.get(0);
    String[] trailerCount = rows.get(index);

    System.out.println("-------");
    System.out.println(Arrays.toString(headers));
    System.out.println("-------");
    System.out.println(Arrays.toString(strings));
    System.out.println("-------");
    System.out.println(Arrays.toString(trailerCount));
    System.out.println("-------");
    rows.remove(0);
    rows.remove(index - 1);

    print(headers, rows);
  }

  public static Reader getFile(String file) throws IOException {
    return Files.newBufferedReader(Paths.get(file));
  }

  public static void print(Object[] headers, Collection<?> rows) {

    if (headers != null) {
      System.out.println(Arrays.toString(headers));
      System.out.println("=======================");
    }
    int rowCount = 1;
    for (Object row : rows) {

      Object[] row1 = (Object[]) row;
      if (row1.length != 2) {
        break;
      }
      Object o = row1[0];
      Object o1 = row1[1];
      System.out.println(o + " - " + o1);

      System.out.println((rowCount++) + " " + Arrays.toString(row1));
      System.out.println("-----------------------");
    }
  }

}
