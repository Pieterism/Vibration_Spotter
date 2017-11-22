
package model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity //comment
@Table(name="Persoon")
@NamedQuery(name="Persoon.findAll", query="SELECT p FROM Persoon p")
//@SecondaryTable(name = "Person_Group", pkJoinColumns=@PrimaryKeyJoinColumn(name="login", referencedColumnName="login"))
public class Persoon implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable=false, length=16)
	private int id;
	
	@Column(name="gebruikersnaam", nullable=false)
	private String gebruikersnaam;
	
	@Column(name="paswoord", nullable=false)
	private String paswoord;

	
	
	
	public Persoon(String gebruikersnaam, String paswoord) {
		this.gebruikersnaam = gebruikersnaam;
		this.paswoord = paswoord;
	}
	
	

	public int getId() {
		return id;
	}

	public String getGebruikersnaam() {
		return gebruikersnaam;
	}

	public void setGebruikersnaam(String gebruikersnaam) {
		this.gebruikersnaam = gebruikersnaam;
	}

	public String getPaswoord() {
		return paswoord;
	}

	public void setPaswoord(String paswoord) {
		this.paswoord = paswoord;
	}

	
	
	
	

	
	
	
}
	

	
	//@ManyToMany
//	@OneToOne(cascade=CascadeType.ALL)
//	@JoinTable(
//			name="Person_Group",
//			joinColumns={@JoinColumn(name="login", referencedColumnName="login")},
//			inverseJoinColumns={@JoinColumn(name="idGroup", referencedColumnName="idGroup")})


	/*public Person(Person person)
	{
		this.name = person.name;
		this.login = person.login;
		this.hPassword = person.hPassword;
		this.address = person.address;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Person(String name) {
		super();
		this.name = name;
	}
	
	public Person(){
		super();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String gethPassword() {
		return hPassword;
	}

	public void sethPassword(String hPassword) {
		this.hPassword = hPassword;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}*/