package crisis.application;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "transaction")
@XmlType()
public class CrisisItemTransaction {

	@XmlIDREF
	private CrisisItemGroup group;
	@XmlElement
	private Date date;
	@XmlElement
	private Money value;
	@XmlAttribute
	private String description;
	@XmlAttribute
	private String comment;

	public CrisisItemTransaction() {
	}

	public CrisisItemTransaction(Date date, String description, Money value,
			String comment) {

		this.date = date;
		this.description = description;
		this.value = value;
		this.comment = comment;
	}

	public String getComment() {

		return comment;
	}

	public Money getValue() {

		return value;
	}

	public String getDescription() {

		return description;
	}

	public Date getDate() {

		return date;
	}
}
