package crisis.application;

import java.util.List;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType
public class Money {

	@XmlTransient
	private final String COIN = "€";
	@XmlValue
	private double value;

	public Money() {

	}

	public Money(double value) {

		this.value = value;
	}

	public static Money sum(Money a, Money b) {

		return new Money(a.value + b.value);
	}

	public static Money sum(List<Money> moneyList) {

		double value = 0.0;
		for (Money money : moneyList) {
			value += money.value;
		}
		return new Money(value);
	}

	public String toString() {

		return COIN + value;
	}
}
