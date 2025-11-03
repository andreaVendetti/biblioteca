package it.cefi.models;

import java.util.Objects;

public class Utente extends Persona {


	private String username;

	private String password;

	private boolean admin;

	private int ruolo;
	
	private int avvisoInviato = 0; // 0 = nessun avviso, 1 = avviso inviato
	private String email; //***nuovo campo email

	// una volta che istanzio un utente avrò il suo ruolo
	public Utente() {
		this.ruolo = 0;
	}

	public Utente(int id, String nome, String cognome, String username, String password, int ruolo, String email) {
		super(id, nome, cognome);
		this.username = username;
		this.password = password;
		this.ruolo = ruolo;
		this.setEmail(email);
	}

	public Utente(String username, String password, int ruolo, String email) {
		super();
		this.username = username;
		this.password = password;
		this.ruolo = ruolo;
		this.setEmail(email);
	}
	
	public int getRuolo() {
		return ruolo;
	}

	public void setRuolo(int ruolo) {
		this.ruolo = ruolo;
	}


	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		switch(getRuolo()) {
		case 0: 
			return false;
		case 1:
			return true;
		case 2: 
			return true;
		default:
			return false;
		}
	}

	  public int getAvvisoInviato() {
	        return avvisoInviato;
	    }

	    public void setAvvisoInviato(int avvisoInviato) {
	        this.avvisoInviato = avvisoInviato;
	    }
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(admin, password, username);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || (getClass() != obj.getClass())) {
			return false;
		}
		Utente other = (Utente) obj;
		return admin == other.admin && Objects.equals(password, other.password)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "Utente [username=" + username + ", password=" + password + ", admin=" + admin + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
