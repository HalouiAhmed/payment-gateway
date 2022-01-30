package effyis.partners.socle.shippingCost.dto.response;


public class ShippingCostResponseDTO {

	private Long id;

	private String name;

	private double cost;

	private boolean activated;

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
