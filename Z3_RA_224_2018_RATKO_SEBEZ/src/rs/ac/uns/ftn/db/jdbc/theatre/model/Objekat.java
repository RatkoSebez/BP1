package rs.ac.uns.ftn.db.jdbc.theatre.model;

public class Objekat {
	private Long ido;
	private String idl;
	private Long idvo;
	private double povrsina;
	private String adresa;
	private double vrednost;
	
	public Objekat() {}

	public Objekat(Long ido, String idl, Long idvo, double povrsina, String adresa, double vrednost) {
		super();
		this.ido = ido;
		this.idl = idl;
		this.idvo = idvo;
		this.povrsina = povrsina;
		this.adresa = adresa;
		this.vrednost = vrednost;
	}

	public Long getIdo() {
		return ido;
	}

	public void setIdo(Long ido) {
		this.ido = ido;
	}

	public String getIdl() {
		return idl;
	}

	public void setIdl(String idl) {
		this.idl = idl;
	}

	public Long getIdvo() {
		return idvo;
	}

	public void setIdvo(Long idvo) {
		this.idvo = idvo;
	}

	public double getPovrsina() {
		return povrsina;
	}

	public void setPovrsina(double povrsina) {
		this.povrsina = povrsina;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public double getVrednost() {
		return vrednost;
	}

	public void setVrednost(double vrednost) {
		this.vrednost = vrednost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresa == null) ? 0 : adresa.hashCode());
		result = prime * result + ((idl == null) ? 0 : idl.hashCode());
		result = prime * result + ((ido == null) ? 0 : ido.hashCode());
		result = prime * result + ((idvo == null) ? 0 : idvo.hashCode());
		long temp;
		temp = Double.doubleToLongBits(povrsina);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(vrednost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Objekat other = (Objekat) obj;
		if (adresa == null) {
			if (other.adresa != null)
				return false;
		} else if (!adresa.equals(other.adresa))
			return false;
		if (idl == null) {
			if (other.idl != null)
				return false;
		} else if (!idl.equals(other.idl))
			return false;
		if (ido == null) {
			if (other.ido != null)
				return false;
		} else if (!ido.equals(other.ido))
			return false;
		if (idvo == null) {
			if (other.idvo != null)
				return false;
		} else if (!idvo.equals(other.idvo))
			return false;
		if (Double.doubleToLongBits(povrsina) != Double.doubleToLongBits(other.povrsina))
			return false;
		if (Double.doubleToLongBits(vrednost) != Double.doubleToLongBits(other.vrednost))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("%-6s %-12.12s %-30.30s %-30.30s %-30.30s %-30.30s", ido, idl, idvo, povrsina, adresa, vrednost);
	}

	public static String getFormattedHeader() {
		return String.format("%-6s %-12.12s %-30.30s %-30.30s %-30.30s %-30.30s", "ID OBJEKTA", "ID LICA", "ID VRSTE OBJKTA", "POVRSINA", "ADRESA", "VREDNOST");
	}
}
