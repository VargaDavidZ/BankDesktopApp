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
import java.util.Collection;
import java.util.List;
import java.util.Objects;


public class RestApi {

    static HttpClient client = HttpClient.newHttpClient();

    /**
     * Authenticates a user by sending their email and password to the login endpoint.
     *
     * @param email the email address of the user attempting to log in
     * @param password the password of the user attempting to log in
     * @return the response body as a String if the login is successful (HTTP status code 201),
     *         or "Login Error" if the login attempt fails
     * @throws IOException if an I/O error occurs when sending or receiving the HTTP request
     * @throws InterruptedException if the operation is interrupted while waiting for the response
     */
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

    /**
     * Retrieves a single user based on the provided user ID and authentication token.
     *
     * @param userId the unique identifier of the user to retrieve
     * @param authToken the authentication token for authorization
     * @return the user object corresponding to the provided user ID
     * @throws IOException if an I/O error occurs while making the HTTP request
     * @throws InterruptedException if the thread executing the request is interrupted
     */
    public User getOneUser(String userId, String authToken) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/user/" + userId))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());


        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        User user = mapper.readValue(response.body().toString(),User.class);




        return user;

    }

    /**
     * Retrieves all bank accounts associated with a specific user.
     *
     * @param user the user whose bank accounts are to be retrieved; must contain a valid user ID and authentication token
     * @return an array of BankAccount objects associated with the specified user
     * @throws IOException if an I/O error occurs while making the HTTP request
     * @throws InterruptedException if the thread executing the request is interrupted
     */
    public BankAccount[] getAllBankAccounts(User user) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts/user/" + user.getId()))
                .header("authorization", "Bearer "+ user.getAuthToken() )
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());


        ObjectMapper mapper = new ObjectMapper();


        return mapper.readValue(response.body().toString(),BankAccount[].class);

    }

    /**
     * Retrieves the list of expenses associated with a specific bank account.
     *
     * @param accountId the unique identifier of the bank account for which expenses are to be fetched
     * @param authToken the authentication token used for*/
    public Expense[] getAccountExpenses(String accountId, String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts/allex/" + accountId))
                .header("authorization", "Bearer "+ authToken )
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        try{
           // System.out.println(Arrays.toString(mapper.readValue(response.body().toString(), Expense[].class)));
            return mapper.readValue(response.body().toString(),Expense[].class);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return new Expense[]{};
        }

    }

    /**
     * Retrieves the list of incomes associated with a specific bank account.
     *
     * @param accountId the unique identifier of the bank account for which incomes are to be fetched
     * @param authToken the authentication token used for authorization
     * @return an array of Income objects associated with the specified bank account;
     *         returns an empty array if an error occurs during the data retrieval process
     * @throws IOException if an I/O error occurs while making the HTTP request
     * @throws InterruptedException if the thread executing the request is interrupted
     */
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

    /**
     * Creates a new user by sending the provided details to the user creation endpoint.
     *
     * @param firstName the first name of the user to be created
     * @param lastName the last name of the user to be created
     * @param email the email address of the user to be created
     * @param password the password for the new user account
     * @return the response body as a String after the user is created
     * @throws IOException if an I/O error occurs during the HTTP request
     * @throws InterruptedException if the thread executing the request is interrupted
     */
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

    /**
     * Creates a new bank account for a user by sending the account details to the account creation endpoint.
     *
     * @param userId the unique identifier of the user owning the account
     * @param currency the currency type for the new account (e.g., "HUF")
     * @param fistName the first name of the account owner
     * @param lastName the last name of the account owner
     * @param authToken the authentication token used for authorization
     * @return the response body as a String after the account is created
     * @throws IOException if an I/O error occurs while making the HTTP request
     * @throws InterruptedException if the operation is interrupted while waiting for the response
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

    /**
     * Creates a new expense by sending the given expense details to the corresponding endpoint.
     *
     * @param total the total amount of the expense
     * @param category the category of the expense (e.g., "Food", "Travel")
     * @param description a brief description of the expense
     * @param userId the unique identifier of the user who owns the expense
     * @param bankAccountId the unique identifier of the bank account associated with the expense
     * @param authToken the authentication token used for authorization
     * @return the response body as a String received from the server after creating the expense
     * @throws IOException if an I/O error occurs while making the HTTP request
     * @throws InterruptedException if the thread executing the request is interrupted
     */
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

    /**
     * Creates a new income record by sending the provided income details to the corresponding endpoint.
     *
     * @param total the total amount of the income
     * @param category the category of the income (e.g., "Salary", "Investment")
     * @param description a brief description of the income
     * @param userId the unique identifier of the user who owns the income
     * @param bankAccountId the unique identifier of the bank account associated with the income
     * @param authToken the authentication token used for authorization
     * @return the response body as a String received from the server after creating the income
     * @throws IOException if an I/O error occurs while making the HTTP request
     * @throws InterruptedException if the thread executing the request is interrupted
     */
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

    /**
     * Retrieves the Euro (EUR) exchange rates data for a specified number of days back from the current date.
     *
     * @param daysBack the number of days back from the current date for which to retrieve the EUR exchange rates data;
     *                 0 represents the current date, 1 represents yesterday, and so on
     * @return an instance of the {@code Eur} class containing the exchange rate data for the specified date
     * @throws IOException if an I/O error occurs during the HTTP request
     * @throws InterruptedException if the thread executing the request is interrupted
     */
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

    /**
     * Retrieves the USD exchange rates data for a specified number of days back from the current date.
     *
     * @param daysBack the number of days back from the current date to retrieve the USD exchange rates data;
     *                 0 represents the current date, 1 represents yesterday, and so on
     * @return an instance of the {@code Usd} class containing the exchange rate data for the specified date
     * @throws IOException if an I/O error occurs during the HTTP request
     * @throws InterruptedException if the operation is interrupted while waiting for the response
     */
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


    /**
     * Updates a user's email address by sending a PATCH request to the designated endpoint.
     *
     * @param accId the unique identifier of the user's account
     * @param email the new email address to associate with the user's account
     * @param authToken the authentication token for authorization
     * @throws IOException if an I/O error occurs while sending or receiving the HTTP request
     * @throws InterruptedException if the operation is interrupted while waiting for the response
     */
    public void connectUser(String accId, String email, String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("http://localhost:3000/accounts/user/email/%s",accId)))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(String.format("{\"email\":\"%s\"}", email)))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());



    }

    /**
     * Removes a card from a user's account by sending a disconnection request to the server.
     *
     * @param accId The account ID from which the card is to be disconnected.
     * @param userId The ID of the user whose card is being removed.
     * @param authToken The authorization token required to authenticate the request.
     * @throws IOException If an I/O error occurs when sending or receiving the request.
     * @throws InterruptedException If the operation is interrupted while waiting for a response.
     */
    public void removeCardFromUser(String accId, String userId, String authToken) throws IOException, InterruptedException {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("http://localhost:3000/accounts/disconnect/%s",accId)))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(String.format("{\"userId\":\"%s\"}", userId)))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    }

    /**
     * Deletes a card associated with a specific user account.
     *
     * @param accId The unique identifier of the user account from which the card is to be deleted.
     * @param authToken The authorization token used to authenticate the request.
     * @throws IOException If an I/O error occurs during the HTTP request.
     * @throws InterruptedException If the HTTP request operation is interrupted.
     */
    public void deleteCardFromUser(String accId, String authToken) throws IOException, InterruptedException {


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("http://localhost:3000/accounts/%s",accId)))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken)
                .method("DELETE", HttpRequest.BodyPublishers.ofString(""))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    }

    /**
     * Transfers a specified amount from one account to another.
     * This method sends an HTTP PATCH request to execute the transfer operation.
     *
     * @param userId The ID of the user initiating the transfer.
     * @param senderId The ID of the sender's account.
     * @param receiverId The ID of the receiver's account.
     * @param amount The amount to be transferred.
     * @param authToken The authentication token for the user performing the transfer.
     * @throws IOException If an I/O error occurs when sending or receiving data.
     * @throws InterruptedException If the operation is interrupted while waiting for a response.
     */
    public void transfer(String userId, String senderId, String receiverId, int amount, String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("http://localhost:3000/accounts/transfer")))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(String.format("{\"userId\":\"%s\",\"accountfrom\":\"%s\",\"accountto\":\"%s\",\"amount\": %d}", userId,senderId,receiverId,amount)))
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

    }

    /**
     * Fetches all users associated with a specific account ID.
     *
     * @param accId The account ID for which the user information is to be retrieved.
     * @param authToken The authorization token used for authenticating the API request.
     * @return An array of User objects that belong to the specified account ID.
     * @throws IOException If an input or output exception occurs during the HTTP request or response handling.
     * @throws InterruptedException If the operation is interrupted while sending the request or receiving the response.
     */
    public User[] getAllUsers(String accId, String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts/onlyUsers/" + accId))
                .header("authorization", "Bearer "+ authToken)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body().toString(),User[].class);


    }

    /**
     * Retrieves a repeatable transaction by its account ID from the server.
     *
     * @param accId the account ID associated with the repeatable transaction
     * @param authToken the authorization token for authenticating the request
     * @return a {@code RepeatableExpense} object representing the repeatable transaction
     * @throws IOException if an I/O error occurs when sending or receiving the request
     * @throws InterruptedException if the thread is interrupted while waiting for the response
     */
    public RepeatableExpense getRepeatableTransactionById(String accId, String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/repeatabletransaction/" + accId))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(),RepeatableExpense.class);
    }

    /**
     * Sends a request to stop a repeatable transaction identified by the given transaction ID.
     *
     * @param repId The unique identifier of the repeatable transaction to be stopped.
     * @param authToken The authorization token used to authenticate the request.
     * @throws IOException If an I/O error occurs when sending or receiving the HTTP request.
     * @throws InterruptedException If the operation is interrupted while waiting for the response.
     */
    public void stopRepeatableTransaction(String repId, String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/repeatabletransaction/stop/" + repId))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken)
                .method("DELETE", HttpRequest.BodyPublishers.ofString(""))
                .build();

        ObjectMapper mapper = new ObjectMapper();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

    }

    /**
     * Deletes a repeatable transaction identified by its unique ID.
     * This method sends a DELETE request to the server to remove the specified repeatable transaction.
     *
     * @param repId The ID of the repeatable transaction to delete.
     * @param authToken The authorization token for authenticating the request.
     * @throws IOException If an I/O error occurs when sending or receiving the request.
     * @throws InterruptedException If the operation is interrupted while waiting for the response.
     */
    public void deleteRepeatableTransactions(String repId,String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/repeatabletransaction/" + repId))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken)
                .method("DELETE", HttpRequest.BodyPublishers.ofString(""))
                .build();

        ObjectMapper mapper = new ObjectMapper();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

    }


    /**
     * Retrieves all repeatable transactions associated with a specific account ID.
     *
     * @param accId The account ID for which repeatable transactions are to be retrieved.
     * @param authToken The authorization token to authenticate the request.
     * @return A collection of repeatable transactions associated with the specified account ID.
     * @throws IOException If an I/O error occurs when sending or receiving the request.
     * @throws InterruptedException If the operation is interrupted while waiting for a response.
     */
    public Collection<? extends RepeatableTransaction> getAllRepeatableByAccId(String accId, String authToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/accounts/allrepeat/" + accId))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer "+ authToken)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        return List.of(mapper.readValue(response.body(), RepeatableTransaction[].class));

    }

    /**
     * Deletes a transaction by its ID and type.
     *
     * @param transactionId the ID of the transaction to delete
     * @param inpType the type of the transaction (e.g., "Expense" or "Income")
     * @param authToken the authorization token for API access
     * @throws IOException if an I/O error occurs during the request
     * @throws InterruptedException if the operation is interrupted
     */
    public void deleteTransaction(String transactionId, String inpType  ,String authToken) throws IOException, InterruptedException {
        HttpRequest request;
        //System.out.printf("%s\n", Expense.class.getSimpleName() + " asdadasdad");
        if(Objects.equals(inpType, Expense.class.getSimpleName()))
        {
            request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/Expense/" + transactionId))
                    .header("Content-Type", "application/json")
                    .header("authorization", "Bearer "+ authToken)
                    .method("DELETE", HttpRequest.BodyPublishers.ofString(""))
                    .build();

        }
        else {
            request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/Income/" + transactionId))
                    .header("Content-Type", "application/json")
                    .header("authorization", "Bearer "+ authToken)
                    .method("DELETE", HttpRequest.BodyPublishers.ofString(""))
                    .build();
        }


        ObjectMapper mapper = new ObjectMapper();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());


    }


    /**
     * Creates a repeatable transaction by sending an HTTP POST request.
     *
     * @param total         The total amount for the transaction.
     * @param category      The category of the transaction.
     * @param description   A description for the transaction.
     * @param userId        The ID of the user associated with the transaction.
     * @param bankAccountId The ID of the bank account linked with the transaction.
     * @param repeatAmount  The number of times the transaction will repeat.
     * @param repeatMetric  The time metric (e.g., daily, weekly, monthly) defining the repetition interval.
     * @param repeatStart   The start date of the repetition period.
     * @param repeatEnd     The end date of the repetition period.
     * @param title         The title or name of the transaction.
     * @param authToken     The authorization token for API access.
     * @throws IOException              If an I/O error occurs when sending or receiving the HTTP request.
     * @throws InterruptedException     If the operation is interrupted during processing.
     */
    public void createRepeatableTransaction(float total, String category, String description, String userId, String bankAccountId, int repeatAmount, String repeatMetric, LocalDate repeatStart, LocalDate repeatEnd, String title ,String authToken) throws IOException, InterruptedException {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/repeatabletransaction"))
                    .header("Content-Type", "application/json")
                    .header("authorization", "Bearer "+ authToken)
                    .method("POST", HttpRequest.BodyPublishers.ofString(String.format("{\"total\": \"%f\",\"category\": \"%s\",\"description\":\"%s\",\"userId\": \"%s\",\"accountId\": \"%s\",\"repeatAmount\": \"%d\",\"repeatMetric\": \"%s\",\"repeatStart\": \"%s\",\"repeatEnd\": \"%s\", \"name\": \"%s\" }",total,category,description,userId,bankAccountId,repeatAmount,repeatMetric,repeatStart,repeatEnd,title)))
                    .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        //System.out.printf(response.body());
    }

    /**
     * Updates the status of a repeatable transaction for a specified account by making
     * an HTTP PATCH request to the designated endpoint.
     *
     * @param accountId The unique identifier of the account associated with the transaction.
     * @param userId The unique identifier of the user initiating the update request.
     * @param authToken The authentication token required for authorization to the API.
     * @throws IOException If an I/O error occurs while sending or receiving the HTTP request.
     * @throws InterruptedException If the operation is interrupted while waiting for the response.
     */
    public void updateRepeatableTransaction(String accountId,String userId, String authToken) throws IOException, InterruptedException { //have to call an endpoint that check the status of the transaction
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:3000/repeatabletransaction/update/" + accountId))
                .header("Content-Type","application/json")
                .header("authorization", "Bearer "+ authToken)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(String.format("{\"userId\": \"%s\"}", userId)))
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.body());
    }


    /**
     * Retrieves Bitcoin (BTC) data from a specified number of days back.
     *
     * @param daysBack the number of days back from the current date to retrieve Bitcoin data for
     * @return a {@code Btc} object containing the retrieved Bitcoin data
     * @throws IOException if an I/O error occurs when sending or receiving the HTTP request
     * @throws InterruptedException if the operation is interrupted
     */
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

    /**
     * Retrieves Ethereum (ETH) currency information for a given number of days back from the current date.
     *
     * @param daysBack the number of days to go back from the current date to fetch the Ethereum data.
     * @return an Eth object containing Ethereum (ETH) currency data as per the specified date.
     * @throws IOException if an I/O error occurs during the HTTP request or response handling.
     * @throws InterruptedException if the HTTP request operation is interrupted.
     */
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
