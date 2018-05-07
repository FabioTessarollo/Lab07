package it.polito.tdp.poweroutages.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Blackout;
import it.polito.tdp.poweroutages.model.Nerc;

public class PowerOutageDAO {

	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public LinkedList<Blackout> getBlackoutNercData(int inizio, int fine, int nerc) {

		String sql = "SELECT poweroutages.id, poweroutages.date_event_began, poweroutages.date_event_finished, poweroutages.customers_affected\n" + 
				"FROM poweroutages\n" + 
				"WHERE YEAR(date_event_began) > ? AND YEAR(date_event_finished) < ? AND nerc_id = ?";
		LinkedList<Blackout> blackoutList = new LinkedList<Blackout>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, inizio);
			st.setInt(2, fine);
			st.setInt(3, nerc);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				LocalDateTime t1 = res.getTimestamp("date_event_began").toLocalDateTime();
				LocalDateTime t2 = res.getTimestamp("date_event_finished").toLocalDateTime();
				Blackout bo = new Blackout(res.getInt("id"), Duration.between(t2, t1), res.getInt("customers_affected"));
				blackoutList.add(bo);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return blackoutList;
	}
	
	

}
