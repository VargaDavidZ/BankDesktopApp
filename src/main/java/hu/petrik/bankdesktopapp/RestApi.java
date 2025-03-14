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
import java.util.Arrays;
import java.util.Date;


public class RestApi {

    static HttpClient client = HttpClient.newHttpClient();


    public String login(String email, String password) throws IOException, InterruptedException {

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:3000/user/login"))
                        .header("Content-Type", "application/json")
                        .method("POST", HttpRequest.BodyPublishers.ofString(String.format("{\"email\": \"%s\",\"password\": \"%s\"}",email,password)))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


                if(response.statusCode() == 201) {
                    return response.body();
                }
                else
                {
                    return "Login Error";
                }

    }

    public User getOneUser(String userId) throws IOException, InterruptedException {

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

    public BankAccount[] getAllBankAccounts(User user) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts/user/" + user.getId()))
                .header("authorization", "Bearer "+ user.getAuthToken() )
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());


        ObjectMapper mapper = new ObjectMapper();


        return mapper.readValue(response.body().toString(),BankAccount[].class);

    }

    public Expense[] getAccountExpenses(String accountId, String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts/allex/" + accountId))
                .header("authorization", "Bearer "+ authToken )
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        try{
            System.out.println(Arrays.toString(mapper.readValue(response.body().toString(), Expense[].class)));
            return mapper.readValue(response.body().toString(),Expense[].class);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return new Expense[]{};
        }

    }

    public Income[] getAccountIncomes(String accountId, String authToken) throws IOException, InterruptedException {
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

    public String createUser(String firstName, String lastName, String email, String password) throws IOException, InterruptedException {

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

    /*
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

     */

    public String createAccount(String userId, String currency, String fistName, String lastName, String authToken) throws IOException, InterruptedException {

        HttpRequest createAccountRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts"))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken )
                .method("POST", HttpRequest.BodyPublishers.ofString(String.format("{\"total\": \"%s\",\"userId\": \"%s\",\"currency\": \"HUF\", \"ownerName\": \"%s %s\"}",2000,userId, fistName,lastName)))
                .build();

        HttpResponse<String> response = client.send(createAccountRequest, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String createExpense(int total, String category, String description, String userId, String bankAccountId, String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/expense"))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken)
                .method("POST",HttpRequest.BodyPublishers.ofString(String.format("{\"total\": %d,\"category\": \"%s\",\"description\":\"%s\",\"userId\": \"%s\",\"bankAccountId\": \"%s\"}", total,category,description,userId,bankAccountId)))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return  response.body();

    }

    public String createIncome(int total, String category, String description, String userId, String bankAccountId, String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/Income"))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken)
                .method("POST",HttpRequest.BodyPublishers.ofString(String.format("{\"total\": %d,\"category\": \"%s\",\"description\":\"%s\",\"userId\": \"%s\",\"bankAccountId\": \"%s\"}", total,category,description,userId,bankAccountId)))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return   response.body();

    }

    //prop to set how far back i want to go
    //rename to GetPastEur(x:dayBack)
    //return the Eur price in HUF, -daysBack indicates how far in the past we want to go back -> 0 present ->1 yesterday..
    public Eur getEur(int daysBack) throws IOException, InterruptedException {
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
    public Usd getUsd(int daysBack) throws IOException, InterruptedException {

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



    public void connectUser(String accId, String email, String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("http://localhost:3000/accounts/user/email/%s",accId)))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(String.format("{\"email\":\"%s\"}", email)))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.printf(response.body());

    }

    public void removeCardFromUser(String accId, String userId, String authToken) throws IOException, InterruptedException {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("http://localhost:3000/accounts/disconnect/%s",accId)))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(String.format("{\"userId\":\"%s\"}", userId)))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.printf(response.body());
    }

    public void deleteCardFromUser(String accId, String authToken) throws IOException, InterruptedException {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("http://localhost:3000/accounts/%s",accId)))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken)
                .method("DELETE", HttpRequest.BodyPublishers.ofString(""))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.printf(response.body());
    }

    public void transfer(String userId, String senderId, String receiverId, int amount, String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("http://localhost:3000/accounts/transfer")))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(String.format("{\"userId\":\"%s\",\"accountfrom\":\"%s\",\"accountto\":\"%s\",\"amount\": %d}", userId,senderId,receiverId,amount)))
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());


        System.out.printf(response.body());

    }

    public User[] getAllUsers(String accId, String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts/onlyUsers/" + accId))
                .header("authorization", "Bearer "+ authToken)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.printf("%s\n", response.body());

        ObjectMapper mapper = new ObjectMapper();

        System.out.println("---------------------");
        System.out.println(response.body());
        return mapper.readValue(response.body().toString(),User[].class);


    }


    public void createRepeatableTransaction(float total, String category, String description, String userId, String bankAccountId, int repeatAmount, String repeatMetric, LocalDate repeatStart, LocalDate repeatEnd ,String authToken) throws IOException, InterruptedException {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/repeatabletransaction"))
                    .header("Content-Type", "application/json")
                    .header("authorization", "Bearer "+ authToken)
                    .method("POST", HttpRequest.BodyPublishers.ofString(String.format("{\"total\": \"%f\",\"category\": \"%s\",\"description\":\"%s\",\"userId\": \"%s\",\"accountId\": \"%s\",\"repeatAmount\": \"%d\",\"repeatMetric\": \"%s\",\"repeatStart\": \"%s\",\"repeatEnd\": \"%s\" }",total,category,description,userId,bankAccountId,repeatAmount,repeatMetric,repeatStart,repeatEnd)))
                    .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        System.out.printf(response.body());
    }

    public void updateRepeatableTransaction(){ //have to call an endpoint that check the status of the transaction

    }




    public Btc getBtc(int daysBack) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://%s.currency-api.pages.dev/v1/currencies/btc.json",LocalDate.now().minusDays(daysBack)) ))
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Btc btc = mapper.readValue(response.body().toString(),Btc.class);

        //  System.out.printf(response.body().toString());
        return btc;

    }

    public Eth getEth(int daysBack) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://%s.currency-api.pages.dev/v1/currencies/eth.json",LocalDate.now().minusDays(daysBack)) ))
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        Eth eth = mapper.readValue(response.body().toString(),Eth.class);

        //  System.out.printf(response.body().toString());
        return eth;

    }






}
