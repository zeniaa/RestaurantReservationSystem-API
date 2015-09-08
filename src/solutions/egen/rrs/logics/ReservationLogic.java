package solutions.egen.rrs.logics;

import java.util.ArrayList;

import solutions.egen.rrs.dao.ReservationDAO;
import solutions.egen.rrs.exception.ExceptionHandler;
import solutions.egen.rrs.model.ReservationDetails;

public class ReservationLogic {

	public ReservationDetails makeReservation(ReservationDetails mr) throws ExceptionHandler {

		if (autoAssign(mr)) {

			if (AvailabilityofTable(mr)) {

				ReservationDAO dao = new ReservationDAO();
				mr.setStatus("Confirmed");
				dao.createReservation(mr);
			} else {
				mr.setStatus("Waiting");
			}
		} else {
			mr.setStatus("Waiting");
		}
		return mr;
	}

	private boolean AvailabilityofTable(ReservationDetails mr) throws ExceptionHandler {

		boolean flag = false;

		ArrayList<Integer> tables = new ArrayList<Integer>();
		ReservationDAO dao = new ReservationDAO();
		tables = dao.AvailabilityofTable(mr);
		if (tables.size() != 0) {
			mr = dao.searchForAvailableTables(mr, tables);
			if (mr.getAssignedTableNumber() != 0) {
				flag = true;
			}

		}

		return flag;
	}

	private boolean autoAssign(ReservationDetails mr) throws ExceptionHandler {
		boolean flag = false;
		ReservationDAO dao = new ReservationDAO();
		flag = dao.autoAssign(mr);
		return flag;

	}

	public void updateReservation(ReservationDetails er, boolean updatecontact) throws ExceptionHandler {
		ReservationDAO dao = new ReservationDAO();
		if (updatecontact) {
			dao.updateReservation(er, updatecontact);
		} else {
			if (autoAssign(er)) {

				if (AvailabilityofTable(er)) {

					er.setStatus("Confirmed");
					dao.updateReservation(er, updatecontact);

				} else {
					er.setStatus("Waiting");
				}
			} else {
				er.setStatus("Waiting");
			}
		}
	}

	public void cancelReservation(int confirmationNumber) throws ExceptionHandler {

		ReservationDAO dao = new ReservationDAO();
		dao.cancelReservation(confirmationNumber);

	}

	public void confirmReservationCreate(ReservationDetails mr) throws ExceptionHandler {
		
		ReservationDAO dao = new ReservationDAO();
		dao.createReservation(mr);
		
	}

	public void confirmReservationUpdate(ReservationDetails mr) throws ExceptionHandler {
		ReservationDAO dao = new ReservationDAO();
		dao.updateReservation(mr, false);
	}

	public void assigningTable(ReservationDetails rd) throws ExceptionHandler{
		ReservationDAO dao = new ReservationDAO();
		dao.assignTable(rd);
		
	}

}
