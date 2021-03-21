package dlt.load.monitor.model;

import dlt.load.monitor.services.IProcessor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Uellington Damasceno
 * @version 0.0.1
 */
public class Processor implements IProcessor<BrokerStatus> {

    private BrokerStatus lastValueCalculated;
    private LedgerConnector connector;
    private int loadLimit;
    private static Integer id; // temporário até mapear o indentificador do gateway 
    private boolean overLoad = false;
    private Queue<Integer> samples = new LinkedList<Integer>();
    private String tag;
    Logger logger;
    FileHandler fh;



    public Processor(int loadLimit, int idGateway, String tagGateway) {
        this.lastValueCalculated = new BrokerStatus();
        this.loadLimit = loadLimit;
        this.id = idGateway;
        this.tag = tagGateway;
        this.initLogger();
    }

    public void initLogger() {
        logger = Logger.getLogger("Processor Log");  
           try {
   			fh = new FileHandler("ProcessorLogFile.log",true);
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
    
    
    public void setConnector(LedgerConnector connector){
        this.connector = connector;
    }
    
    protected void updateBrokerStatus(Integer qtdDevices) throws InterruptedException {
        
    	if(qtdDevices >= loadLimit ) {
    		System.out.println("Update Broker");
    		overLoad = true;
            this.connector.put(qtdDevices,true,tag+id);
            logger.info("Transaction send to tangle. Charge : " + qtdDevices);
            
            
    	}else {
    		System.out.println("Update Broker");
    		this.connector.put(qtdDevices,false,tag+id);
    		 logger.info("Transaction send to tangle. Charge : " + qtdDevices);  

    	}

    }
    

//    @Override
//    public BrokerStatus getLastFitness() {
//        try {
////			this.updateBrokerStatus(0, 0);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return this.lastValueCalculated;
//    }
    
    public double getAverageCharge(Queue<Integer> samples){
    	int sum = 0;
    	int total = samples.size();
    	
    	for (Integer cpuUsage : samples ) {
    		sum +=cpuUsage;
    	}
    	
    	
    	return sum/total;
    	
    }

	@Override
	public BrokerStatus getLastFitness() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isOverLoad() {
		return overLoad;
	}
}
