package trade.reporting.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import trade.reporting.constants.TransactionType;
import trade.reporting.model.Instruction;
import trade.reporting.model.TransactionTypeAndSettlementDate;

public class TradeReportServiceTest {

	private TradeReportService tradeReportService = new TradeReportService();
	private List<Instruction> instructionList;

	@Before
	public void setUp() throws Exception {
		instructionList = Arrays.asList(
				new Instruction("foo", TransactionType.BUY, new BigDecimal("0.50"), "SGP", LocalDate.now(),
						LocalDate.of(2018, Month.AUGUST, 31), new Double(200), new BigDecimal("100.25")),
				new Instruction("bar", TransactionType.SELL, new BigDecimal("0.22"), "AED", LocalDate.now(),
						LocalDate.of(2018, Month.AUGUST, 31), new Double(450), new BigDecimal("150.5")),
				new Instruction("foo2", TransactionType.BUY, new BigDecimal("0.50"), "SGP", LocalDate.now(),
						LocalDate.of(2018, Month.AUGUST, 31), new Double(200), new BigDecimal("100.25")),
				new Instruction("bar2", TransactionType.SELL, new BigDecimal("0.50"), "AED", LocalDate.now(),
						LocalDate.of(2018, Month.AUGUST, 31), new Double(200), new BigDecimal("100.25")),
				new Instruction("bar5", TransactionType.SELL, new BigDecimal("0.50"), "AED", LocalDate.now(),
						LocalDate.of(2018, Month.SEPTEMBER, 3), new Double(200), new BigDecimal("100.25")));
	}

	@Test
	public void getUSDAmountSettledByTypeAndDateTest() {
		Map<TransactionTypeAndSettlementDate, Double> typeSettledDateUSDAmountMap = tradeReportService
				.getUSDAmountSettledByTransactionTypeAndDate(instructionList);

		TransactionTypeAndSettlementDate buyForSettlementDate31August2018 = new TransactionTypeAndSettlementDate(
				TransactionType.BUY, LocalDate.of(2018, Month.AUGUST, 31));
		assertEquals(new Double("20050.00"), typeSettledDateUSDAmountMap.get(buyForSettlementDate31August2018));

		TransactionTypeAndSettlementDate sellForSettlementDate2September2018 = new TransactionTypeAndSettlementDate(
				TransactionType.SELL, LocalDate.of(2018, Month.SEPTEMBER, 2));
		assertEquals(new Double("24924.50"), typeSettledDateUSDAmountMap.get(sellForSettlementDate2September2018));

		TransactionTypeAndSettlementDate sellForSettlementDate3September2018 = new TransactionTypeAndSettlementDate(
				TransactionType.SELL, LocalDate.of(2018, Month.SEPTEMBER, 3));
		assertEquals(new Double("10025.00"), typeSettledDateUSDAmountMap.get(sellForSettlementDate3September2018));

	}

	@Test
	public void rankingEntitiesBasedOnAmountTest() {
		List<Instruction> entityRankingBasedOnUSDAmount = tradeReportService
				.sortInstructionsBasedOnUSDAmount(instructionList);
		assertEquals("bar", entityRankingBasedOnUSDAmount.get(0).getEntity());
		assertTrue(new BigDecimal("14899.50").compareTo(entityRankingBasedOnUSDAmount.get(0).getUsdAmount()) == 0);
	}

}
