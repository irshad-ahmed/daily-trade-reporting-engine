package com.jpmc.techtest.reporting.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import org.junit.Test;
import com.jpmc.techtest.reporting.constants.TransactionType;
import com.jpmc.techtest.reporting.model.Instruction;

import junit.framework.Assert;

public class InstructionTest {

	@Test
	public void constructorTest() {
		Instruction instruction = new Instruction("bar5", TransactionType.SELL, new BigDecimal("0.50"), "AED",
				LocalDate.now(), LocalDate.of(2018, Month.SEPTEMBER, 3), new Double(200), new BigDecimal("100.25"));
		Assert.assertEquals("bar5", instruction.getEntity());
		Assert.assertEquals(TransactionType.SELL, instruction.getTransactionType());
		Assert.assertTrue(new BigDecimal("0.50").compareTo(instruction.getAgreedFx()) == 0);
		Assert.assertEquals("AED", instruction.getCurrency());
		Assert.assertEquals(LocalDate.now(), instruction.getInstructionDate());
		Assert.assertEquals(LocalDate.of(2018, Month.SEPTEMBER, 3), instruction.getSettlementDate());
		Assert.assertEquals(new Double("200"), instruction.getUnits());
		Assert.assertTrue(new BigDecimal("100.25").compareTo(instruction.getPricePerUnit()) == 0);
		Assert.assertTrue(new BigDecimal("10025").compareTo(instruction.getUsdAmount()) == 0);
	}

	@Test
	public void hoildaySettlementDateForAEDAndSARCurrencyTest() {
		Instruction instructionSAR = new Instruction("foo2", TransactionType.BUY, new BigDecimal("0.50"), "SAR",
				LocalDate.now(), LocalDate.of(2018, Month.AUGUST, 31), new Double(200), new BigDecimal("100.25"));
		Instruction instructionAED = new Instruction("bar2", TransactionType.SELL, new BigDecimal("0.50"), "AED",
				LocalDate.now(), LocalDate.of(2018, Month.SEPTEMBER, 1), new Double(200), new BigDecimal("100.25"));
		Assert.assertEquals(LocalDate.of(2018, Month.SEPTEMBER, 2), instructionSAR.getSettlementDate());
		Assert.assertEquals(LocalDate.of(2018, Month.SEPTEMBER, 2), instructionAED.getSettlementDate());
	}

	@Test
	public void hoildaySettlementDateForOtherCurrencyTest() {
		Instruction instructionUSD = new Instruction("foo2", TransactionType.BUY, new BigDecimal("1.00"), "USD",
				LocalDate.now(), LocalDate.of(2018, Month.SEPTEMBER, 1), new Double(200), new BigDecimal("100.25"));
		Instruction instructionSGP = new Instruction("foo", TransactionType.BUY, new BigDecimal("0.50"), "SGP",
				LocalDate.now(), LocalDate.of(2018, Month.SEPTEMBER, 2), new Double(200), new BigDecimal("100.25"));
		Assert.assertEquals(LocalDate.of(2018, Month.SEPTEMBER, 3), instructionUSD.getSettlementDate());
		Assert.assertEquals(LocalDate.of(2018, Month.SEPTEMBER, 3), instructionSGP.getSettlementDate());
	}

	@Test
	public void workingDaySettlementDateForAEDAndSARCurrencyTest() {
		Instruction instructionNormalAED = new Instruction("bar2", TransactionType.SELL, new BigDecimal("0.50"), "AED",
				LocalDate.now(), LocalDate.of(2018, Month.AUGUST, 30), new Double(200), new BigDecimal("100.25"));
		Assert.assertEquals(LocalDate.of(2018, Month.AUGUST, 30), instructionNormalAED.getSettlementDate());
	}

	@Test
	public void workingDaySettlementDateForOtherCurrencyTest() {
		Instruction instructionNormalUSD = new Instruction("bar2", TransactionType.SELL, new BigDecimal("0.50"), "USD",
				LocalDate.now(), LocalDate.of(2018, Month.AUGUST, 30), new Double(200), new BigDecimal("100.25"));
		Assert.assertEquals(LocalDate.of(2018, Month.AUGUST, 30), instructionNormalUSD.getSettlementDate());
	}

	@Test
	public void USDAmountOfTradeTest() {
		Instruction instructionAED = new Instruction("bar", TransactionType.SELL, new BigDecimal("0.22"), "AED",
				LocalDate.now(), LocalDate.now(), new Double(450), new BigDecimal("150.5"));
		Assert.assertTrue(new BigDecimal("14899.50").compareTo(instructionAED.getUsdAmount()) == 0);
	}

}
