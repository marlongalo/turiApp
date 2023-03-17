package com.example.application.data.entity;


public class ReservaModel {
	
	private Integer reservaid;
	private Integer packageid;
	private Integer clienteid;
    private String fechainicio;
    private String fechafin;
    private Integer totalprice;
    private Integer payed;
    
    
	public Integer getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(Integer totalprice) {
		this.totalprice = totalprice;
	}
	public Integer getPayed() {
		return payed;
	}
	public void setPayed(Integer payed) {
		this.payed = payed;
	}
	public Integer getReservaid() {
		return reservaid;
	}
	public void setReservaid(Integer reservaid) {
		this.reservaid = reservaid;
	}
	public Integer getPackageid() {
		return packageid;
	}
	public void setPackageid(Integer packageid) {
		this.packageid = packageid;
	}
	public Integer getClienteid() {
		return clienteid;
	}
	public void setClienteid(Integer clienteid) {
		this.clienteid = clienteid;
	}
	public String getFechainicio() {
		return fechainicio;
	}
	public void setFechainicio(String fechainicio) {
		this.fechainicio = fechainicio;
	}
	public String getFechafin() {
		return fechafin;
	}
	public void setFechafin(String fechafin) {
		this.fechafin = fechafin;
	}
	public Integer getPrice() {
		return totalprice;
	}
	public void setPrice(Integer price) {
		this.totalprice = price;
	}
    

}
