import dao.BrokerDao;
import service.BrokerService;

import static spark.Spark.get;

public class Main {

    static BrokerService service = new BrokerService();

    public static void main(String[] args) {
        get("/ticker/:ticker", (req, res) -> {
            BrokerDao.Data data = service.getBrokerData(req.params("ticker"));

            return data;
        });
    }

}