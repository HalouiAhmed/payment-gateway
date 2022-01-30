package effyis.partners.socle.taxeRate.dto;

public class TaxeRateDTO {

	private String name;

	private double cost;

	private boolean activated;

	private boolean incluse;
	private String country;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public boolean isIncluse() {
		return incluse;
	}

	public void setIncluse(boolean incluse) {
		this.incluse = incluse;
	}

	@Override
	public String toString() {
		return "ShippingCostDTO [name=" + name + ", cost=" + cost + ", activated=" + activated + "]";
	}

}
