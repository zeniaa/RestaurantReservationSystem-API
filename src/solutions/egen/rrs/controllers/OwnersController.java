package solutions.egen.rrs.controllers;

import java.util.List;

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
import solutions.egen.rrs.dao.AuthenticateDAO;
import solutions.egen.rrs.dao.ReservationDAO;
import solutions.egen.rrs.exception.ExceptionHandler;
import solutions.egen.rrs.logics.AuthenticateLogic;
import solutions.egen.rrs.logics.ReservationLogic;
import solutions.egen.rrs.model.OwnersDetails;
import solutions.egen.rrs.model.ReservationDetails;
import solutions.egen.rrs.model.RestaurantProfile;
import solutions.egen.rrs.model.SeatingArrangements;

@Path("/owners")
@Api(tags = { "/owners" })

public class OwnersController {

	@GET
	@Path("/viewReservation/{ConfirmationNumber}/{Email}/{Token}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "View Restuarnt Reservation by Confirmation Number", notes = "View the Restuarant Reservation")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 401, message = "UnAuthorized"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internet Server Error"), })
	public ReservationDetails viewReservation(@PathParam("ConfirmationNumber") int confirmationNumber,
			@PathParam("Email") String email, @PathParam("Token") String token) {
		ReservationDetails rd = null;
		try {

			if (AuthenticateLogic.ValidateRequest(email, token)) {
				ReservationDAO dao = new ReservationDAO();
				rd = dao.getReservationDetails(confirmationNumber);
				if (rd == null) {
					throw new WebApplicationException(Status.NOT_FOUND);
				}
			} else {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}

		} catch (ExceptionHandler e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);

		}
		return rd;
	}

	@GET
	@Path("/viewAllReservation/{RestaurantId}/{Email}/{Token}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "View All Restuarnt Reservation", notes = "View the Restuarant Reservation in the database")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 401, message = "UnAuthorized"),
			@ApiResponse(code = 500, message = "Internet Server Error"), })
	public List<ReservationDetails> viewAllReservation(@PathParam("RestaurantId") int restaurantId,
			@PathParam("Email") String email, @PathParam("Token") String token) {
		List<ReservationDetails> viewReservations = null;
		try {

			if (AuthenticateLogic.ValidateRequest(email, token)) {
				ReservationDAO dao = new ReservationDAO();
				viewReservations = dao.getAllReservationDetails(restaurantId);
			} else {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}

		} catch (ExceptionHandler e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}

		return viewReservations;

	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Onwers Login", notes = "Onwers Login")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internet Server Error") })
	public OwnersDetails logIn(OwnersDetails login) {

		AuthenticateDAO authenticate = new AuthenticateDAO();

		try {
			AuthenticateLogic logic = new AuthenticateLogic();
			boolean flag = authenticate.userAuthentication(login);
			if (flag) {
				logic.maintainingSession(login);
			}
		} catch (ExceptionHandler e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return login;

	}

	@POST
	@Path("/makeReservation/{Email}/{Token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Make Restuarnt Reservation", notes = "Make a Restaurant Reservation")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 401, message = "UnAuthorized"),
			@ApiResponse(code = 500, message = "Internet Server Error") })
	public ReservationDetails makeReservation(ReservationDetails mr, @PathParam("Email") String email,
			@PathParam("Token") String token) {
		ReservationLogic rl = new ReservationLogic();
		try {

			if (AuthenticateLogic.ValidateRequest(email, token)) {
				rl.makeReservation(mr);
			} else {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}

		} catch (ExceptionHandler e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}

		return mr;
	}

	@GET
	@Path("/viewAllSeatingArea/{RestaurantId}/{Email}/{Token}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "View All Seating Arrangememts", notes = "View the Seating Arrangememts in the database")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 401, message = "UnAuthorized"),
			@ApiResponse(code = 500, message = "Internet Server Error"), })

	public List<SeatingArrangements> viewAllSeatingAreas(@PathParam("RestaurantId") int restaurantId,
			@PathParam("Email") String email, @PathParam("Token") String token) {
		List<SeatingArrangements> viewSeatingAreas = null;
		try {
			if (AuthenticateLogic.ValidateRequest(email, token)) {
				ReservationDAO dao = new ReservationDAO();
				viewSeatingAreas = dao.getAllSeatingArrangements(restaurantId);
			} else {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		} catch (ExceptionHandler e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}

		return viewSeatingAreas;

	}

	@PUT
	@Path("/editReservation/{updatecontact}/{Email}/{Token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Edit Restuarnt  Reservation", notes = "Edit Restaurant Reservation")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 401, message = "UnAuthorized"),
			@ApiResponse(code = 500, message = "Internet Server Error") })

	public ReservationDetails editReservation(ReservationDetails er, @PathParam("updatecontact") boolean updatecontact,
			@PathParam("Email") String email, @PathParam("Token") String token) {

		ReservationLogic rl = new ReservationLogic();
		try {
			if (AuthenticateLogic.ValidateRequest(email, token)) {
				rl.updateReservation(er, updatecontact);

			} else {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		} catch (ExceptionHandler e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return er;
	}

	@PUT
	@Path("/SearchForAvailbleTables/{Email}/{Token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Search for Avaiable Tables", notes = "Searching for List of Tables")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 401, message = "UnAuthorized"),
			@ApiResponse(code = 500, message = "Internet Server Error") })

	public List<SeatingArrangements> AssigningTable(ReservationDetails rd, @PathParam("Email") String email,
			@PathParam("Token") String token) {

		List<SeatingArrangements> availableTableNumber = null;
		try {
			if (AuthenticateLogic.ValidateRequest(email, token)) {
				ReservationDAO dao = new ReservationDAO();
				availableTableNumber = dao.getListOfAvaiableTable(rd);
			} else {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		} catch (ExceptionHandler e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return availableTableNumber;

	}

	@PUT
	@Path("/AssigningTable/{Email}/{Token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Assigning table for the Reservation", notes = "Assign the table for the reservation")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 401, message = "UnAuthorized"),
			@ApiResponse(code = 500, message = "Internet Server Error") })

	public ReservationDetails AssignTable(ReservationDetails rd, @PathParam("Email") String email,
			@PathParam("Token") String token) {

		try {

			ReservationLogic rl = new ReservationLogic();
			if (AuthenticateLogic.ValidateRequest(email, token)) {
				rl.assigningTable(rd);

			} else {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}

		} catch (ExceptionHandler e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return rd;
	}

	@GET
	@Path("/viewProfileAndSettings/{RestaurantId}/{Email}/{Token}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "View Profile And Setting", notes = "View Profile And Setting")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 401, message = "UnAuthorized"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internet Server Error") })
	public RestaurantProfile viewProfileAndSetiings(@PathParam("RestaurantId") int restaurantId,
			@PathParam("Email") String email, @PathParam("Token") String token) {

		RestaurantProfile rp = null;
		try {

			if (AuthenticateLogic.ValidateRequest(email, token)) {
				ReservationDAO dao = new ReservationDAO();
				rp = dao.getRestaurantProfileAndSettingDetails(restaurantId);

			} else {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		} catch (ExceptionHandler e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return rp;

	}

	@PUT
	@Path("/updateProfileAndSettings/{Email}/{Token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update Profile And Setting", notes = "Update Profile And Setting")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 401, message = "UnAuthorized"),
			@ApiResponse(code = 500, message = "Internet Server Error") })
	public RestaurantProfile updateProfileAndSettings(RestaurantProfile rp, @PathParam("Email") String email,
			@PathParam("Token") String token) {

		try {
			if (AuthenticateLogic.ValidateRequest(email, token)) {
				ReservationDAO dao = new ReservationDAO();
				dao.updateProfileAndSettings(rp);

			} else {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		} catch (ExceptionHandler e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);

		}
		return rp;

	}

	@GET
	@Path("/viewGuestReservation/{contactNo}/{Email}/{Token}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "View Guest Reservation History", notes = "View Guest Reservation History")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 401, message = "UnAuthorized"),
			@ApiResponse(code = 500, message = "Internet Server Error"), })
	public ReservationDetails viewGuestReservation(@PathParam("contactNo") String contactNumber,
			@PathParam("Email") String email, @PathParam("Token") String token) {
		ReservationDetails rd = null;
		try {
			if (AuthenticateLogic.ValidateRequest(email, token)) {
				ReservationDAO dao = new ReservationDAO();
				rd = dao.getAllGuestReservationHistory(contactNumber);

			} else {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		} catch (ExceptionHandler e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		return rd;
	}
}
