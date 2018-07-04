package com.jl.hangye;

public class HangYeClass {
	private  String ticker;
	private  String secShortName;
	private  String negMarketValueA;

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getSecShortName() {
		return secShortName;
	}

	public void setSecShortName(String secShortName) {
		this.secShortName = secShortName;
	}

	public String getnegMarketValueA() {
		return negMarketValueA;
	}

	public void setnegMarketValueA(String negMarketValueA) {		
		this.negMarketValueA = negMarketValueA;
	}
	
	
	public HangYeClass(String ticker,String secShortName,String negMarketValueA ) {
		this.secShortName = secShortName;
		this.secShortName = secShortName;
		this.negMarketValueA = negMarketValueA;			
	}

	public HangYeClass() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String toString() {
		return this.getTicker()+","+this.getSecShortName()+","+this.getnegMarketValueA();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
