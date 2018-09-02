package com.jpmc.techtest.reporting;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.jpmc.techtest.reporting.constants.TransactionType;
import com.jpmc.techtest.reporting.grouping.TransactionTypeAndSettlementDate;
import com.jpmc.techtest.reporting.model.Instruction;
import com.jpmc.techtest.reporting.service.TradeReportService;

/*
 * This class displays reports of 
 * 1. Daily USD amount settled for Buy/Outgoing and Sell/Incoming
 * 2. Ranking of entities based on Incoming and Outgoing amount
 * 
 * For demonstration purposes sample data is used.
 */

public class Application {

	TradeReportService tradeReportService = new TradeReportService();

	/*
	 * main method where the execution would start. Will call the method for
	 * displaying reports for 1. Daily USD amount settled for Buy/Outgoing and
	 * Sell/Incoming 2. Ranking of entities based on Incoming and Outgoing amount
	 */
	public static void main(final String[] args) {
		Application application = new Application();
		application.displayUSDReportReport();
		application.displayRankingEntitiesBasedOnAmountReport();
	}

	/*
	 * Sample Data
	 */
	private static List<Instruction> instructionList = Arrays.asList(
			new Instruction("foo", TransactionType.BUY, new BigDecimal("0.50"), "SGP", LocalDate.now(), LocalDate.now(),
					new Double(200), new BigDecimal("100.25")),
			new Instruction("bar", TransactionType.SELL, new BigDecimal("0.22"), "AED", LocalDate.now(),
					LocalDate.now(), new Double(450), new BigDecimal("150.5")),
			new Instruction("foo2", TransactionType.BUY, new BigDecimal("0.50"), "SGP", LocalDate.now(),
					LocalDate.now(), new Double(200), new BigDecimal("100.25")),
			new Instruction("bar2", TransactionType.SELL, new BigDecimal("0.50"), "AED", LocalDate.now(),
					LocalDate.now(), new Double(200), new BigDecimal("100.25")),
			new Instruction("bar5", TransactionType.SELL, new BigDecimal("0.50"), "AED", LocalDate.now(),
					LocalDate.of(2018, Month.SEPTEMBER, 3), new Double(200), new BigDecimal("100.25")));

	/*
	 * Displays the daily USD amount settled for Buy/Outgoing and Sell/Incoming in
	 * tabular format.
	 */
	public void displayUSDReportReport() {
		System.out.println("============= Daily Trade Settlement Report ================== ");
		System.out.println("BuyOrSell\t Settlement Date\tUSD Amount");
		Map<TransactionTypeAndSettlementDate, Double> typeSettledDateUSDAmountMap = tradeReportService
				.getUSDAmountSettledByTransactionTypeAndDate(instructionList);
		typeSettledDateUSDAmountMap.forEach(
				(typeAndsettledDate, usdAmount) -> System.out.println(typeAndsettledDate + "\t\t" + usdAmount));
		System.out.println("============= End of Report =================================== ");
	}

	/*
	 * Displays ranking of entities based on Incoming and Outgoing amount in tabular
	 * format.
	 */

	public void displayRankingEntitiesBasedOnAmountReport() {
		System.out.println(
				"============================================== Entity Ranking Report =============================================================== ");
		System.out.println(
				"Entity\tBuyOrSell\tAgreedFx\tCurrency\tInstruction Date\tSettlement Date\tUnits\tUnit Price\tUSD Amount");
		instructionList = tradeReportService.sortInstructionsBasedOnUSDAmount(instructionList);
		instructionList.forEach(System.out::println);
		System.out.println(
				"============================================== End of Report ======================================================================= ");
	}
}
