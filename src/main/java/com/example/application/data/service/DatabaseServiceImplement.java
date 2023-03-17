package com.example.application.data.service;

import java.io.IOException;

import com.example.application.data.entity.ClientResponse;
import com.example.application.data.entity.PaqueteResponse;
import com.example.application.data.entity.PaymentModel;
import com.example.application.data.entity.ReservaModel;
import com.example.application.data.entity.ReservasResponse;

import okhttp3.ResponseBody;
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
    
    public boolean crearReserva( ReservaModel nuevaReserva ) throws IOException {
    	Call<ResponseBody> reservaCall = client.getDatabaseServiceReserva().crearReserva(nuevaReserva);
    	Response<ResponseBody> response = reservaCall.execute();
    	
    	return response.isSuccessful();
    	
    }
    
    public boolean actualizarReserva( ReservaModel nuevaReserva ) throws IOException {
    	Call<ResponseBody> reservaCall = client.getDatabaseServiceReserva().actualizarReserva(nuevaReserva);
    	Response<ResponseBody> response = reservaCall.execute();
    	
    	return response.isSuccessful();
    	
    }
    
    
    public ReservasResponse listarReserva() throws IOException {
    	Call<ReservasResponse> reservaCall = client.getDatabaseServiceReserva().listarReservasPendientes();
    	Response<ReservasResponse> response = reservaCall.execute();
    	
    	if(response.isSuccessful()) {
    		return response.body();
    	} else {
    		return null;
    	}
    	
    }
    
    public boolean crearPayment( PaymentModel nuevaPayment ) throws IOException {
    	Call<ResponseBody> paymentCall = client.getDatabaseServicePayment().crearPayment(nuevaPayment);
    	Response<ResponseBody> response = paymentCall.execute();
    	
    	return response.isSuccessful();
    	
    }
    

}
