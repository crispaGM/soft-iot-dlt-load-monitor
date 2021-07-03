package dlt.load.monitor.model;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TesteLogger {
	final static String  loggerDir = "src\\main\\java\\dlt\\load\\monitor\\model\\Log\\Logteste.txt";
    static Logger logger = Logger.getLogger("MyLog");  
    static FileHandler fh;
    
    public TesteLogger() {
    }
    public static void main(String[] args) {
    	 try {  

    	        // This block configure the logger with handler and formatter  
    	        fh = new FileHandler("./Logteste.txt");  
    	        logger.addHandler(fh);
    	        SimpleFormatter formatter = new SimpleFormatter();  
    	        fh.setFormatter(formatter);  

    	        // the following statement is used to log any messages  
    	        logger.info("My first log");  

    	    }
    	    catch (SecurityException e) {  
    	        e.printStackTrace();  
    	    } catch (IOException e) {  
    	        e.printStackTrace();  
    	    }  

    	    logger.info("Hi How r u?");  
	}
    
   
}


