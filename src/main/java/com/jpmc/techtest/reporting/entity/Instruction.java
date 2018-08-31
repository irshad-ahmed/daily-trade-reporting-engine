package com.jpmc.techtest.reporting.entity;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

import com.jpmc.techtest.reporting.constants.BuyOrSell;

public class Instruction implements Comparable<Instruction> {

	private String entity;
	private BuyOrSell buyOrSell;
	private BigDecimal agreedFx;
	private String currency;
	private LocalDate instructionDate;
	private LocalDate settlementDate;
	private Double units;
	private BigDecimal pricePerUnit;
	private BigDecimal usdAmount;

	public Instruction(String entity, BuyOrSell buyOrSell, BigDecimal agreedFx, String currency,
			LocalDate instructionDate, LocalDate settlementDate, Double units, BigDecimal pricePerUnit) {
		this.entity = entity;
		this.buyOrSell = buyOrSell;
		this.agreedFx = agreedFx;
		this.currency = currency;
		this.instructionDate = instructionDate;
		this.settlementDate = validateAndUpdateSettlementDate(settlementDate, currency);
		this.units = units;
		this.pricePerUnit = pricePerUnit;
		getUSDAmountOfTrade();
	}

	public String getEntity() {
		return entity;
	}

	public BuyOrSell getBuyOrSell() {
		return buyOrSell;
	}

	public BigDecimal getAgreedFx() {
		return agreedFx;
	}

	public String getCurrency() {
		return currency;
	}

	public LocalDate getInstructionDate() {
		return instructionDate;
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	public Double getUnits() {
		return units;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public BigDecimal getUsdAmount() {
		return usdAmount;
	}

	private BigDecimal getUSDAmountOfTrade() {
		usdAmount = pricePerUnit.multiply(agreedFx).multiply(new BigDecimal(units));
		return usdAmount;
	}

	private LocalDate validateAndUpdateSettlementDate(LocalDate settlementDate, String currency) {
		DayOfWeek dayOfWeek = settlementDate.getDayOfWeek();
		if ("AED".equals(currency) || "SAR".equals(currency)) {
			if (DayOfWeek.FRIDAY.equals(dayOfWeek)) {
				settlementDate = settlementDate.plusDays(2);
			}
			if (DayOfWeek.SATURDAY.equals(dayOfWeek)) {
				settlementDate = settlementDate.plusDays(1);
			}
		} else {
			if (DayOfWeek.SATURDAY.equals(dayOfWeek)) {
				settlementDate = settlementDate.plusDays(2);
			}
			if (DayOfWeek.SUNDAY.equals(dayOfWeek)) {
				settlementDate = settlementDate.plusDays(1);
			}
		}
		return settlementDate;
	}

	public int compareTo(final Instruction instruction) {
		return instruction.usdAmount.compareTo(this.usdAmount);
	}

	@Override
	public String toString() {
		return entity + "\t" + buyOrSell + "\t\t" + agreedFx + "\t\t" + currency + "\t\t" + instructionDate + "\t\t"
				+ settlementDate + "\t" + units + "\t" + pricePerUnit + "\t\t" + usdAmount;
	}
}
