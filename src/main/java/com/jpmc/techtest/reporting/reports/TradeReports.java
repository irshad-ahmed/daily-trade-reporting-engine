package com.jpmc.techtest.reporting.reports;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jpmc.techtest.reporting.entity.Instruction;
import com.jpmc.techtest.reporting.grouping.TypeAndDate;

public class TradeReports {

	public static Map<TypeAndDate, Double> getUSDAmountSettledByTypeAndDate(final List<Instruction> instructionList) {
		Map<TypeAndDate, Double> byTypeAndDate = instructionList.stream()
				.collect(Collectors.groupingBy(
						instruction -> new TypeAndDate(instruction.getBuyOrSell(), instruction.getSettlementDate()),
						Collectors.summingDouble(instruction -> instruction.getUsdAmount().doubleValue())));
		return byTypeAndDate;
	}

	public static List<Instruction> sortInstructionsBasedOnUSDAmount(final List<Instruction> instructionList) {
		Collections.sort(instructionList);
		return instructionList;
	}

}
