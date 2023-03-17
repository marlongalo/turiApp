package com.example.application.data.entity;

public class PaymentModel {
	
	private Integer paymentid;
    private Integer reservaid;
    private String card;
    private String datecard;
    private String secretnumber;
    private String datepayment;
    private Integer price;
    
    
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getPaymentid() {
		return paymentid;
	}
	public void setPaymentid(Integer paymentid) {
		this.paymentid = paymentid;
	}
	public Integer getReservaid() {
		return reservaid;
	}
	public void setReservaid(Integer reservaid) {
		this.reservaid = reservaid;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getDatecard() {
		return datecard;
	}
	public void setDatecard(String datecard) {
		this.datecard = datecard;
	}
	public String getSecretnumber() {
		return secretnumber;
	}
	public void setSecretnumber(String secretnumber) {
		this.secretnumber = secretnumber;
	}
	public String getDatepayment() {
		return datepayment;
	}
	public void setDatepayment(String datepayment) {
		this.datepayment = datepayment;
	}

}
