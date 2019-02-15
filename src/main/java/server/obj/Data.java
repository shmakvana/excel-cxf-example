
package server.obj;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Data{

  private LocalDate localDate;
  private long pageCount;
  private List<Account> list = new ArrayList<>();

  public LocalDate getLocalDate() {
    return localDate;
  }

  public void setLocalDate(LocalDate localDate) {
    this.localDate = localDate;
  }

  public long getPageCount() {
    return pageCount;
  }

  public void setPageCount(long pageCount) {
    this.pageCount = pageCount;
  }

  public List<Account> getList() {
    return list;
  }

  public void setList(List<Account> list) {
    this.list = list;
  }

  @Override
  public String toString() {
    return "Data{" +
            "localDate=" + localDate +
            ", pageCount=" + pageCount +
            ", list=" + list +
            '}';
  }
}