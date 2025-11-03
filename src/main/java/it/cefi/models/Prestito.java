package it.cefi.models;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Prestito {

	private int idPrestito;

	private int idLibro;
	
	private String titolo;
	
	private Utente utente;
	

	//quando un utente prende un libro
	private LocalDate ritiro;

	//quando un utente riporta un libro
	private LocalDate ritorno;

	public Prestito() {	}
	
	public Prestito(int idPrestito, Utente utente, int idLibro, LocalDate ritorno) {
		this.idPrestito = idPrestito;
		this.utente = utente;
		this.idLibro = idLibro;
		this.ritorno = ritorno;
	}
	
	public Prestito(int idPrestito, Utente utente, String titolo,int idLibro, LocalDate ritiro, LocalDate ritorno) {
		this.idPrestito = idPrestito;
		this.utente = utente;
		this.titolo = titolo;
		this.idLibro = idLibro;
		this.ritiro = ritiro;
		this.ritorno = ritorno;
	}

	public Prestito(int idPrestito, int idUtente, int idLibro, String titolo, LocalDate ritiro, LocalDate ritorno) {
		this.idPrestito = idPrestito;
		this.utente.setId(idUtente);
		this.idLibro = idLibro;
		this.ritiro = ritiro;
		this.ritorno = ritorno;
	}
	
	//-----Metodi Getter e Setter-----
	
	
	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public int getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}

	public LocalDate getRitiro() {
		return ritiro;
	}

	public void setRitiro(LocalDate ritiro) {
		this.ritiro = ritiro;
	}

	public LocalDate getRitorno() {
		return ritorno;
	}

	public void setRitorno(LocalDate ritorno) {
		this.ritorno = ritorno;
	}

	public int getIdPrestito() {
		return idPrestito;
	}

	public void setIdPrestito(int idPrestito) {
		this.idPrestito = idPrestito;
	}
	
	//----- Metodi di utilità-----

	public boolean isLate() {
		return ritiro.isBefore(LocalDate.now().minusDays(10)) && ritorno == null ? true : false;
	}
	
	 // Converte LocalDate in java.util.Date per la formattazione in JSP
	 // Prende il luogo e l'istante dove ci troviamo (zoneId), prende il fusorario(atStartOfDay), 
     //lo trasforma in istante e col from lo converte in data 
    public Date getRitiroAsDate() {
        return ritiro == null ? null : Date.from(ritiro.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date getRitornoAsDate() {
        return ritorno == null ? null : Date.from(ritorno.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
	
}
