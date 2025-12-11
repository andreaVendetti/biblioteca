package it.cefi.models;

import java.time.LocalDate;


public class Recensione {

	private int id;
	
	private Libro libro;
	
	private Utente utente;
	
	private LocalDate dataRecensione;
	
	private String testo;

	
	public Recensione() {}	
	
	public Recensione(int id, Libro libro, Utente utente, LocalDate dataRecensione, String testo) {
		this.id = id;
		this.libro = libro;
		this.utente = utente;
		this.dataRecensione = dataRecensione;
		this.testo = testo;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public LocalDate getDataRecensione() {
		return dataRecensione;
	}

	public void setDataRecensione(LocalDate dataRecensione) {
		this.dataRecensione = dataRecensione;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}
	
	
	
}
