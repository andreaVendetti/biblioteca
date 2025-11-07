package it.cefi.models;

import java.util.Objects;

public class Libro {

	private int id;

	private String titolo;

	private String genere;

	private int disponibilita;

	private Integer idAutore;
	
	private String copertina;
	
	private Autore autore;
	
	public Libro() {
		if(this.disponibilita < 0) {
			this.disponibilita = 0;
		}
		
	}


	public Libro(int id, String titolo, String genere, int disponibilita, Autore autore, String copertina) {
		this.id = id;
		this.titolo = titolo;
		this.genere = genere;
		this.autore = autore;
		this.disponibilita = disponibilita;
		this.copertina = copertina;
	}

	public Libro(int id, String titolo, String genere, int disponibilita, Integer idAutore) {
		this.id = id;
		this.titolo = titolo;
		this.genere = genere;
		this.disponibilita = disponibilita;
		this.idAutore = idAutore;
	}
	
	public int getId() {
		return id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public Autore getAutore() {
		return autore;
	}

	public void setAutore(Autore autore) {
		this.autore = autore;
	}

	public int getDisponibilita() {
		return disponibilita;
	}

	//se l'autore non è null allora prende l'idAutore
	public Integer getIdAutore() {
		return idAutore;
	}

	public void setDisponibilita(int disponibilita) {
		this.disponibilita = disponibilita;
	}

	@Override
	public int hashCode() {
		return Objects.hash(autore, disponibilita, genere, id, titolo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Libro other = (Libro) obj;
		return Objects.equals(autore, other.autore) && disponibilita == other.disponibilita
				&& Objects.equals(genere, other.genere) && id == other.id && Objects.equals(titolo, other.titolo);
	}

	@Override
	public String toString() {
		return "Libro [id=" + id + ", titolo=" + titolo + ", genere=" + genere + ", disponibilita=" + disponibilita
				+ ", autoreNome=" + autore.getNome() + ", autoreCognome=" + autore.getCognome() + "]";
	}

	public String getCopertina() {
		return copertina;
	}

	public void setCopertina(String copertina) {
		this.copertina = copertina;
	}


}
