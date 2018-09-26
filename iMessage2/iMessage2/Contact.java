package iMessage2;

import java.util.ArrayList;
import java.util.EnumSet;

public class Contact {
	private boolean selected;
	private String name;
	private String number;
	private String email;
	private Carrier carrier;

	public enum Carrier {
		ATT("@mms.att.net"), Sprint("@pm.spring.com"), Verizon("@vzwpix.com"), TMobile("@tmomail.net"), USCellular(
				"@mms.uscc.net");
		private String site;

		Carrier(String site) {
			this.site = site;
		}

		public static Carrier getCarrier(String carrier) {
			ArrayList<Carrier> carriers = Carrier.getCarriers();
			for (Carrier c : carriers) {
				if (c.toString().equals(carrier)) {
					return c;
				}
			}
			return null;
		}

		public String getSite() {
			return site;
		}

		public static ArrayList<Carrier> getCarriers() {
			return new ArrayList<Carrier>(EnumSet.allOf(Carrier.class));
		}

		public static ArrayList<String> getCarriersString() {
			ArrayList<Carrier> carriers = Carrier.getCarriers();
			ArrayList<String> total = new ArrayList<>();
			total.add("Carrier");
			for (Carrier c : carriers) {
				total.add(c.toString());
			}
			return total;
		}
	}

	public Contact(String name, String number, Carrier carrier) {
		this.name = name;
		this.number = number;
		this.carrier = carrier;
		this.selected = false;
	}

	public Contact(String name, String email) {
		this.name = name;
		this.email = email;
		this.selected = false;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getContact() {
		return email == null ? number + carrier.getSite() : email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Carrier getCarrier() {
		return carrier;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}

	public String toString() {
		return name + " " + number + " " + carrier;
	}
}
