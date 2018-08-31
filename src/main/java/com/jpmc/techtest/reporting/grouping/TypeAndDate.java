package com.jpmc.techtest.reporting.grouping;

import java.time.LocalDate;

import com.jpmc.techtest.reporting.constants.BuyOrSell;

public class TypeAndDate {

	private BuyOrSell buyOrSell;
	private LocalDate settlementDate;

	public TypeAndDate(BuyOrSell buyOrSell, LocalDate settlementDate) {
		this.buyOrSell = buyOrSell;
		this.settlementDate = settlementDate;
	}

	public BuyOrSell getBuyOrSell() {
		return buyOrSell;
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buyOrSell == null) ? 0 : buyOrSell.hashCode());
		result = prime * result + ((settlementDate == null) ? 0 : settlementDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypeAndDate other = (TypeAndDate) obj;
		if (buyOrSell != other.buyOrSell)
			return false;
		if (settlementDate == null) {
			if (other.settlementDate != null)
				return false;
		} else if (!settlementDate.equals(other.settlementDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return buyOrSell + "\t\t " + settlementDate;
	}

}
