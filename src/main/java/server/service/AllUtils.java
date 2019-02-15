package server.service;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import server.obj.Account;
import server.obj.Data;

import javax.ws.rs.core.MultivaluedMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AllUtils {

  public static String getFileName(MultivaluedMap<String, String> multivaluedMap) throws Exception {
    String[] contentDisposition = multivaluedMap.getFirst("Content-Disposition").split(";");
    for (String filename : contentDisposition) {

      if ((filename.trim().startsWith("filename"))) {
        String[] name = filename.split("=");
        return name[1].trim().replaceAll("\"", "");
      }
    }
    throw new Exception("UNKNOWN_FILE_FOUND");
  }

  public static Data getData(Attachment attachment) throws Exception {
    Data data = new Data();
    String fileName = getFileName(attachment.getHeaders());
    Workbook workbook = new XSSFWorkbook(attachment.getDataHandler().getInputStream());
    DataFormatter dataFormatter = new DataFormatter();
    Sheet sheet = workbook.getSheetAt(0);
    Row rowHeaderDate = sheet.getRow(0);
    Row rowTotalCount = sheet.getRow(1);
    Row rowHeader = sheet.getRow(2);
    Cell cellHeaderDate = rowHeaderDate.getCell(1);
    Cell cellTotalCount = rowTotalCount.getCell(1);
    data.setLocalDate(parseLocalDate(dataFormatter.formatCellValue(cellHeaderDate)));
    data.setPageCount(Long.valueOf(dataFormatter.formatCellValue(cellTotalCount)));
    int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
    int nameIndex = 0;
    int numberIndex = 0;
    for (Cell cell : rowHeader) {
      if (cell.getRichStringCellValue().getString().trim().equals("Name")) {
        nameIndex = cell.getColumnIndex();
      } else if (cell.getRichStringCellValue().getString().trim().equals("Number")) {
        numberIndex = cell.getColumnIndex();
      }
    }
    for (int i = 3; i < physicalNumberOfRows; i++) {
      Row row = sheet.getRow(i);
      data.getList().add(new Account(dataFormatter.formatCellValue(row.getCell(nameIndex)), dataFormatter.formatCellValue(row.getCell(numberIndex))));
    }
    return data;
  }

  public static LocalDate parseLocalDate(String date) {
    if (StringUtils.isEmpty(date)) {
      return null;
    }
    return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }

}
