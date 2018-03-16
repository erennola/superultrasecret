package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import exceptions.BrokerException;

import java.io.IOException;

public class BrokerDao {

    private static final ObjectMapper mapper = new ObjectMapper();

    public DataDto getTickerPrice(String ticker) {
        String url = "http://localhost:8080/stocks/" + ticker;
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        try {
            HttpResponse response = client.execute(request);
            return mapper.readValue(response.getEntity().getContent(), DataDto.class);
        } catch (IOException e) {
            throw new BrokerException("Stocks server can't be reached");
        }

    }

}