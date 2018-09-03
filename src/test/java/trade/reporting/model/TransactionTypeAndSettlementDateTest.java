package trade.reporting.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import trade.reporting.constants.TransactionType;
import trade.reporting.model.TransactionTypeAndSettlementDate;

public class TransactionTypeAndSettlementDateTest {

	@Test
	public void constructorTest() {
		TransactionTypeAndSettlementDate transactionTypeAndSettlementDate = new TransactionTypeAndSettlementDate(
				TransactionType.BUY, LocalDate.now());
		assertEquals(TransactionType.BUY, transactionTypeAndSettlementDate.getTransactionType());
		assertEquals(LocalDate.now(), transactionTypeAndSettlementDate.getSettlementDate());
	}
}
