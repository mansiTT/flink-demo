package com.batch.filetoes.es.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalesReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1569813599030629052L;

	private String region;

	private String country;

	private String itemType;

	private String salesChannel;

	private String orderPriority;

	private String orderDate;

	private String shipDate;

	private Integer unitSold;

	private Double unitPrice;

	private Double unitCost;

	private Double totalRevenue;

	private Double cost;

	private Double profit;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getSalesChannel() {
		return salesChannel;
	}

	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}

	public String getOrderPriority() {
		return orderPriority;
	}

	public void setOrderPriority(String orderPriority) {
		this.orderPriority = orderPriority;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getShipDate() {
		return shipDate;
	}

	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}

	public Integer getUnitSold() {
		return unitSold;
	}

	public void setUnitSold(Integer unitSold) {
		this.unitSold = unitSold;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Double getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(Double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	@Override
	public String toString() {
		return "SalesReport [region=" + region + ", country=" + country + ", itemType=" + itemType + ", salesChannel="
				+ salesChannel + ", orderPriority=" + orderPriority + ", orderDate=" + orderDate + ", shipDate="
				+ shipDate + ", unitSold=" + unitSold + ", unitPrice=" + unitPrice + ", unitCost=" + unitCost
				+ ", totalRevenue=" + totalRevenue + ", cost=" + cost + ", profit=" + profit + "]";
	}

}
