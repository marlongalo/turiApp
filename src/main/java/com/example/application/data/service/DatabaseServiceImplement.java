package com.example.application.data.service;

import java.io.IOException;

import com.example.application.data.entity.ClientResponse;
import com.example.application.data.entity.PaqueteResponse;

import retrofit2.Call;
import retrofit2.Response;


public class DatabaseServiceImplement {
	
	private static DatabaseServiceImplement INSTANCE = null;
	private DatabaseClient client;

    private DatabaseServiceImplement(){
    	client = new DatabaseClient();
    }

    public static DatabaseServiceImplement getInstance() {
        if (INSTANCE == null) {
        	synchronized (DatabaseServiceImplement.class) {
        		if (INSTANCE == null) { 
                    INSTANCE = new DatabaseServiceImplement();
                }
			}
        }
        return INSTANCE;
    }
    
    
    public PaqueteResponse listarPaquetes() throws IOException {
    	Call<PaqueteResponse> paquetesCall = client.getDatabaseServicePaquetes().listarPaquetes();
    	
    	Response<PaqueteResponse> response = paquetesCall.execute();
    	if(response.isSuccessful()) {
    		return response.body();
    	} else {
    		return null;
    	}
    }
    
    
    public ClientResponse listarClientes() throws IOException {
    	Call<ClientResponse> paquetesCall = client.getDatabaseServiceClient().listarClientes();
    	
    	Response<ClientResponse> response = paquetesCall.execute();
    	if(response.isSuccessful()) {
    		return response.body();
    	} else {
    		return null;
    	}
    }
    

}
