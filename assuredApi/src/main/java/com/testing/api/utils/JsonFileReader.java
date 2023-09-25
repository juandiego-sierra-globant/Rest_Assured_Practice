package com.testing.api.utils;

import com.google.gson.Gson;
import com.testing.api.models.Client;
import com.testing.api.models.Resources;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JsonFileReader {
    /**
     * This method read a JSON file and deserialize the body into a Client object
     *
     * @param jsonFileName json file location path
     *
     * @return Client : client
     */
    public Client getClientByJson(String jsonFileName) {
        Client client = new Client();
        try (Reader reader = new FileReader(jsonFileName)) {
            Gson gson = new Gson();
            client = gson.fromJson(reader, Client.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return client;
    }

    public Resources getResourcesByJson(String jsonFileName) {
        Resources resources = new Resources();
        try (Reader reader = new FileReader(jsonFileName)) {
            Gson gson = new Gson();
            resources = gson.fromJson(reader, Resources.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }
}
