package solutions.egen.rrs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import solutions.egen.rrs.exception.ExceptionHandler;
import solutions.egen.rrs.model.ReservationDetails;
import solutions.egen.rrs.model.RestaurantProfile;
import solutions.egen.rrs.model.SeatingArrangements;
import solutions.egen.rrs.utils.DBUtils;

public class ReservationDAO {

	public boolean autoAssign(ReservationDetails mr) throws ExceptionHandler {

		boolean flag = false;
		Connection conn = DBUtils.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = conn.prepareStatement("SELECT AutoAssign FROM RestaurantProfile WHERE RestaurantID=?");
			ps.setInt(1, mr.getResturantId());

			rs = ps.executeQuery();

			if (rs.next()) {
				if (rs.getString("AutoAssign").equals("Y")) {
					flag = true;
				}
			} else {
				throw new ExceptionHandler("No such Restaurants found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(), e.getCause());
		} finally {

			DBUtils.closeResouce(ps, rs, conn);

		}
		return flag;

	}

	public ArrayList<Integer> AvailabilityofTable(ReservationDetails mr) throws ExceptionHandler {

		ArrayList<Integer> TableNumbers = new ArrayList<Integer>();
		Connection conn = DBUtils.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"SELECT TableNumber FROM SeatingArrangements WHERE RestaurantID=? AND SeatsAvailable >= ? ORDER BY SeatsAvailable ");
			ps.setInt(1, mr.getResturantId());
			ps.setInt(2, mr.getPartySize());

			rs = ps.executeQuery();

			while (rs.next()) {
				TableNumbers.add(rs.getInt("TableNumber"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(), e.getCause());
		} finally {

			DBUtils.closeResouce(ps, rs, conn);

		}

		return TableNumbers;

	}

	public ReservationDetails searchForAvailableTables(ReservationDetails mr, ArrayList<Integer> tables)
			throws ExceptionHandler {
		Connection conn = DBUtils.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			for (Integer tableNumberAvialble : tables) {
				ps = conn.prepareStatement(
						"SELECT * FROM ReservationDetails WHERE RestaurantID=? AND Date=? AND Time BETWEEN SUBTIME(?, '01:00:00') AND ADDTIME(?, '01:00:00') AND AssignedTableNumber=? AND Status <> 'Cancel' ");
				ps.setInt(1, mr.getResturantId());
				ps.setString(2, mr.getDate());
				ps.setString(3, mr.getTime());
				ps.setString(4, mr.getTime());
				ps.setInt(5, tableNumberAvialble);

				rs = ps.executeQuery();
				if (!rs.next()) {
					mr.setAssignedTableNumber(tableNumberAvialble);
					break;
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(), e.getCause());

		} finally {

			DBUtils.closeResouce(ps, rs, conn);

		}

		return mr;
	}

	public void createReservation(ReservationDetails mr) throws ExceptionHandler {

		Connection conn = DBUtils.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(
					"INSERT INTO ReservationDetails(FirstName, LastName, ContactNumber, Date, Time, PartySize, Status, Visited, AssignedTableNumber, RestaurantId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? ,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, mr.getFirstName());
			ps.setString(2, mr.getLastName());
			ps.setString(3, mr.getContactNumber());
			ps.setString(4, mr.getDate());
			ps.setString(5, mr.getTime());
			ps.setInt(6, mr.getPartySize());
			ps.setString(7, mr.getStatus());
			ps.setString(8, "No");
			ps.setInt(9, mr.getAssignedTableNumber());
			ps.setInt(10, mr.getResturantId());

			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				mr.setConfirmationNumber(rs.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(), e.getCause());

		} finally {

			DBUtils.closeResouce(ps, rs, conn);

		}

	}

	public ReservationDetails getReservationDetails(int confirmationNumber) throws ExceptionHandler {

		ReservationDetails reservationMade = null;

		Connection conn = DBUtils.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM  ReservationDetails  WHERE ConfirmationNumber=?");
			ps.setInt(1, confirmationNumber);

			rs = ps.executeQuery();

			if (rs.next()) {

				reservationMade = new ReservationDetails();
				reservationMade.setConfirmationNumber(rs.getInt("ConfirmationNumber"));
				reservationMade.setFirstName(rs.getString("FirstName"));
				reservationMade.setLastName(rs.getString("LastName"));
				reservationMade.setContactNumber(rs.getString("ContactNumber"));
				reservationMade.setDate(rs.getString("Date"));
				reservationMade.setTime(rs.getString("Time"));
				reservationMade.setPartySize(rs.getInt("PartySize"));
				reservationMade.setStatus(rs.getString("Status"));
				reservationMade.setAssignedTableNumber(rs.getInt("AssignedTableNumber"));
				reservationMade.setResturantId(rs.getInt("RestaurantId"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(), e.getCause());
		} finally {

			DBUtils.closeResouce(ps, rs, conn);

		}
		return reservationMade;
	}

	public void updateReservation(ReservationDetails er, boolean updatecontact) throws ExceptionHandler {

		Connection conn = DBUtils.connect();
		PreparedStatement ps = null;
		try {
			if (updatecontact) {
				ps = conn.prepareStatement("UPDATE ReservationDetails SET ContactNumber= ? WHERE ConfirmationNumber=?");
				ps.setString(1, er.getContactNumber());
				ps.setInt(2, er.getConfirmationNumber());
			} else {
				ps = conn.prepareStatement(
						"UPDATE ReservationDetails SET Date= ?, Time =?, PartySize=?, ContactNumber=?, AssignedTableNumber=?, Status=? WHERE ConfirmationNumber=?");
				ps.setString(1, er.getDate());
				ps.setString(2, er.getTime());
				ps.setInt(3, er.getPartySize());
				ps.setString(4, er.getContactNumber());
				ps.setInt(5, er.getAssignedTableNumber());
				ps.setString(6, er.getStatus());
				ps.setInt(7, er.getConfirmationNumber());

			}
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(), e.getCause());
		} finally {

			DBUtils.closeResouce(ps, null, conn);

		}
	}

	public void cancelReservation(int confirmationNumber) throws ExceptionHandler {

		Connection conn = DBUtils.connect();
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement("UPDATE ReservationDetails SET Status ='Cancelled' WHERE ConfirmationNumber=?");
			ps.setInt(1, confirmationNumber);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(), e.getCause());
		} finally {

			DBUtils.closeResouce(ps, null, conn);
		}

	}

	public List<ReservationDetails> getAllReservationDetails(int restaurantId) throws ExceptionHandler {

		List<ReservationDetails> viewReservations = new ArrayList<ReservationDetails>();

		Connection conn = DBUtils.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM ReservationDetails WHERE RestaurantId =?");
			ps.setInt(1, restaurantId);
			rs = ps.executeQuery();

			while (rs.next()) {

				ReservationDetails vr = new ReservationDetails();
				vr.setConfirmationNumber(rs.getInt("ConfirmationNumber"));
				vr.setFirstName(rs.getString("FirstName"));
				vr.setLastName(rs.getString("LastName"));
				vr.setContactNumber(rs.getString("ContactNumber"));
				vr.setDate(rs.getString("Date"));
				vr.setTime(rs.getString("Time"));
				vr.setPartySize(rs.getInt("PartySize"));
				vr.setStatus(rs.getString("Status"));
				vr.setVisited(rs.getString("Visited"));
				vr.setAssignedTableNumber(rs.getInt("AssignedTableNumber"));

				viewReservations.add(vr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(), e.getCause());
		} finally {

			DBUtils.closeResouce(ps, null, conn);
		}

		return viewReservations;
	}

	public List<SeatingArrangements> getAllSeatingArrangements(int restaurantId) throws ExceptionHandler {

		List<SeatingArrangements> viewSeatingAreas = new ArrayList<SeatingArrangements>();
		Connection conn = DBUtils.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM SeatingArrangements WHERE RestaurantId=?");
			ps.setInt(1, restaurantId);
			rs = ps.executeQuery();

			while (rs.next()) {

				SeatingArrangements sa = new SeatingArrangements();
				sa.setTableNumber(rs.getInt("TableNumber"));
				sa.setSeatsAvailable(rs.getInt("SeatsAvailable"));

				viewSeatingAreas.add(sa);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(), e.getCause());
		} finally {

			DBUtils.closeResouce(ps, null, conn);
		}

		return viewSeatingAreas;
	}

	public List<SeatingArrangements> getListOfAvaiableTable(ReservationDetails rd) throws ExceptionHandler {

		List<SeatingArrangements> listofTables = new ArrayList<SeatingArrangements>();
		List<SeatingArrangements> AvailableTables = new ArrayList<SeatingArrangements>();

		Connection conn = DBUtils.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"SELECT TableNumber, SeatsAvailable FROM SeatingArrangements WHERE RestaurantId=? AND SeatsAvailable >= ?");
			ps.setInt(1, rd.getResturantId());
			ps.setInt(2, rd.getPartySize());

			rs = ps.executeQuery();
			while (rs.next()) {

				SeatingArrangements sa = new SeatingArrangements();
				sa.setSeatsAvailable(rs.getInt("SeatsAvailable"));
				sa.setTableNumber(rs.getInt("TableNumber"));

				listofTables.add(sa);

			}

			for (SeatingArrangements tableList : listofTables) {
				ps = conn.prepareStatement(
						"SELECT * FROM ReservationDetails WHERE RestaurantID=? AND Date=? AND Time BETWEEN SUBTIME(?, '01:00:00') AND ADDTIME(?, '01:00:00') AND AssignedTableNumber=? AND Status <> 'Cancel' ");
				ps.setInt(1, rd.getResturantId());
				ps.setString(2, rd.getDate());
				ps.setString(3, rd.getTime());
				ps.setString(4, rd.getTime());
				ps.setInt(5, tableList.getTableNumber());

				rs = ps.executeQuery();
				if (!rs.next()) {
					AvailableTables.add(tableList);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(), e.getCause());
		} finally {

			DBUtils.closeResouce(ps, null, conn);
		}
		return listofTables;
	}

	public void assignTable(ReservationDetails rd) throws ExceptionHandler {

		Connection conn = DBUtils.connect();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"UPDATE ReservationDetails SET AssignedTableNumber =?, Status = ? WHERE ConfirmationNumber=?");
			ps.setInt(1, rd.getAssignedTableNumber());
			ps.setString(2, "Confirmed");
			ps.setInt(3, rd.getConfirmationNumber());

			int row = ps.executeUpdate();
			if (row > 0) {
				rd.setStatus("Confirmed");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(), e.getCause());
		} finally {

			DBUtils.closeResouce(ps, null, conn);
		}

	}

	public RestaurantProfile getRestaurantProfileAndSettingDetails(int restaurantId) throws ExceptionHandler {

		RestaurantProfile rp = null;

		Connection conn = DBUtils.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM RestaurantProfile WHERE RestaurantId =?");
			ps.setInt(1, restaurantId);

			rs = ps.executeQuery();

			if (rs.next()) {

				rp = new RestaurantProfile();
				rp.setRestaurantId(rs.getInt("RestaurantId"));
				rp.setName(rs.getString("Name"));
				rp.setContactNumber(rs.getString("ContactNumber"));
				rp.setEmail(rs.getString("Email"));
				rp.setAddress(rs.getString("Address"));
				rp.setOperationalDays(rs.getString("OperationalDays"));
				rp.setOpeningHours(rs.getString("OpeningHours"));
				rp.setClosingHours(rs.getString("ClosingHours"));
				rp.setAutoAssign(rs.getString("AutoAssign"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(), e.getCause());
		} finally {

			DBUtils.closeResouce(ps, null, conn);
		}

		return rp;
	}

	public void updateProfileAndSettings(RestaurantProfile rp) throws ExceptionHandler {

		Connection conn = DBUtils.connect();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"UPDATE RestaurantProfile SET Name=?, ContactNumber= ?, Email =?, Address=?, OperationalDays=?, OpeningHours=? , ClosingHours=?, AutoAssign =? WHERE RestaurantId=?");
			ps.setString(1, rp.getName());
			ps.setString(2, rp.getContactNumber());
			ps.setString(3, rp.getEmail());
			ps.setString(4, rp.getAddress());
			ps.setString(5, rp.getOperationalDays());
			ps.setString(6, rp.getOpeningHours());
			ps.setString(7, rp.getClosingHours());
			ps.setString(8, rp.getAutoAssign());
			ps.setInt(9, rp.getRestaurantId());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(), e.getCause());
		} finally {

			DBUtils.closeResouce(ps, null, conn);
		}

	}

	public ReservationDetails getAllGuestReservationHistory(String contactNumber) throws ExceptionHandler {

		ReservationDetails rd = null;

		Connection conn = DBUtils.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM  ReservationDetails WHERE ContactNumber=?");
			ps.setString(1, contactNumber);

			rs = ps.executeQuery();

			if (rs.next()) {

				rd = new ReservationDetails();
				rd.setConfirmationNumber(rs.getInt("ConfirmationNumber"));
				rd.setFirstName(rs.getString("FirstName"));
				rd.setLastName(rs.getString("LastName"));
				rd.setContactNumber(rs.getString("ContactNumber"));
				rd.setDate(rs.getString("Date"));
				rd.setTime(rs.getString("Time"));
				rd.setPartySize(rs.getInt("PartySize"));
				rd.setStatus(rs.getString("Status"));
				rd.setAssignedTableNumber(rs.getInt("AssignedTableNumber"));
				rd.setResturantId(rs.getInt("RestaurantId"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExceptionHandler(e.getMessage(), e.getCause());
		} finally {

			DBUtils.closeResouce(ps, rs, conn);

		}
		return rd;
	}
}
