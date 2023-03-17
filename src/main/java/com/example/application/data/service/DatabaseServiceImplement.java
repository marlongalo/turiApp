package com.example.application.data.service;

import java.io.IOException;

import com.example.application.data.entity.ClientModel;
import com.example.application.data.entity.ClientResponse;
import com.example.application.data.entity.PackageModel;
import com.example.application.data.entity.PaqueteResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;


public class DatabaseServiceImplement {
	
	private static DatabaseServiceImplement INSTANCE = null;
	private DatabaseClient client;
	private DatabaseClient models;

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
    
    
    
    //OPERACIONES DE LA VISTA PAQUETES
    public PaqueteResponse listarPaquetes() throws IOException {
    	Call<PaqueteResponse> paquetesCall = client.getDatabaseServicePaquetes().listarPaquetes();
    	
    	Response<PaqueteResponse> response = paquetesCall.execute();
    	if(response.isSuccessful()) {
    		return response.body();
    	} else {
    		return null;
    	}
    }
    
    
    public boolean crearPaquetes(PackageModel nuevo) throws IOException {
    	Call<ResponseBody> call = client.getDatabaseServicePaquetes().crearPaquetes(nuevo);
    	Response<ResponseBody> response = call.execute();
    	return response.isSuccessful();
    }
    
    
    public boolean actualizarPaquetes(PackageModel actualizar) throws IOException {
    	Call<ResponseBody> call = client.getDatabaseServicePaquetes().actualizarPaquetes(actualizar);
    	Response<ResponseBody> response = call.execute();
    	return response.isSuccessful();
    }
    
    
    
    //OPERACIONES DE LA VISTA CLIENTE
    
    public ClientResponse listarClientes() throws IOException {
    	Call<ClientResponse> paquetesCall = client.getDatabaseServiceClient().listarClientes();
    	
    	Response<ClientResponse> response = paquetesCall.execute();
    	if(response.isSuccessful()) {
    		return response.body();
    	} else {
    		return null;
    	}
    }
    
    
    public boolean crearClientes(ClientModel nuevo) throws IOException {
    	Call<ResponseBody> call = client.getDatabaseServiceClient().crearClientes(nuevo);
    	Response<ResponseBody> response = call.execute();
    	return response.isSuccessful();
    }
    
    
    public boolean actualizarClientes(ClientModel actualizar) throws IOException {
    	Call<ResponseBody> call = client.getDatabaseServiceClient().actualizarClientes(actualizar);
    	Response<ResponseBody> response = call.execute();
    	return response.isSuccessful();
    }

}
