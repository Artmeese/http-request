package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static CloseableHttpResponse getResponse(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(request);
        return response;
    }

    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpResponse response1 = getResponse("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");

        List<Cat> cats = mapper.readValue(response1.getEntity().getContent(), new TypeReference<List<Cat>>(){});

        List<Cat> nullUpvotesCat = cats.stream().
                filter(value -> value.getUpvotes() != null && Integer.valueOf(value.getUpvotes()) > 0).
                collect(Collectors.toList());

        for (Cat kitty : nullUpvotesCat) {
            System.out.println(kitty);
        }

    }
}