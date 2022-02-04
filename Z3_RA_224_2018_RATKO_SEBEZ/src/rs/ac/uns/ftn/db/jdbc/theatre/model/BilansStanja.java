package rs.ac.uns.ftn.db.jdbc.theatre.model;

public class BilansStanja {
	private Long idbs;
	private String idl;
	private double saldo;
	private double dug;
	private double kamata;
	
	public BilansStanja() {
		super();
	}

	public BilansStanja(Long idbs, String idl, double saldo, double dug, double kamata) {
		super();
		this.idbs = idbs;
		this.idl = idl;
		this.saldo = saldo;
		this.dug = dug;
		this.kamata = kamata;
	}
	
	public Long getIdbs() {
		return idbs;
	}

	public void setIdbs(Long idbs) {
		this.idbs = idbs;
	}

	public String getIdl() {
		return idl;
	}

	public void setIdl(String idl) {
		this.idl = idl;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public double getDug() {
		return dug;
	}

	public void setDug(double dug) {
		this.dug = dug;
	}

	public double getKamata() {
		return kamata;
	}

	public void setKamata(double kamata) {
		this.kamata = kamata;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(dug);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((idbs == null) ? 0 : idbs.hashCode());
		result = prime * result + ((idl == null) ? 0 : idl.hashCode());
		temp = Double.doubleToLongBits(kamata);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(saldo);
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
		BilansStanja other = (BilansStanja) obj;
		if (Double.doubleToLongBits(dug) != Double.doubleToLongBits(other.dug))
			return false;
		if (idbs == null) {
			if (other.idbs != null)
				return false;
		} else if (!idbs.equals(other.idbs))
			return false;
		if (idl == null) {
			if (other.idl != null)
				return false;
		} else if (!idl.equals(other.idl))
			return false;
		if (Double.doubleToLongBits(kamata) != Double.doubleToLongBits(other.kamata))
			return false;
		if (Double.doubleToLongBits(saldo) != Double.doubleToLongBits(other.saldo))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("%-6d %-12.12s %-30.30s %-30.30s %-30.30s", idbs, idl, saldo, dug,
				kamata);
	}

	public static String getFormattedHeader() {
		return String.format("%-6s %-12.12s %-30.30s %-30.30s %-30.30s", "ID", "ID LICA", "SALDO", "DUG", "KAMATA");
	}
}
