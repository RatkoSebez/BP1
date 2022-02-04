package rs.ac.uns.ftn.db.jdbc.theatre.model;

public class Lice {
	private String idl;
	private String imel;
	private String przl;
	private String vrstal;
	private double mes_prihodi;
	
	public Lice() {}

	public Lice(String idl, String imel, String przl, String vrstal, double mes_prihodi) {
		super();
		this.idl = idl;
		this.imel = imel;
		this.przl = przl;
		this.vrstal = vrstal;
		this.mes_prihodi = mes_prihodi;
	}

	public String getIdl() {
		return idl;
	}

	public void setIdl(String idl) {
		this.idl = idl;
	}

	public String getImel() {
		return imel;
	}

	public void setImel(String imel) {
		this.imel = imel;
	}

	public String getPrzl() {
		return przl;
	}

	public void setPrzl(String przl) {
		this.przl = przl;
	}

	public String getVrstal() {
		return vrstal;
	}

	public void setVrstal(String vrstal) {
		this.vrstal = vrstal;
	}

	public double getMes_prihodi() {
		return mes_prihodi;
	}

	public void setMes_prihodi(double mes_prihodi) {
		this.mes_prihodi = mes_prihodi;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idl == null) ? 0 : idl.hashCode());
		result = prime * result + ((imel == null) ? 0 : imel.hashCode());
		long temp;
		temp = Double.doubleToLongBits(mes_prihodi);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((przl == null) ? 0 : przl.hashCode());
		result = prime * result + ((vrstal == null) ? 0 : vrstal.hashCode());
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
		Lice other = (Lice) obj;
		if (idl == null) {
			if (other.idl != null)
				return false;
		} else if (!idl.equals(other.idl))
			return false;
		if (imel == null) {
			if (other.imel != null)
				return false;
		} else if (!imel.equals(other.imel))
			return false;
		if (Double.doubleToLongBits(mes_prihodi) != Double.doubleToLongBits(other.mes_prihodi))
			return false;
		if (przl == null) {
			if (other.przl != null)
				return false;
		} else if (!przl.equals(other.przl))
			return false;
		if (vrstal == null) {
			if (other.vrstal != null)
				return false;
		} else if (!vrstal.equals(other.vrstal))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("%-15s %-12.12s %-30.30s %-30.30s %-30.30s", idl, imel, przl, vrstal,
				mes_prihodi);
	}

	public static String getFormattedHeader() {
		return String.format("%-15s %-12.12s %-30.30s %-30.30s %-30.30s", "ID LICA", "IME", "PREZIME", "VRSTA LICA", "MESECNI PRIHODI");
	}
}
