package by.gsu.epamlab.beans;

public class User implements Comparable<User> {
	private String login;
	private int id;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public User(String login, int id) {
		super();
		this.login = login;
		this.id = id;
	}
	
	public User(String login) {
		super();
		this.login = login;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (!login.equals(other.login))
			return false;
		return true;
	}


	@Override
	public int compareTo(User other) {	
		return this.login.compareTo(other.login);
	}
	
}
