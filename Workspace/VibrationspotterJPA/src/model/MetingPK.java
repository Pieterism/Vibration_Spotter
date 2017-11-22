package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the meting database table.
 * 
 */
@Embeddable
public class MetingPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idMeting;

	@Column(insertable=false, updatable=false)
	private int persoon_idPersoon;

	public MetingPK() {
	}
	public int getIdMeting() {
		return this.idMeting;
	}
	public void setIdMeting(int idMeting) {
		this.idMeting = idMeting;
	}
	public int getPersoon_idPersoon() {
		return this.persoon_idPersoon;
	}
	public void setPersoon_idPersoon(int persoon_idPersoon) {
		this.persoon_idPersoon = persoon_idPersoon;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MetingPK)) {
			return false;
		}
		MetingPK castOther = (MetingPK)other;
		return 
			(this.idMeting == castOther.idMeting)
			&& (this.persoon_idPersoon == castOther.persoon_idPersoon);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idMeting;
		hash = hash * prime + this.persoon_idPersoon;
		
		return hash;
	}
}