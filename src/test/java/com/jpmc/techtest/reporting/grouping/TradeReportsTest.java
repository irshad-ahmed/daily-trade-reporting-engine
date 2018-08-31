package com.jpmc.techtest.reporting.grouping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.jpmc.techtest.reporting.constants.BuyOrSell;
import com.jpmc.techtest.reporting.entity.Instruction;
import com.jpmc.techtest.reporting.reports.TradeReports;

public class TradeReportsTest {

	private static final List<Instruction> instructionList = Arrays.asList(
			new Instruction("foo", BuyOrSell.BUY, new BigDecimal("0.50"), "SGP", LocalDate.now(),
					LocalDate.of(2018, Month.AUGUST, 31), new Double(200), new BigDecimal("100.25")),
			new Instruction("bar", BuyOrSell.SELL, new BigDecimal("0.22"), "AED", LocalDate.now(),
					LocalDate.of(2018, Month.AUGUST, 31), new Double(450), new BigDecimal("150.5")),
			new Instruction("foo2", BuyOrSell.BUY, new BigDecimal("0.50"), "SGP", LocalDate.now(),
					LocalDate.of(2018, Month.AUGUST, 31), new Double(200), new BigDecimal("100.25")),
			new Instruction("bar2", BuyOrSell.SELL, new BigDecimal("0.50"), "AED", LocalDate.now(),
					LocalDate.of(2018, Month.AUGUST, 31), new Double(200), new BigDecimal("100.25")),
			new Instruction("bar5", BuyOrSell.SELL, new BigDecimal("0.50"), "AED", LocalDate.now(),
					LocalDate.of(2018, Month.SEPTEMBER, 3), new Double(200), new BigDecimal("100.25")));

	@Test
	public void getUSDAmountSettledByTypeAndDateTest() {
		Map<TypeAndDate, Double> typeSettledDateUSDAmountMap = TradeReports
				.getUSDAmountSettledByTypeAndDate(instructionList);

		TypeAndDate buyForSettlementDate31August2018 = new TypeAndDate(BuyOrSell.BUY,
				LocalDate.of(2018, Month.AUGUST, 31));
		assertEquals(new Double("20050.00"), typeSettledDateUSDAmountMap.get(buyForSettlementDate31August2018));

		TypeAndDate sellForSettlementDate2September2018 = new TypeAndDate(BuyOrSell.SELL,
				LocalDate.of(2018, Month.SEPTEMBER, 2));
		assertEquals(new Double("24924.50"), typeSettledDateUSDAmountMap.get(sellForSettlementDate2September2018));

		TypeAndDate sellForSettlementDate3September2018 = new TypeAndDate(BuyOrSell.SELL,
				LocalDate.of(2018, Month.SEPTEMBER, 3));
		assertEquals(new Double("10025.00"), typeSettledDateUSDAmountMap.get(sellForSettlementDate3September2018));

	}

	@Test
	public void rankingEntitiesBasedOnAmountTest() {

		List<Instruction> entityRankingBasedOnUSDAmount = TradeReports
				.sortInstructionsBasedOnUSDAmount(instructionList);
		assertEquals("bar", entityRankingBasedOnUSDAmount.get(0).getEntity());
		assertTrue(new BigDecimal("14899.50").compareTo(entityRankingBasedOnUSDAmount.get(0).getUsdAmount()) == 0);
	}

}
