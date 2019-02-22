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
      Data data = new Data();
      data.setLocalDate(AllUtils.parseLocalDate(records.get(0).get(1)));
      data.setPageCount(Long.valueOf(records.get(records.size() - 1).get(1)));
      for(int n = 2;n <records.size() - 1; n++){
        data.getList().add(new Account(records.get(n).get(0), records.get(n).get(1)));
      }
      System.out.println(data);
    }
  }
}