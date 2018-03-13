package service;

import dao.BrokerDao;
import dao.DataDto;
import model.Data;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class BrokerService {

    private final Map<String, String> tickerNames;
    private final Map<String, Integer> stockQuantity;

    public BrokerService() {
        tickerNames = readPropertiesFile("nombres.properties");
        stockQuantity = readPropertiesFile("stock-inicial.properties")
                        .entrySet().stream()
                        .collect(Collectors.toMap(e -> e.getKey(), e -> Integer.valueOf(e.getValue())));
    }


    private BrokerDao dao = new BrokerDao();

    public Data getBrokerData(String ticker) {
        DataDto dataDto = dao.getTickerPrice(ticker);
        String name = tickerNames.get(ticker);
        return new Data(name, dataDto.getTicker(), dataDto.getValue());
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