#Daily Trade Reporting Engine

Daily Trade Reporting Engine is used to generate following reports
 1. Daily USD amount settled for Buy/Outgoing and Sell/Incoming
 2. Ranking of entities based on Incoming and Outgoing amount  

To view the sample report either run Application class or 
execute artifact jar by running the command **java -jar daily-trade-reporting-engine-0.0.1.jar**  

Approach 
1. Instruction model was created to store an instruction received from a customer. 
2. Instructed Settlement date was updated to next working day for a currency if it fell on a weekend using method validateAndUpdateSettlementDate of Instruction class.  
   USD amount was calculated based on the formula given in the method getUSDAmountOfInstruction.  
   Both Settlement date and USD amount was updated in Instruction class constructor as it needs to be done when the Instruction was received.  
3. TradeReportService class was created to generate the reports.  
	**Daily USD amount settled for Buy/Outgoing and Sell/Incoming :**  
	A report was needed to get the sum of settled amount in USD for day and Transaction Type (BUY OR SELL). Java 8 stream API with Collectors.groupingBy() was used to group an instruction based on transaction type and settlement date. And Collectors.summing was used to sum USD amounts. Thus giving the Daily USD amount settled for Buy and Sell.  
	**Ranking of entities based on Incoming and Outgoing amount :**   
	To Rank entities based on USD amount, the instruction list needed to be sorted in descending order to give highest amount first. Comparable interface was implemented in Instruction class, to sort the Instruction in Descending order based on USD amount.  	
4. Application class which has the main method was used to displaying the results in tabular format as a report for a sample data.  
5. Test cases was written to validate the results.