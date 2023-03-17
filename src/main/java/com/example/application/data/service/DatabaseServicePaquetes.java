package com.example.application.data.service;
import com.example.application.data.entity.ClientModel;
import com.example.application.data.entity.PackageModel;
import com.example.application.data.entity.PaqueteResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface DatabaseServicePaquetes {
	
	@Headers({
	    "Accept: application/json",
	    "User-Agent: Retrofit-Sample-App"
	})
	@GET("turiApp/paquetes")
	Call<PaqueteResponse> listarPaquetes();
	


	@Headers({
	    "Accept: application/json",
	    "User-Agent: Retrofit-Sample-App"
	})
	@POST("turiApp/paquetes")
	Call<ResponseBody> crearPaquetes(@Body PackageModel nuevo);
	
	
	@Headers({
	    "Accept: application/json",
	    "User-Agent: Retrofit-Sample-App"
	})
	@PUT("turiApp/paquetes")
	Call<ResponseBody> actualizarPaquetes(@Body PackageModel actualizar);

}
