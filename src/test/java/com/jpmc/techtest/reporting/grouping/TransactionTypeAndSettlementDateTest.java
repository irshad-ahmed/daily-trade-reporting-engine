package com.jpmc.techtest.reporting.grouping;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import com.jpmc.techtest.reporting.constants.TransactionType;

public class TransactionTypeAndSettlementDateTest {

	@Test
	public void constructorTest() {

		TransactionTypeAndSettlementDate transactionTypeAndSettlementDate = new TransactionTypeAndSettlementDate(
				TransactionType.BUY, LocalDate.now());
		assertEquals(TransactionType.BUY, transactionTypeAndSettlementDate.getTransactionType());
		assertEquals(LocalDate.now(), transactionTypeAndSettlementDate.getSettlementDate());
	}
}
