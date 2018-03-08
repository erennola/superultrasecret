package service;

import dao.BrokerDao;
import dao.DataDto;
import model.Data;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BrokerService {

    private static Map<String, String> tickers = new HashMap<>();

    static {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = BrokerService.class.getClassLoader().getResourceAsStream("config.properties");

            if (input != null) {
                prop.load(input);

                Enumeration<?> e = prop.propertyNames();
                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();
                    String value = prop.getProperty(key);
                    tickers.put(key, value);
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
    }

    private BrokerDao dao = new BrokerDao();

    public Data getBrokerData(String ticker) {
        DataDto dataDto = dao.getTickerPrice(ticker);
        String name = tickers.get(ticker);
        return new Data(name, dataDto.getTicker(), dataDto.getValue());
    }

}