package dlt.load.monitor.model;


import java.util.ArrayList;
import java.util.Scanner;

import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.SendTransferResponse;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.model.Transfer;
import org.iota.jota.utils.SeedRandomGenerator;
import org.iota.jota.utils.TrytesConverter;
import org.json.JSONObject;

import com.google.gson.Gson;

import dlt.client.tangle.enums.TransactionType;
import dlt.client.tangle.model.Transaction;

public class TesteScriptsBalancer {
	IotaAPI api;
	//Variáveis da API
    String address = "ZLGVEQ9JUZZWCZXLWVNTHBDX9G9KZTJP9VEERIIFHY9SIQKYBVAHIMLHXPQVE9IXFDDXNHQINXJDRPFDXNYVAPLZAW";
    String protocol = "https";
    String url ="nodes.devnet.iota.org";
    int port = 443;
    int depth = 3;
    private String tag = "cloud1/fog";
    int minimumWeightMagnitude = 9;
    int securityLevel = 2;
	public TesteScriptsBalancer() {
		 this.api = new IotaAPI.Builder()
	                .protocol(protocol)
	                .host(url)
	                .port(port)
	                .build();	}
	
	public static void main(String[] args) {
	TesteScriptsBalancer testeBalancer = new TesteScriptsBalancer();
	
	Scanner scanner = new Scanner(System.in);
	int opcao = 10;
	while (opcao!=0){
		System.out.println(" 1-status 2-EntryReply 3-LBEntry 4-Request 5- REPLY ");
	 opcao = scanner.nextInt();

		switch (opcao) {
		case 1:
			testeBalancer.sendStatusTransaction();
			break;
		case 2:
			testeBalancer.sendLBEntryReplyTransaction();;
			break;
		case 3:
			testeBalancer.sendLBEntryTransaction();
			break;
			
		case 4:
			testeBalancer.sendRequestTransaction();
			break;
			
		case 5:
			testeBalancer.sendREPLYTransaction();
			break;
		case 6:
			System.out.println(testeBalancer.api.getNodeInfo());
			testeBalancer.api.addNeighbors("tcp://localhost:14266");
			break;

		default:
			break;
		}
		
		
		
	}
	
	

	
	}
	
	public void sendStatusTransaction() {
		Transaction transaction = new Transaction();
    	transaction.setTarget("");
    	transaction.setTimestamp(System.currentTimeMillis());
    	transaction.setLbEntry(false);
    	transaction.setSource("teste");
		transaction.setType(TransactionType.LB_ENTRY);
		writeToTangle(transaction);
		

	}
	
	public void sendLBEntryTransaction() {
		System.out.println("LB ENTROU");
		Transaction transaction = new Transaction();
    	transaction.setTarget("");
    	transaction.setTimestamp(System.currentTimeMillis());
    	transaction.setLbEntry(true);
    	transaction.setSource("teste");
		transaction.setType(TransactionType.LB_ENTRY);
		writeToTangle(transaction);

	}
	
	public void sendLBEntryReplyTransaction() {
		Transaction transaction = new Transaction();
    	transaction.setTarget("testado");
    	transaction.setTimestamp(System.currentTimeMillis());
    	transaction.setSource("teste");
		transaction.setType(TransactionType.LB_ENTRY_REPLY);
		writeToTangle(transaction);


	}
	
	public void sendRequestTransaction() {
		Transaction transaction = new Transaction();
    	transaction.setTarget("testado");
    	transaction.setTimestamp(System.currentTimeMillis());
    	transaction.setSource("teste");
		transaction.setType(TransactionType.LB_REQUEST);
		JSONObject device  = new JSONObject();
		device.put("id", "deviceteste1");
		transaction.setDeviceSwap(device.toString());

		writeToTangle(transaction);


	}
	
	public void sendREPLYTransaction() {
		Transaction transaction = new Transaction();
    	transaction.setTarget("testado");
    	transaction.setTimestamp(System.currentTimeMillis());
    	transaction.setSource("teste");
		transaction.setType(TransactionType.LB_REPLY);
		writeToTangle(transaction);


	}
	
	public void writeToTangle(Transaction transaction) {

		Gson gson = new Gson();
    	String message = gson.toJson(transaction);

		String myRandomSeed = SeedRandomGenerator.generateNewSeed();
		String messageTrytes = TrytesConverter.asciiToTrytes(message);
		String tagTrytes = TrytesConverter.asciiToTrytes(tag);
		Transfer zeroValueTransaction = new Transfer(address, 0, messageTrytes, tagTrytes);
		ArrayList<Transfer> transfers = new ArrayList<Transfer>();
		transfers.add(zeroValueTransaction);

		try {
			SendTransferResponse response = api.sendTransfer(myRandomSeed, securityLevel, depth,
					minimumWeightMagnitude, transfers, null, null, false, false, null);
			System.out.println(response.toString());
		} catch (ArgumentException e) {
			// Handle error
			e.printStackTrace();
		}

	}

}
