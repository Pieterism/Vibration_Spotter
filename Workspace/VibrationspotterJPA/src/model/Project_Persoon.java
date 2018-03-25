package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

//@Entity
//@Table(name = "Project_Persoon")
public class Project_Persoon {
	  @Id
	  private long Persoon_idPersoon;
	  @Id
	  private long Project_idProject;


}
