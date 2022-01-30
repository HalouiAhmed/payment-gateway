package effyis.partners.socle.taxeRate.dto.response;


public class TaxeRateResponseDTO {

	private Long id;

	private String name;

	private double cost;

	private boolean activated;
	private String country;
	private boolean incluse;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean isIncluse() {
		return incluse;
	}

	public void setIncluse(boolean incluse) {
		this.incluse = incluse;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	@Override
	public String toString() {
		return "ShippingCostResponseDTO [id=" + id + ", name=" + name + ", cost=" + cost + ", activated=" + activated
				+ "]";
	}

}
