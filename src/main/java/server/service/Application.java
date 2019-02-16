package server.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import server.obj.Account;

public class Application {
  static int i=0;

  public static void main(String[] args) {

    Application application = new Application();
    application.printList();
  }


  public void printList(){
    List<Account> list = new ArrayList<>();

    for(int i = 1;i<20;i++) {
      list.add(new Account(String.valueOf(i), String.valueOf(i)));
    }

    final AtomicInteger counter = new AtomicInteger(0);
    final int size = 3;

    final Collection<List<Account>> partitioned = list.stream()
        .collect(Collectors.groupingBy(it -> {
          int i = counter.getAndIncrement() / size;
          System.out.println("i: " + i);
          return i;
        }))
        .values();

    List<Boolean> collect = partitioned.stream().map(Application::printList).collect(Collectors.toList());
    System.out.println(collect);

  }
  public static boolean printList(List<Account> accounts){
    i++;
    if(i == 3){
      throw new RuntimeException("I === 3");
    }
    System.out.println(accounts);
    return true;
  }

}
