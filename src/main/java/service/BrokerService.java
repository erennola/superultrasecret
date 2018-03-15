package service;

import dao.BrokerDao;
import model.Data;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class BrokerService {

    private final Map<String, String> tickerNames;
    private final Map<String, Integer> stockQuantity;

    public BrokerService() {
        tickerNames = readPropertiesFile("nombres.properties");
        stockQuantity = readPropertiesFile("stock-inicial.properties")
                        .entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> Integer.valueOf(e.getValue())));
    }


    private BrokerDao dao = new BrokerDao();

    public Data getBrokerData(String ticker) {
        String name = tickerNames.get(ticker);
        Double price = getStockPrice(ticker);
        return new Data(name, ticker, price);
    }

    public boolean buyStock(Map<String, Integer> transactionRequest) {
        Map<String, Integer> quantitiesToBuy = transactionRequest.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> {
                    Double stockPrice = getStockPrice(e.getKey());
                    Integer quantityToBuy = (int) (e.getValue() / stockPrice)  ;
                    Double remaining = e.getValue() % stockPrice;

                    if (stockQuantity.get(e.getKey()) - quantityToBuy < 0) {
                        throw new RuntimeException("There is no enough stock for " + e.getKey());
                    }
                    return quantityToBuy;
                }
        ));

        for (Map.Entry<String, Integer> e : quantitiesToBuy.entrySet()) {
            stockQuantity.put(e.getKey(), stockQuantity.get(e.getKey()) - e.getValue());
        }

        return true;
    }

    private Double getStockPrice(String ticker) {
        String value = dao.getTickerPrice(ticker).getValue();
        return Double.valueOf(value);
    }

    private Map<String, String> readPropertiesFile(String fileName) {
        Properties prop = new Properties();
        InputStream input = null;
        Map<String, String> result = new HashMap<>();

        try {
            input = BrokerService.class.getClassLoader().getResourceAsStream(fileName);

            if (input != null) {
                prop.load(input);

                Enumeration<?> e = prop.propertyNames();
                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();
                    String value = prop.getProperty(key);
                    result.put(key, value);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}