package hk.com.novare.tempoplus.bmnmanager.holiday;

import org.springframework.stereotype.Component;

@Component
public class HolidayPojo {
	private String date;
	private String day;
	private String name;
	private String code;
	private String procNo;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProcNo() {
		return procNo;
	}

	public void setProcNo(String procNo) {
		this.procNo = procNo;
	}

}
