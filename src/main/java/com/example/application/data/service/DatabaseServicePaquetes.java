package com.example.application.data.service;
import com.example.application.data.entity.PaqueteResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface DatabaseServicePaquetes {
	
	@Headers({
	    "Accept: application/json",
	    "User-Agent: Retrofit-Sample-App"
	})

	
	@GET("turiApp/paquetes")
	Call<PaqueteResponse> listarPaquetes();
	
	

}
