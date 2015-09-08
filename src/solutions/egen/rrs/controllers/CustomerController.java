package solutions.egen.rrs.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import solutions.egen.rrs.dao.ReservationDAO;
import solutions.egen.rrs.exception.ExceptionHandler;
import solutions.egen.rrs.logics.ReservationLogic;
import solutions.egen.rrs.model.ReservationDetails;

@Path("/customers")
@Api(tags = { "/customers" })
public class CustomerController {

	@POST
	@Path("/makeReservation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Make Restuarnt Reservation", notes = "Make a Restaurant Reservation")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internet Server Error") })
	public ReservationDetails makeReservation(ReservationDetails mr) {

		ReservationLogic rl = new ReservationLogic();
		try {
			rl.makeReservation(mr);

		} catch (ExceptionHandler e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}

		return mr;
	}

	@GET
	@Path("/viewReservation/{ConfirmationNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "View Restuarnt Reservation", notes = "View the Restuarant Reservation")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internet Server Error"), })
	public ReservationDetails viewReservation(@PathParam("ConfirmationNumber") int confirmationNumber) {

		ReservationDetails rd;
		try {

			ReservationDAO dao = new ReservationDAO();
			rd = dao.getReservationDetails(confirmationNumber);
			if (rd == null) {
				throw new WebApplicationException(Status.NOT_FOUND);
			}

		} catch (ExceptionHandler e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);

		}
		return rd;
	}

	@PUT
	@Path("/editReservation/{updatecontact}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Edit Restuarnt  Reservation", notes = "Edit Restaurant Reservation")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internet Server Error") })
	public ReservationDetails editReservation(ReservationDetails er,
			@PathParam("updatecontact") boolean updatecontact) {

		ReservationLogic rl = new ReservationLogic();
		try {

			rl.updateReservation(er, updatecontact);
		} catch (ExceptionHandler e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}

		return er;
	}

	@PUT
	@Path("/cancelReservation/{ConfirmationNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Cancel Restuarnt Reservation", notes = "Cancel Restuarant Reservation in the database")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internet Server Error"), })
	public ReservationDetails cancelReservation(@PathParam("ConfirmationNumber") int confirmationNumber) {

		ReservationLogic rl = new ReservationLogic();
		try {
			rl.cancelReservation(confirmationNumber);

		} catch (ExceptionHandler e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}

		return null;
	}

	@POST
	@Path("/confirmReservationCreate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Confirm Restuarnt Reservation - While Creating", notes = "Confirm Restaurant Reservation")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internet Server Error") })
	public ReservationDetails confirmReservationCreate(ReservationDetails mr) {

		ReservationLogic rl = new ReservationLogic();
		try {
			rl.confirmReservationCreate(mr);

		} catch (ExceptionHandler e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return mr;

	}

	@PUT
	@Path("/confirmReservationUpdate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Confirm Restuarnt Reservation - While Updating", notes = "Confirm Restaurant Reservation")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internet Server Error") })
	public ReservationDetails confirmReservationUpdate(ReservationDetails mr) {
		ReservationLogic rl = new ReservationLogic();
		try {
			rl.confirmReservationUpdate(mr);
		} catch (ExceptionHandler e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return mr;

	}
}
