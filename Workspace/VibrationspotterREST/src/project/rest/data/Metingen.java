package project.rest.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso(Meting.class)
public class Metingen extends ArrayList<Meting>{
	
	public Metingen(){
		super();
	}
	
	public Metingen(Collection<? extends Meting> c){
		super(c);
	}
	
	@XmlElement(name = "meting")
	public List<Meting> getMeting(){
		return this;
	}
	
	public void setMetingen(List<Meting> metingen){
		this.addAll(metingen);
	}
}
