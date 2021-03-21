package dlt.load.monitor.model;

/**
 *
 * @author Uellington Damasceno
 * @version 0.0.1
 */
public class BrokerStatus {
    
    private boolean full;
    
    public void setFull(boolean full){
        this.full = full;
    }
    
    public boolean isFull(){
        return this.full;
    }
    
}
