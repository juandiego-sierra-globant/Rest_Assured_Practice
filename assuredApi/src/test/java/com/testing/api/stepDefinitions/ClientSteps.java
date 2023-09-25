package com.testing.api.stepDefinitions;

import com.testing.api.models.Client;
import com.testing.api.models.Resources;
import com.testing.api.requests.ClientRequest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class ClientSteps {
    private static final Logger logger = LogManager.getLogger(ClientSteps.class);

    private final ClientRequest clientRequest = new ClientRequest();

    private Response response;
    private Client   client;


    @Given("there are registered clients in the system")
    public void thereAreRegisteredClientsInTheSystem() {
        logger.info("there are registered clients in the system");
        response = clientRequest.getClients();
        List<Client> ClientList = clientRequest.getClientsEntity(response);
        if(ClientList.isEmpty()){
            response = clientRequest.createDefaultClient();
            Assert.assertEquals(201,response.getStatusCode());
        }

    }

    @Given("there are registered resources in the system")
    public void thereAreRegisteredResourcesInTheSystem() {
        logger.info("there are registered resources in the system");
        response = clientRequest.getResources();
        List<Resources> ResourcesList = clientRequest.getResourcesEntity(response);
        if (response.getStatusCode() == 200) {
        } else {
            response = clientRequest.createDefaultResource();
            Assert.assertEquals(201, response.getStatusCode());
        }
    }

    @Given("I have a client with the following details:")
    public void iHaveAClientWithTheFollowingDetails(DataTable clientData) {
        logger.info("I have a client with the following details:" + clientData);
        List<Map<String, String>> data = clientData.asMaps(String.class, String.class);
        String name = data.get(0).get("Name");
        String lastName = data.get(0).get("LastName");
        String country = data.get(0).get("Country");
        String city = data.get(0).get("City");
        String email = data.get(0).get("Email");
        String phone = data.get(0).get("Phone");
        Client client = new Client();
        client.setName(name);
        client.setLastName(lastName);
        client.setCountry(country);
        client.setCity(city);
        client.setEmail(email);
        client.setPhone(phone);
        this.client = client;
    }


    @When("I retrieve the details of the latest resource")
    public void sendGETRequestResource() {
        logger.info("I retrieve the details of the latest resource");

    }

    @When("I send a GET request to view all the clients")
    public void iSendAGETRequestToViewAllTheClient() {
        logger.info("I send a GET request to view all the clients");
        response = clientRequest.getClients();
        logger.info(response.jsonPath().prettify());
    }

    @When("I send a GET request to view all the resources")
    public void iSendAGETRequestToViewAllTheResources() {
        logger.info("I send a GET request to view all the resources");
        response = clientRequest.getResources();
        logger.info(response.jsonPath().prettify());
    }

    @When("I send a POST request to create a client")
    public void iSendAPOSTRequestToCreateAClient() {
        logger.info("I send a POST request to create a client");
        response = clientRequest.createClient(client);
    }


    @When("I send a PUT request to update the latest resource")
    public void iSendAPUTRequestToUpdateTheClientWithID(String requestBody) {
        try {
            List<Resources> ResourcesList = clientRequest.getResourcesEntity(response);
            int lastIndex = ResourcesList.size() - 1;
            Resources lastResource = ResourcesList.get(lastIndex);
            String lastID= lastResource.getId();
            ObjectMapper objectMapper = new ObjectMapper();
            Resources resources = objectMapper.readValue(requestBody, Resources.class);
            logger.info("I send a PUT request to update the latest resource" + clientRequest.getResource(lastID).jsonPath().prettify());
            clientRequest.updateResource(resources,lastID);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Then("the response should have a status code of {int}")
    public void theResponseShouldHaveAStatusCodeOf(int statusCode) {
        logger.info("the response should have a status code of " + statusCode);
        Assert.assertEquals(statusCode,response.getStatusCode());
    }

    @Then("the response should have the following details:")
    public void theResponseShouldHaveTheFollowingDetails(DataTable expectedData) {
        logger.info("the response should have the following details:" + expectedData);
    }

    @Then("the response should include the details of the created client")
    public void theResponseShouldIncludeTheDetailsOfTheCreatedClient() {
        logger.info("the response should include the details of the created client");
        logger.info(response.jsonPath().prettify());
    }

    @Then("validates the response with client JSON schema")
    public void userValidatesResponseWithClientJSONSchema() {
        logger.info("validates the response with client JSON schema");
    }

    @Then("validates the response with client list JSON schema")
    public void userValidatesResponseWithClientListJSONSchema() {
        logger.info("validates the response with client list JSON schema");
        response = clientRequest.getClients();
        String schema = "schemas/clientListSchema.json";
        Assert.assertTrue(clientRequest.validateSchema(response,schema));
    }

    @Then("validates the response with resources list JSON schema")
    public void userValidatesResponseWithResourcesListJSONSchema() {
        logger.info("validates the response with resources list JSON schema");
        response = clientRequest.getResources();
        String schema = "schemas/resourcesListSchema.json";
        Assert.assertTrue(clientRequest.validateSchema(response,schema));
    }
}
