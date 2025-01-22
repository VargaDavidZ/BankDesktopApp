package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;

public class RestApi {

    static HttpClient client = HttpClient.newHttpClient();


    public static int Login(String email, String password) throws IOException, InterruptedException {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/user/login"))
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString(String.format("{\"email\": \"%s\",\"password\": \"%s\"}",email,password)))
                    .build();

            //client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            HttpResponse<String> response1 = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.printf("Login response: %s\n", response1.statusCode());
            return response1.statusCode();

    }

    public User GetOneUser(String userId) throws IOException, InterruptedException {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/user/" + userId))
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //System.out.printf(response.body().toString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        User user = mapper.readValue(response.body().toString(),User.class);

        System.out.printf(user.toString());
        client.close();

        return user;

    }


    public String CreateUser(String firstName,String lastName,String email,String password) throws IOException, InterruptedException {

        //"{\"Fristname\":\"s%\",\"Lastname\":\"s%\",\"email\": %s,\"password\": %s }",firstName,lastName,email,password
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/user"))
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString(String.format("{\"firstname\": \"%s\",\"lastname\": \"%s\",\"email\": \"%s\",\"password\": \"%s\"}",firstName,lastName,email,password)))
                    .build();

            //client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        HttpResponse<String> response1 = client.send(request, HttpResponse.BodyHandlers.ofString());

        return   response1.body();



    }


}
