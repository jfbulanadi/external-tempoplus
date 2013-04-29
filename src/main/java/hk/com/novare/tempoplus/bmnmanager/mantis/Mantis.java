package hk.com.novare.tempoplus.bmnmanager.mantis;

public class Mantis {

	private int hours;
	private int minutes;
	private int employeeId;
	private int ticketId;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	private String dateSubmitted;
	private String category;
	private String status;

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public void setDateSubmitted(String dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public int getHours() {
		return hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public String getDateSubmitted() {
		return dateSubmitted;
	}

	public String getCategory() {
		return category;
	}

	public String getStatus() {
		return status;
	}

	public float getDuration() {
		return getHours() + getMinutes() / 60;
	}

}
