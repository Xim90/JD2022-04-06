package by.it.avramchuk.jd02_02;

public class Shop {

    private final String name;

    public Shop(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Shop: "+name;
    }
}
