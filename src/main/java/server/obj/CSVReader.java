package server.obj;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import server.service.AllUtils;

public class CSVReader {

  public static void main(String[] args) throws IOException {
    parseCSV();
  }

  public static void parseCSV() throws IOException {
    try (CSVParser parser = new CSVParser(new FileReader("/home/admin1/Desktop/users.csv"),
        CSVFormat.EXCEL.withSkipHeaderRecord())) {

      List<CSVRecord> records = parser.getRecords();
      long limit = records.size() - 2L;
      boolean first = true;
      Data data = new Data();
      for (CSVRecord record : records) {

        if (first) {
          data.setLocalDate(AllUtils.parseLocalDate(record.get(1)));
          first = false;
          continue;
        }
        if (limit-- == 0) {
          break;
        }
        data.getList().add(new Account(record.get(0), record.get(1)));
      }
      data.setPageCount(Long.valueOf(records.get(records.size() - 1).get(1)));
      System.out.println(data);
    }
  }
}