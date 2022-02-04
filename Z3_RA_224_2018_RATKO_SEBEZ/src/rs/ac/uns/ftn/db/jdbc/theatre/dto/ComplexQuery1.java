package rs.ac.uns.ftn.db.jdbc.theatre.dto;

public class ComplexQuery1 {
	private Long ido;
	private int cntVo;
	private double sumPovrsine;
	private double sumVrednosti;
	private double avgCenaKvadrata;
	
	public ComplexQuery1() {}

	public ComplexQuery1(Long ido, int cntVo, double sumPovrsine, double sumVrednosti, double avgCenaKvadrata) {
		super();
		this.ido = ido;
		this.cntVo = cntVo;
		this.sumPovrsine = sumPovrsine;
		this.sumVrednosti = sumVrednosti;
		this.avgCenaKvadrata = avgCenaKvadrata;
	}

	public Long getIdo() {
		return ido;
	}

	public void setIdo(Long ido) {
		this.ido = ido;
	}

	public int getCntVo() {
		return cntVo;
	}

	public void setCntVo(int cntVo) {
		this.cntVo = cntVo;
	}

	public double getSumPovrsine() {
		return sumPovrsine;
	}

	public void setSumPovrsine(double sumPovrsine) {
		this.sumPovrsine = sumPovrsine;
	}

	public double getSumVrednosti() {
		return sumVrednosti;
	}

	public void setSumVrednosti(double sumVrednosti) {
		this.sumVrednosti = sumVrednosti;
	}

	public double getAvgCenaKvadrata() {
		return avgCenaKvadrata;
	}

	public void setAvgCenaKvadrata(double avgCenaKvadrata) {
		this.avgCenaKvadrata = avgCenaKvadrata;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(avgCenaKvadrata);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + cntVo;
		result = prime * result + ((ido == null) ? 0 : ido.hashCode());
		temp = Double.doubleToLongBits(sumPovrsine);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(sumVrednosti);
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
		ComplexQuery1 other = (ComplexQuery1) obj;
		if (Double.doubleToLongBits(avgCenaKvadrata) != Double.doubleToLongBits(other.avgCenaKvadrata))
			return false;
		if (cntVo != other.cntVo)
			return false;
		if (ido == null) {
			if (other.ido != null)
				return false;
		} else if (!ido.equals(other.ido))
			return false;
		if (Double.doubleToLongBits(sumPovrsine) != Double.doubleToLongBits(other.sumPovrsine))
			return false;
		if (Double.doubleToLongBits(sumVrednosti) != Double.doubleToLongBits(other.sumVrednosti))
			return false;
		return true;
	}
	
	public String toString(String nazivObjekta) {
		return String.format("%-25s %-25.25s %-30.30s %-30.30s %-30.30s", nazivObjekta, cntVo, sumPovrsine, sumVrednosti, avgCenaKvadrata);
	}

	public static String getFormattedHeader() {
		return String.format("%-25s %-25.25s %-30.30s %-30.30s %-30.30s", "NAZIV VRSTE OBJEKTA", "BROJ ZADUZENIH LICA", "UKUPNA POVRSINA", "UKUPNA VREDNOST", "PROSECNA CENA KVADRATA");
	}
}
