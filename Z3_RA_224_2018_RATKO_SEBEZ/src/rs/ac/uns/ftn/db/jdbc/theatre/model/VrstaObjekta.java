package rs.ac.uns.ftn.db.jdbc.theatre.model;

public class VrstaObjekta {
	private Long idvo;
	private String naziv;
	
	public VrstaObjekta() {}

	public VrstaObjekta(Long idvo, String naziv) {
		super();
		this.idvo = idvo;
		this.naziv = naziv;
	}

	public Long getIdvo() {
		return idvo;
	}

	public void setIdvo(Long idvo) {
		this.idvo = idvo;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idvo == null) ? 0 : idvo.hashCode());
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
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
		VrstaObjekta other = (VrstaObjekta) obj;
		if (idvo == null) {
			if (other.idvo != null)
				return false;
		} else if (!idvo.equals(other.idvo))
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("%-15s", naziv);
	}
}
