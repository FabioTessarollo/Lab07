package it.polito.tdp.poweroutages.db;

import java.sql.Connection;
import java.util.List;

import it.polito.tdp.poweroutages.model.Blackout;

public class TestConnection {

	public static void main(String[] args) {
		
		try {
			Connection connection = ConnectDB.getConnection();
			PowerOutageDAO pdao = new PowerOutageDAO();
			
			for(int i = 0; i < 11; i++) {
				List<Blackout> lista = pdao.getBlackoutNercData(2000 + i, 2004 + i, 3);
				System.out.println(lista.toString());
			}
			

			
			connection.close();
			System.out.println("Test PASSED");

		} catch (Exception e) {
			System.err.println("Test FAILED");
			e.printStackTrace();
		}
	}

}
