package service;

import dao.BrokerDao;

public class BrokerService {

    private BrokerDao dao = new BrokerDao();

    public BrokerDao.Data getBrokerData(String ticker) {
        return dao.getTickerPrice(ticker);

    }
}
