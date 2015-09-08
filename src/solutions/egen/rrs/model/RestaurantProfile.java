package solutions.egen.rrs.model;

public class RestaurantProfile {

	private int restaurantId;
	private String Name;
	private String contactNumber;
	private String email;
	private String address;
	private String operationalDays;
	private String openingHours;
	private String closingHours;
	private String autoAssign;

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOperationalDays() {
		return operationalDays;
	}

	public void setOperationalDays(String operationalDays) {
		this.operationalDays = operationalDays;
	}

	public String getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}

	public String getClosingHours() {
		return closingHours;
	}

	public void setClosingHours(String closingHours) {
		this.closingHours = closingHours;
	}

	public String getAutoAssign() {
		return autoAssign;
	}

	public void setAutoAssign(String autoAssign) {
		this.autoAssign = autoAssign;
	}

}
