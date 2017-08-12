package items.model;

public class TaxCategory {
	private int id;
	private double vat;	// VAT - value added tax
	private String text;
	
	// ArtikliFormaDodaj
	// ArtikliDetalji
	public TaxCategory(int id, double vat, String text) {
		this.id = id;
		this.vat = vat;
		this.text = text;
	}

	public double getVat() {
		return vat;
	}

	public int getId() {
		return id;
	}
	
	public String toString() {
		return text;
	}

}
