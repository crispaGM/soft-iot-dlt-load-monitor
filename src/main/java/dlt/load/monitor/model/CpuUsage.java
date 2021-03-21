package dlt.load.monitor.model;

public class CpuUsage {
private double cpu;
private long mem;

	
	public CpuUsage(double cpu, long mem) {
	this.cpu = cpu;
	this.mem = mem;
	}


	public double getCpu() {
		return cpu;
	}


	public void setCpu(double cpu) {
		this.cpu = cpu;
	}


	public long getMem() {
		return mem;
	}


	public void setMem(long mem) {
		this.mem = mem;
	}




}
