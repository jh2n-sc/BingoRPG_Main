package bingo;

public class Person implements Cloneable {
    private String name;
    private double money;
    private int IDnumber;

    Person(int IDnumber) {
        this.IDnumber = IDnumber;
        money = 0;
        name = "";
    }
    Person(int IDnumber, String name) {
        this.IDnumber = IDnumber;
        this.name = new String(name);
        money = 0;
    }
    Person(int IDnumber, String name, double money) {
        this.IDnumber = IDnumber;
        this.name = new String(name);
        this.money = money;
    }
    @Override
    public Person clone() {
        try {
            Person person = (Person) super.clone();
            person.name = new String(this.name);
            return person;
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    Person(Person person) {
        this.name = new String(person.name);
        this.money = person.money;
        this.IDnumber = person.IDnumber;
    }

    public void setMoney(double money) {
        this.money = money;
    }
    public double getMoney() {
        return money;
    }
    public void subtractMoney(double negMoney) {
        this.money -= negMoney;
    }
    public void addMoney(double money) {
        this.money += money;
    }

    public void setName(String name) {
        this.name = new String(name);
    }
    public String getName() {
        return new String(name);
    }

    public int getIDnumber() {
        return IDnumber;
    }
    @Override
    public String toString() {
        return new String(name);
    }
}
