package trade.reporting.model;

import java.time.LocalDate;

import trade.reporting.constants.TransactionType;

/*
 * This class is used for grouping {link @Instruction} based TransactionType and SettlementDate.
 */

public class TransactionTypeAndSettlementDate {

	private TransactionType transactionType;
	private LocalDate settlementDate;

	/*
	 * Constructor to create an object of this class
	 */
	public TransactionTypeAndSettlementDate(final TransactionType transactionType, final LocalDate settlementDate) {
		this.transactionType = transactionType;
		this.settlementDate = settlementDate;
	}

	/*
	 * Retrieves Transaction type {link @TransactionType}
	 * 
	 * @return trasactionType
	 */
	public TransactionType getTransactionType() {
		return transactionType;
	}

	/*
	 * Retrieves Settlement Date
	 * 
	 * @return settlementDate
	 */
	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((transactionType == null) ? 0 : transactionType.hashCode());
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
		TransactionTypeAndSettlementDate other = (TransactionTypeAndSettlementDate) obj;
		if (transactionType != other.transactionType)
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
		return transactionType + "\t\t " + settlementDate;
	}

}
