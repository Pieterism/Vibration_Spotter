package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "leerkracht")
@NamedQuery(name = "leerkracht.findAll", query = "SELECT p FROM leerkracht p")

public class Leerkracht implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idLeerkracht", nullable = false, unique = true)
	private int idLeerkracht;

	@Column(name = "schoolnaam", nullable = false)
	private String schoolnaam;

	@Column(name = "schooladres", nullable = false)
	private String schooladres;

}
