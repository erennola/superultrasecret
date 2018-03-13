package service;

import dao.BrokerDao;
import dao.DataDto;
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
        return new Data(name, ticker, getStockPrice(ticker));
    }

    public void buyStock(Map<String, Integer> transactionRequest) {
        Map<String, Integer> stockPrices = getStockPrices(transactionRequest.keySet());
        Map<String, Integer> quantityToBuy = stockPrices.entrySet().stream()
                                             .collect(Collectors.toMap(Map.Entry::getKey,
                                                                       e -> transactionRequest.get(e.getKey()) / e.getValue()));

        for (Map.Entry<String, Integer> e : quantityToBuy.entrySet()) {
            if (stockQuantity.get(e.getKey()) < e.getValue()) {
                throw new RuntimeException("There is no enough stock for " + e.getKey());
            }
        }

        for (Map.Entry<String, Integer> e : quantityToBuy.entrySet()) {
            stockQuantity.put(e.getKey(), stockQuantity.get(e.getKey()) - e.getValue());
        }
    }

    private Map<String, Integer> getStockPrices(Set<String> tickers) {
        return tickers.parallelStream()
               .collect(Collectors.toMap(e -> e, e -> getStockPrice(e)));
    }

    private Integer getStockPrice(String ticker) {
        return Integer.valueOf(dao.getTickerPrice(ticker).getValue());
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