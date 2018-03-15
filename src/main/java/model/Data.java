package model;

public class Data {

    private String name;
    private String ticker;
    private Double value;

    public Data() {}

    public Data(String name, String ticker, Double value) {
        this.name = name;
        this.ticker = ticker;
        this.value = value;
    }

    public String toString() { return "lalala"; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}