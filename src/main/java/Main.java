import service.BrokerService;

import static spark.Spark.get;

public class Main {

    static BrokerService service = new BrokerService();

    public static void main(String[] args) {
        get("/ticker/:ticker", (req, res) -> service.getBrokerData(req.params("ticker")));
    }

}