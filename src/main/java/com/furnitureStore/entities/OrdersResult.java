package com.furnitureStore.entities;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrdersResult {
	private Integer tid;
	
	private LocalDate purchasedOn;
	private PaymentInfo paymentInfo;
	private boolean delivered;
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public LocalDate getPurchasedOn() {
		return purchasedOn;
	}
	public void setPurchasedOn(LocalDate purchasedOn) {
		this.purchasedOn = purchasedOn;
	}
	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	public boolean isDelivered() {
		return delivered;
	}
	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}
}