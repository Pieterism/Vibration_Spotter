package project.rest.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Meting {
	private long x, y, z, tijd;
	private String id; 

	public Meting(String id, long x, long y, long z, long tijd) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.tijd = tijd;
	}

	public Meting() {
		super();
	}
	
	public long getX() {
		return x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public long getY() {
		return y;
	}

	public void setY(long y) {
		this.y = y;
	}

	public long getZ() {
		return z;
	}

	public void setZ(long z) {
		this.z = z;
	}

	public long getTijd() {
		return tijd;
	}

	public void setTijd(long tijd) {
		this.tijd = tijd;
	}

	@Override
	public String toString() {
		return "Meting [x=" + x + ", y=" + y + ", z=" + z + ", tijd=" + tijd + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	


}
