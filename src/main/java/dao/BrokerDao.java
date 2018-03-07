package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class BrokerDao {

    private static final ObjectMapper mapper = new ObjectMapper();

    public Data getTickerPrice(String ticker) {
        String url = "http://www.google.com/search?q=httpClient";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        try {
            HttpResponse response = client.execute(request);
            return mapper.readValue(response.getEntity().getContent(), Data.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static class Data {
        public String nombre;
        public String ticket;
        public String cotizacion;
    }

}