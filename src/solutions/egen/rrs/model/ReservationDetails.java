package solutions.egen.rrs.model;

public class ReservationDetails {

	private int confirmationNumber;
	private String firstName;
	private String lastName;
	private String contactNumber;
	private String date;
	private String time;
	private int partySize;
	private String status;
	private String visited;
	private int assignedTableNumber;
	private int resturantId;

	public int getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(int confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getPartySize() {
		return partySize;
	}

	public void setPartySize(int partySize) {
		this.partySize = partySize;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVisited() {
		return visited;
	}

	public void setVisited(String visited) {
		this.visited = visited;
	}

	public int getAssignedTableNumber() {
		return assignedTableNumber;
	}

	public void setAssignedTableNumber(int assignedTableNumber) {
		this.assignedTableNumber = assignedTableNumber;
	}

	public int getResturantId() {
		return resturantId;
	}

	public void setResturantId(int resturantId) {
		this.resturantId = resturantId;
	}

}
