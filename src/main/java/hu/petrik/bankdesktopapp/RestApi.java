package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;


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

    public BankAccount[] GetAllBankAccounts(User user) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts/all/" + user.getId()))
                .header("authorization", "Bearer "+ user.getAuthToken() )
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());


        ObjectMapper mapper = new ObjectMapper();

        BankAccount[] bankaccounts = mapper.readValue(response.body().toString(),BankAccount[].class);


        return bankaccounts;

    }



    public Expense[] GetAccountExpenses(String accountId, String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts/allex/" + accountId))
                .header("authorization", "Bearer "+ authToken )
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        try{

            return mapper.readValue(response.body().toString(),Expense[].class);
        }
        catch(Exception e){
            return new Expense[]{};
        }

    }

    public Income[] GetAccountIncomes(String accountId, String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts/allin/" + accountId))
                .header("authorization", "Bearer "+ authToken )
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        try{

            return mapper.readValue(response.body().toString(),Income[].class);
        }
        catch(Exception e){
            return new Income[]{};
        }



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

    public static String UpdateTotal(String accId, Float total, String authToken) throws IOException, InterruptedException {

        //"{\"Fristname\":\"s%\",\"Lastname\":\"s%\",\"email\": %s,\"password\": %s }",firstName,lastName,email,password
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts/" + accId))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken )
                .method("PATCH", HttpRequest.BodyPublishers.ofString(String.format("{\"total\": \"%.2f\"}",total)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);


        // CreateAccount(user.getId(),"HUF");

        return   response.body();

    }

    public static String CreateAccount(String userId, String currency, String fistName, String lastName, String authToken) throws IOException, InterruptedException {

        HttpRequest createAccountRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts"))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken )
                .method("POST", HttpRequest.BodyPublishers.ofString(String.format("{\"total\": \"%s\",\"userId\": \"%s\",\"currency\": \"HUF\", \"ownerName\": \"%s %s\"}",2000,userId, fistName,lastName)))
                .build();

        HttpResponse<String> response = client.send(createAccountRequest, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public static String CreateExpense(int total, String category,String vendor,String description, String userId, String bankAccountId, String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/expense"))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken)
                .method("POST",HttpRequest.BodyPublishers.ofString(String.format("{\"total\": %d,\"category\": \"%s\",\"vendor\": \"%s\",\"description\":\"%s\",\"userId\": \"%s\",\"bankAccountId\": \"%s\"}", total,category,vendor,description,userId,bankAccountId)))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return   response.body();

    }

    public static String CreateIncome(int total, String category,String vendor,String description, String userId, String bankAccountId, String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/Income"))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken)
                .method("POST",HttpRequest.BodyPublishers.ofString(String.format("{\"total\": %d,\"category\": \"%s\",\"vendor\": \"%s\",\"description\":\"%s\",\"userId\": \"%s\",\"bankAccountId\": \"%s\"}", total,category,vendor,description,userId,bankAccountId)))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return   response.body();

    }


    /*public Eur GetEur() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://latest.currency-api.pages.dev/v1/currencies/eur.json"))
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Eur eur = mapper.readValue(response.body().toString(),Eur.class);

      //  System.out.printf(response.body().toString());

        return eur;

    }

     */
    //prop to set how far back i want to go
    //rename to GetPastEur(x:dayBack)
    //return the Eur price in HUF, -daysBack indicates how far in the past we want to go back -> 0 present ->1 yesterday..
    public Eur GetEur(int daysBack) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://%s.currency-api.pages.dev/v1/currencies/eur.json",LocalDate.now().minusDays(daysBack)) ))
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Eur eur = mapper.readValue(response.body().toString(),Eur.class);

        //  System.out.printf(response.body().toString());
        return eur;

    }


    //return the Usd price in HUF, -daysBack indicates how far in the past we want to go back -> 0 present ->1 yesterday..
    public Usd GetUsd(int daysBack) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://%s.currency-api.pages.dev/v1/currencies/usd.json",LocalDate.now().minusDays(daysBack)) ))
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Usd usd = mapper.readValue(response.body().toString(),Usd.class);

        //  System.out.printf(response.body().toString());
        //System.out.println(String.valueOf(usd.getValue().get("huf")));
        return usd;

    }



}
