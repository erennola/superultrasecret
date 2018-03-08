package model;

public class Data {

    private String name;
    private String ticker;
    private String value;

    public Data() {}

    public Data(String name, String ticker, String value) {
        this.name = name;
        this.ticker = ticker;
        this.value = value;
    }


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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}