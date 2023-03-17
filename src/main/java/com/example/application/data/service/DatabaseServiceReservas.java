package com.example.application.data.service;

import com.example.application.data.entity.ReservaModel;
import com.example.application.data.entity.ReservasResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.GET;

public interface DatabaseServiceReservas {
	
	@Headers({
	    "Accept: application/json",
	    "User-Agent: Retrofit-Sample-App"
	})
	
	@GET("turiApp/reservas")
	Call<ReservasResponse> listarReservasPendientes();
	
	@Headers({
	    "Accept: application/json",
	    "User-Agent: Retrofit-Sample-App"
	})
	@POST("turiApp/reservas")
	Call<ResponseBody> crearReserva(@Body ReservaModel nuevaReserva);
	
	@Headers({
	    "Accept: application/json",
	    "User-Agent: Retrofit-Sample-App"
	})
	@PUT("turiApp/reservas")
	Call<ResponseBody> actualizarReserva(@Body ReservaModel nuevaReserva);
	

	
	
}
