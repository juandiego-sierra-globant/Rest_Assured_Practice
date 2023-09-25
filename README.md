# Rest_Assured_Practice

## Overview

This project focuses on testing the CRUD (Create, Read, Update, Delete) operations for clients and resources using REST Assured. It includes scenarios to verify the functionality of the API endpoints.

## Features

### Get the List of Clients

- **Scenario:** Get the List of Clients
  - Given there are registered clients in the system
  - When I send a GET request to view all the clients
  - Then the response should have a status code of 200
  - And validates the response with client list JSON schema

### Get the List of Resources

- **Scenario:** Get the List of Resources
  - Given there are registered resources in the system
  - When I send a GET request to view all the resources
  - Then the response should have a status code of 200
  - And validates the response with resources list JSON schema

### Create a New Client

- **Scenario:** Create a new client
  - Given I have a client with the following details:
    | Name   | LastName  | Country | City      | Email                 | Phone               |
    | ------ | --------- | ------- | --------- | --------------------- | ------------------- |
    | Taylor | Swift     | USA     | New York  | taylor-swift@gmail.com | 1-425-705-0487 x77466 |
  - When I send a POST request to create a client
  - Then the response should have a status code of 201
  - And the response should include the details of the created client
  - And validates the response with client JSON schema

### Update Last Details

- **Scenario:** Update last details
  - Given there are registered resources in the system
  - And I retrieve the details of the latest resource
  - When I send a PUT request to update the latest resource
  - Then the response should have a status code of 200
  - And the response should have the following details:
    | Name        | Trademark      | Stock | Price   | Description | Tags        | Is Active |
    | ----------- | ------------- | ----- | ------- | ----------- | ----------- | --------- |
    | Otro nombre | Otro trademark | 7000  | 3223.07 | Descripcion | otro status | false     |
  - And validates the response with resources list JSON schema

## Usage

- Clone the repository to your local machine.
- Configure the necessary dependencies and environment.
- Run the tests using REST Assured.