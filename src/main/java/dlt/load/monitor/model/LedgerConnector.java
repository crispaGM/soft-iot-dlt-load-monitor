package dlt.load.monitor.model;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import dlt.client.tangle.enums.TransactionType;
import dlt.client.tangle.model.Transaction;
import dlt.client.tangle.services.ILedgerWriter;

/**
 *
 * @author Uellington Damasceno
 * @version 0.0.1
 */
public class LedgerConnector {
    private ILedgerWriter ledgerWriter;
    Logger logger;
    FileHandler fh;

    public LedgerConnector() {
    	initLogger();
    }
    
    public void initLogger() {
        logger = Logger.getLogger("Ledger Connector Log");  
           try {
   			fh = new FileHandler("LedgerConnectorLogFile.log");
   		} catch (SecurityException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}  
           logger.addHandler(fh);
           SimpleFormatter formatter = new SimpleFormatter();  
           fh.setFormatter(formatter); 

    }
    
    
    public void setLedgerWriter(ILedgerWriter ledgerWriter){
        this.ledgerWriter = ledgerWriter;
        System.out.println("LedgerWriter injected");
    }
    
    public void put (int lastCharge, boolean lbEntry, String source) throws InterruptedException {
    	Transaction transaction = new Transaction();
    	transaction.setTarget("");
    	transaction.setTimestamp(System.currentTimeMillis());
    	transaction.setLbEntry(lbEntry);
    	transaction.setSource(source);
    	if(lbEntry)
    		transaction.setType(TransactionType.LB_ENTRY);
    	else {
    		transaction.setType(TransactionType.LB_STATUS);
    		
        logger.info(transaction.toString());  
	

    	}
    	this.ledgerWriter.put(transaction);
    }
}
