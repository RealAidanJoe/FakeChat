package org.chat.bean;

public class Test {
    private String Name;
    private int number;

    public Test() {
        Name = "name";
        number = 666;
    }

    @Override
    public String toString() {
        return "Test{" +
                "Name='" + Name + '\'' +
                ", number=" + number +
                '}';
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
