package trade.reporting.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import trade.reporting.model.Instruction;
import trade.reporting.model.TransactionTypeAndSettlementDate;

/*
 * This class is used to generate the below reports
 * 1. Daily USD amount settled for Buy/Outgoing and Sell/Incoming
 * 2. Ranking of entities based on Incoming and Outgoing amount
 */
public class TradeReportService {

	/*
	 * getUSDAmountSettledByTransactionTypeAndDate takes the list of Instruction as
	 * a parameter. The list of instruction is grouped by TransactionType and
	 * Settlement Date. The USD Amounts for the grouped instruction are summed to
	 * get USD Amount for the transactionType and settlementDate.
	 * 
	 * @param instructionList
	 * 
	 * @return transactionTypeAndSettlementDateUSDAmountMap
	 */
	public Map<TransactionTypeAndSettlementDate, Double> getUSDAmountSettledByTransactionTypeAndDate(
			final List<Instruction> instructionList) {
		Map<TransactionTypeAndSettlementDate, Double> transactionTypeAndSettlementDateUSDAmountMap = instructionList
				.stream()
				.collect(Collectors.groupingBy(
						instruction -> new TransactionTypeAndSettlementDate(instruction.getTransactionType(),
								instruction.getSettlementDate()),
						Collectors.summingDouble(instruction -> instruction.getUsdAmount().doubleValue())));
		return transactionTypeAndSettlementDateUSDAmountMap;
	}

	/*
	 * The list of instruction is sorted based on USD amount in descending order.
	 * The Instruction already implements Comparable interface for doing that. This
	 * helps to rank entity based on their USD amount.
	 * 
	 * @param instructionList
	 * 
	 * @return instructionList
	 */
	public List<Instruction> sortInstructionsBasedOnUSDAmount(final List<Instruction> instructionList) {
		Collections.sort(instructionList);
		return instructionList;
	}

}
