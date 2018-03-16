import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import service.BrokerService;
import exceptions.BrokerException;
import spark.ResponseTransformer;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.exception;

public class Main {

    private static final BrokerService service = new BrokerService();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static class Properties extends HashMap<String, Integer> { }

    public static void main(String[] args) {
        get("/ticker/:ticker", (req, res) -> {
            res.type("application/json");
            return service.getBrokerData(req.params("ticker"));
        }, json());

        post("ticker", (req, res) -> {
            Map<String, Integer> transactionRequest = mapper.readValue(req.body(), Properties.class);
            res.type("application/json");
            res.status(200);
            res.body("Successful Transaction");
            return service.buyStock(transactionRequest);
        }, json());
        exception(BrokerException.class, (exception, request, response) -> {
            response.type("application/json");
            response.status(500);
            response.body(exception.getMessage());
        });
    }


    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer json() {
        return Main::toJson;
    }
}