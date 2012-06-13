package crisis.application;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Date {

	@XmlAttribute
	private int year;
	@XmlAttribute
	private int month;
	@XmlAttribute
	private int day;

	public Date() {

	}

	public Date(int year, int month, int day) {

		this.year = year;
		this.month = month;
		this.day = day;
	}

	public String toString() {

		StringBuilder str = new StringBuilder();
		str.append(year);
		str.append("/");
		if (month < 10)
			str.append("0");
		str.append(month);
		str.append("/");
		if (day < 10)
			str.append("0");
		str.append(day);
		return str.toString();
	}
}
