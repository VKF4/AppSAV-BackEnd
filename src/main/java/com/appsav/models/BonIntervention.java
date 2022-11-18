package com.appsav.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class BonIntervention {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String typeIntervention;
	@Temporal(TemporalType.DATE)
	private Date dateDebut;
	@Temporal(TemporalType.DATE)
	private Date dateFin;
	private int duree;
	private String objet;
	private String description;
	
	public BonIntervention() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeIntervention() {
		return typeIntervention;
	}

	public void setTypeIntervention(String typeIntervention) {
		this.typeIntervention = typeIntervention;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public String getObjet() {
		return objet;
	}

	public void setObjet(String objet) {
		this.objet = objet;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "BonIntervention [N° d'intervention=" + id + ", typeIntervention=" + typeIntervention + ", dateDebut=" + dateDebut
				+ ", dateFin=" + dateFin + ", duree=" + duree + ", objet=" + objet + ", description=" + description
				+ "]";
	}	
}
