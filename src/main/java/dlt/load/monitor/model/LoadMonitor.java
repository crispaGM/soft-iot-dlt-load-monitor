package dlt.load.monitor.model;

import java.awt.image.SampleModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
//import com.sun.management.OperatingSystemMXBean;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import dlt.auth.services.IDevicePropertiesManager;


/**
 *
 * @author Uellington Damasceno
 */
public class LoadMonitor implements Runnable {

    private final ScheduledExecutorService executor;
	private Processor processor;
    private int samplingInterval, sampling;
    private IDevicePropertiesManager deviceManager;
//    private Queue<CpuUsage> samples = new LinkedList<CpuUsage>();
    private Queue<Integer> samples = new LinkedList<Integer>();
    private boolean parada = true;
    
    public LoadMonitor(int samplingInterval, int sampling) {
        this.samplingInterval = samplingInterval;
        this.sampling = sampling;
        this.executor = Executors.newSingleThreadScheduledExecutor();

    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
        System.out.println("Processor Injetado");
    }
    
    public void setDeviceManager(IDevicePropertiesManager deviceManager) {
        this.deviceManager = deviceManager;
    }

    public void start() {
        this.executor.scheduleAtFixedRate(this, 0, this.samplingInterval, TimeUnit.SECONDS);
    	run(); 
    }

    public void stop() {
    }

    @Override
    public void run() {
       Long startTime;
       Long currentTime;
       while(parada) {
  		  startTime = System.currentTimeMillis();
  		  currentTime = System.currentTimeMillis();;
  		while((currentTime- startTime) < samplingInterval){
  			currentTime = System.currentTimeMillis();
  		}
  		try {
			getLoad();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
  	}
    }
	private void getLoad() throws InterruptedException, IOException {
        int qtdDevices;
		try {
			qtdDevices = this.deviceManager.getAllDevices().size();
			samples.add(qtdDevices);
			this.processor.updateBrokerStatus(qtdDevices);
			if(samples.size() == sampling) {
				samples.remove();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isParada() {
		return parada;
	}

	public void setParada(boolean parada) {
		this.parada = parada;
	}
}
