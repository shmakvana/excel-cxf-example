package server.service;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;
import server.obj.Account;
import server.obj.Data;

import javax.ws.rs.core.MultivaluedMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AllUtils {

  public static final String COLUMN_NAME = "Name";
  public static final String COLUMN_NUMBER = "Number";
  public static final String DATE_FORMAT = "dd/MM/yyyy";

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
/*
    if (fileName.equals("xlsx")){
      //get data from excel sheel
    }else if(fileName.equals("csv")){
      //get data from csv file
    }else if(fileName.equals("other")){
      //get data from other file
    }
*/
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
      if (cell.getRichStringCellValue().getString().trim().equals(COLUMN_NAME)) {
        nameIndex = cell.getColumnIndex();
      } else if (cell.getRichStringCellValue().getString().trim().equals(COLUMN_NUMBER)) {
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
    return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
  }

  public static LocalDateTime parseLocalTime(String time, String zone) {
    if (StringUtils.isEmpty(time)) {
      return null;
    }
    try {
      org.joda.time.format.DateTimeFormatter df = DateTimeFormat.forPattern("HH:mm:ss");
      LocalTime start = df.withOffsetParsed().parseLocalTime(time);
      DateTimeZone zone0 = DateTimeZone.forID(zone);
      DateTime getExactTime = DateTime.now(zone0).withTime(start);
      System.out
          .printf("ParseLocalTime: Zone[%s], Time[%s] %n", zone, getExactTime.toLocalDateTime());
      return getExactTime.toLocalDateTime();
    } catch (Exception e) {
      return null;
    }
  }

  public static LocalDateTime getCurrentTime(String zone) {
    DateTimeZone dateTimeZone = DateTimeZone.forID(zone);
    LocalDateTime localDateTime = DateTime.now(dateTimeZone).toLocalDateTime();
    System.out.printf("getCurrentTime: Zone[%s], Time[%s] %n", zone, localDateTime);
    return localDateTime;
  }

  public static boolean validateTiming(String startTime, String endTime, String zoneId) {
    try {
      LocalDateTime startDate = parseLocalTime(startTime, zoneId);
      LocalDateTime endDate = parseLocalTime(endTime, zoneId);
      LocalDateTime currentTime = getCurrentTime(zoneId);
      System.out.println((currentTime.isAfter(startDate) && currentTime.isBefore(endDate)));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }


}
