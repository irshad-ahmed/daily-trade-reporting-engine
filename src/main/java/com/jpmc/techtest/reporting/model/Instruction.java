package com.jpmc.techtest.reporting.model;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

import com.jpmc.techtest.reporting.constants.TransactionType;

/*
 * This class holds the data required for executing the Trading Instruction.
 * Implements Comparable to sort Instruction on descending order.
 * 
 * <p>Validates the settlement date for currency holidays and updates 
 * the settlement date to next working day if settlement date falls on a holiday </p>
 * 
 * <p>Calculates the USD Amount of the Instruction</p>
 * 
 */
public class Instruction implements Comparable<Instruction> {

	private String entity;
	private TransactionType transactionType;
	private BigDecimal agreedFx;
	private String currency;
	private LocalDate instructionDate;
	private LocalDate settlementDate;
	private Double units;
	private BigDecimal pricePerUnit;
	private BigDecimal usdAmount;

	/*
	 * Constructor to create Instruction class
	 * 
	 * <p>Validates the settlement date for currency holidays and updates the
	 * settlement date to next working day if settlement date falls on a holiday
	 * </p>
	 * 
	 * <p>Calculates the USD Amount of the Instruction</p>
	 */
	public Instruction(String entity, TransactionType buyOrSell, BigDecimal agreedFx, String currency,
			LocalDate instructionDate, LocalDate settlementDate, Double units, BigDecimal pricePerUnit) {
		this.entity = entity;
		this.transactionType = buyOrSell;
		this.agreedFx = agreedFx;
		this.currency = currency;
		this.instructionDate = instructionDate;
		this.settlementDate = validateAndUpdateSettlementDate(settlementDate, currency);
		this.units = units;
		this.pricePerUnit = pricePerUnit;
		this.usdAmount = getUSDAmountOfInstruction();
	}

	public String getEntity() {
		return entity;
	}

	public TransactionType getTransactionType() {
		return transactionType;
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

	/*
	 * private method to calculate USD Amount of the instruction
	 * 
	 * @return usdAmount
	 */
	private BigDecimal getUSDAmountOfInstruction() {
		usdAmount = pricePerUnit.multiply(agreedFx).multiply(new BigDecimal(units));
		return usdAmount;
	}

	/*
	 * private method to validateAndUpdateSettlementDate <p>Validates the settlement
	 * date for currency holidays and updates the settlement date to next working
	 * day if settlement date falls on a holiday</p>
	 * 
	 * <p>For AED and SAR currency holidays are Friday and Saturday and for others
	 * its Saturday and Sunday. so the settlement dates are moved to next working
	 * days.
	 * 
	 * Example: For AED or SAR currency if Settlement date falls on Friday or
	 * Saturday, the settlement date is moved next working day (i.e Sunday) by
	 * adding 2 or 1 day respectively. </p>
	 */
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
		return entity + "\t" + transactionType + "\t\t" + agreedFx + "\t\t" + currency + "\t\t" + instructionDate
				+ "\t\t" + settlementDate + "\t" + units + "\t" + pricePerUnit + "\t\t" + usdAmount;
	}
}
