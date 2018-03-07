package service;

import dao.BrokerDao;
import model.Data;

public class BrokerService {

    private BrokerDao dao = new BrokerDao();

    public Data getBrokerData(String ticker) {
        Data data = dao.getTickerPrice(ticker);
        data.name = ticker;
        return data;
    }
}
