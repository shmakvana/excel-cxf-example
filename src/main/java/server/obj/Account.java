package server.obj;

public class Account {
    private String Name;
    private String Number;

    public String getName() {
      return Name;
    }

    public void setName(String name) {
      Name = name;
    }

    public String getNumber() {
      return Number;
    }

    public void setNumber(String number) {
      Number = number;
    }

    public Account(String name, String number) {
      Name = name;
      Number = number;
    }

    @Override
    public String toString() {
      return "Account{" +
              "Name='" + Name + '\'' +
              ", Number='" + Number + '\'' +
              "}\n";
    }
  }
