package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
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


    public static String Login(String email, String password) throws IOException, InterruptedException {

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:3000/user/login"))
                        .header("Content-Type", "application/json")
                        .method("POST", HttpRequest.BodyPublishers.ofString(String.format("{\"email\": \"%s\",\"password\": \"%s\"}",email,password)))
                        .build();

                HttpResponse<String> response1 = client.send(request, HttpResponse.BodyHandlers.ofString());


                if(response1.statusCode() == 201) {
                    return response1.body();
                }
                else
                {
                    return "Login Error";
                }

    }

    public User GetOneUser(String userId) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/user/" + userId))
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());


        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        User user = mapper.readValue(response.body().toString(),User.class);


        client.close();

        return user;

    }

    public BankAccount[] GetAllBankAccounts(String userId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts/all/" + userId))
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());


        ObjectMapper mapper = new ObjectMapper();

        BankAccount[] bankaccounts = mapper.readValue(response.body().toString(),BankAccount[].class);


        return bankaccounts;

    }

    public Expense[] GetAccountExpenses(String accountId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts/allex/" + accountId))
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Expense[] exp = mapper.readValue(response.body().toString(),Expense[].class);

        return exp;

    }

    public Income[] GetAccountIncomes(String accountId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts/allin/" + accountId))
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Income[] inc = mapper.readValue(response.body().toString(),Income[].class);

        return inc;

    }

    public static String CreateUser(String firstName, String lastName, String email, String password) throws IOException, InterruptedException {

        //"{\"Fristname\":\"s%\",\"Lastname\":\"s%\",\"email\": %s,\"password\": %s }",firstName,lastName,email,password
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/user"))
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString(String.format("{\"firstName\": \"%s\",\"lastName\": \"%s\",\"email\": \"%s\",\"password\": \"%s\"}",firstName,lastName,email,password)))
                    .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        User user = mapper.readValue(response.body().toString(),User.class);

       // CreateAccount(user.getId(),"HUF");

        return   response.body();

    }

    public static String CreateAccount(String userId, String currency) throws IOException, InterruptedException {

        HttpRequest createAccountRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts"))
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(String.format("{\"total\": \"%s\",\"userId\": \"%s\",\"currency\": \"HUF\"}",2000,userId)))
                .build();

        HttpResponse<String> response = client.send(createAccountRequest, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public static String CreateExpense(int total, String category,String vendor,String description, String userId, String bankAccountId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/expense"))
                .header("Content-Type", "application/json")
                .method("POST",HttpRequest.BodyPublishers.ofString(String.format("{\"total\": %d,\"Category\": \"%s\",\"vendor\": \"%s\",\"Description\":\"%s\",\"userId\": \"%s\",\"bankAccountId\": \"%s\"}", total,category,vendor,description,userId,bankAccountId)))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return   response.body();

    }

    public static String CreateIncome(int total, String category,String vendor,String description, String userId, String bankAccountId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/Income"))
                .header("Content-Type", "application/json")
                .method("POST",HttpRequest.BodyPublishers.ofString(String.format("{\"total\": %d,\"Category\": \"%s\",\"vendor\": \"%s\",\"description\":\"%s\",\"userid\": \"%s\",\"bankAccountId\": \"%s\"}", total,category,vendor,description,userId,bankAccountId)))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return   response.body();

    }

}
