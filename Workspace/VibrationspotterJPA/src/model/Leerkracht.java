package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "leerkracht")
@NamedQuery(name = "leerkracht.findAll", query = "SELECT p FROM Leerkracht p")
@PrimaryKeyJoinColumn(referencedColumnName = "idPersoon")
@DiscriminatorValue("Leerkracht")
public class Leerkracht extends Persoon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "schoolnaam", nullable = false)
	private String schoolnaam;

	@Column(name = "schooladres", nullable = false)
	private String schooladres;

	public Leerkracht() {
		super();
	}

	public Leerkracht(String schoolnaam, String schooladres) {
		super();
		this.schoolnaam = schoolnaam;
		this.schooladres = schooladres;
	}

	public String getSchoolnaam() {
		return schoolnaam;
	}

	public void setSchoolnaam(String schoolnaam) {
		this.schoolnaam = schoolnaam;
	}

	public String getSchooladres() {
		return schooladres;
	}

	public void setSchooladres(String schooladres) {
		this.schooladres = schooladres;
	}

}
