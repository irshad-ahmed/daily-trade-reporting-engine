package com.jpmc.techtest.reporting;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.jpmc.techtest.reporting.constants.BuyOrSell;
import com.jpmc.techtest.reporting.entity.Instruction;
import com.jpmc.techtest.reporting.grouping.TypeAndDate;
import com.jpmc.techtest.reporting.reports.TradeReports;

/*
 * Main class which displays report
 */

public class Application {

	public static void main(final String[] args) {
		Application application = new Application();
		application.generateUSDReportReport();
		application.generateRankingEntitiesBasedOnAmountReport();
	}
	
	/*
	 * Sample Data
	 */
	private static List<Instruction> instructionList = Arrays.asList(
			new Instruction("foo", BuyOrSell.BUY, new BigDecimal("0.50"), "SGP", LocalDate.now(), LocalDate.now(),
					new Double(200), new BigDecimal("100.25")),
			new Instruction("bar", BuyOrSell.SELL, new BigDecimal("0.22"), "AED", LocalDate.now(), LocalDate.now(),
					new Double(450), new BigDecimal("150.5")),
			new Instruction("foo2", BuyOrSell.BUY, new BigDecimal("0.50"), "SGP", LocalDate.now(), LocalDate.now(),
					new Double(200), new BigDecimal("100.25")),
			new Instruction("bar2", BuyOrSell.SELL, new BigDecimal("0.50"), "AED", LocalDate.now(), LocalDate.now(),
					new Double(200), new BigDecimal("100.25")),
			new Instruction("bar5", BuyOrSell.SELL, new BigDecimal("0.50"), "AED", LocalDate.now(),
					LocalDate.of(2018, Month.SEPTEMBER, 3), new Double(200), new BigDecimal("100.25")));

	public void generateUSDReportReport() {
		System.out.println("============= Daily Trade Settlement Report ================== ");
		System.out.println("BuyOrSell\t Settlement Date\tUSD Amount");
		Map<TypeAndDate, Double> typeSettledDateUSDAmountMap = TradeReports.getUSDAmountSettledByTypeAndDate(instructionList);
		typeSettledDateUSDAmountMap.forEach((typeAndsettledDate,usdAmount)->System.out.println(typeAndsettledDate+"\t\t"+usdAmount));
		System.out.println("============= End of Report =================================== ");
	}

	public void generateRankingEntitiesBasedOnAmountReport() {
		System.out.println("============================================== Entity Ranking Report =============================================================== ");
		System.out.println("Entity\tBuyOrSell\tAgreedFx\tCurrency\tInstruction Date\tSettlement Date\tUnits\tUnit Price\tUSD Amount");
		instructionList = TradeReports.sortInstructionsBasedOnUSDAmount(instructionList);
		instructionList.forEach(System.out::println);
		System.out.println("============================================== End of Report ======================================================================= ");
	}
}
