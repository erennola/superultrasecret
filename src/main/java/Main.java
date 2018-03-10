import com.google.gson.Gson;
import service.BrokerService;
import spark.ResponseTransformer;

import static spark.Spark.get;

public class Main {

    static BrokerService service = new BrokerService();

    public static void main(String[] args) {
        get("/ticker/:ticker", (req, res) -> {
            res.type("application/json");
            return service.getBrokerData(req.params("ticker"));
        }, json());
    }


    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json() {
        return Main::toJson;
    }
}