package it.posteitaliane.sctmbridge;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "InsertResultClass")
@Table(name = "exporttable")
public class InsertResultClass {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_ESITO")
	private int ID_ESITO;
	
	@Column(name = "DATA_ORA_ESITO")
	private LocalDateTime DATA_ORA_ESITO;
	@Column(name = "NOME_UTENTE")
	private String NOME_UTENTE;
	@Column(name = "NOME_COMPUTER")
	private String NOME_COMPUTER;
	@Column(name = "ID_PROGETTO_SCTM")
	private int ID_PROGETTO_SCTM;
	@Column(name = "ID_ISTANZA_EP_SCTM")
	private int ID_ISTANZA_EP_SCTM;
	
	@Column(name = "ID_TEST_SCTM")
	private int ID_TEST_SCTM;
	@Column(name = "NOME_TEST")
	private String NOME_TEST;
	@Column(name = "NOME_SCENARIO")
	private String NOME_SCENARIO;
	@Column(name = "ESITO")
	private int ESITO;
	@Column(name = "VALORE_ESITO")
	private String VALORE_ESITO;
	@Column(name = "DESCRIZIONE_ESITO")
	private String DESCRIZIONE_ESITO;
	
	@Column(name = "ID_SCRIPT")
	private int ID_SCRIPT;
	@Column(name = "NOME_SCRIPT")
	private String NOME_SCRIPT;
	@Column(name = "VERSIONE_SCRIPT")
	private String VERSIONE_SCRIPT;
	@Column(name = "NOME_DATABASE_SCRIPT")
	private String NOME_DATABASE_SCRIPT;
	
	@Column(name = "DATA_ORA_INIZIO_SCRIPT")
	private LocalDateTime DATA_ORA_INIZIO_SCRIPT;
	@Column(name = "DATA_ORA_INIZIO_TEST")
	private LocalDateTime DATA_ORA_INIZIO_TEST;
	@Column(name = "DATA_ORA_FINE_TEST")
	private LocalDateTime DATA_ORA_FINE_TEST;
	@Column(name = "DURATA_TEST")
	private String DURATA_TEST;
	
	public InsertResultClass() {
		
	}

	public LocalDateTime getDATA_ORA_ESITO() {
		return DATA_ORA_ESITO;
	}

	public void setDATA_ORA_ESITO(LocalDateTime dATA_ORA_ESITO) {
		DATA_ORA_ESITO = dATA_ORA_ESITO;
	}

	public String getNOME_UTENTE() {
		return NOME_UTENTE;
	}

	public void setNOME_UTENTE(String nOME_UTENTE) {
		NOME_UTENTE = nOME_UTENTE;
	}

	public String getNOME_COMPUTER() {
		return NOME_COMPUTER;
	}

	public void setNOME_COMPUTER(String nOME_COMPUTER) {
		NOME_COMPUTER = nOME_COMPUTER;
	}

	public int getID_PROGETTO_SCTM() {
		return ID_PROGETTO_SCTM;
	}

	public void setID_PROGETTO_SCTM(int iD_PROGETTO_SCTM) {
		ID_PROGETTO_SCTM = iD_PROGETTO_SCTM;
	}

	public int getID_ISTANZA_EP_SCTM() {
		return ID_ISTANZA_EP_SCTM;
	}

	public void setID_ISTANZA_EP_SCTM(int iD_ISTANZA_EP_SCTM) {
		ID_ISTANZA_EP_SCTM = iD_ISTANZA_EP_SCTM;
	}

	public int getID_TEST_SCTM() {
		return ID_TEST_SCTM;
	}

	public void setID_TEST_SCTM(int iD_TEST_SCTM) {
		ID_TEST_SCTM = iD_TEST_SCTM;
	}

	public String getNOME_TEST() {
		return NOME_TEST;
	}

	public void setNOME_TEST(String nOME_TEST) {
		NOME_TEST = nOME_TEST;
	}

	public String getNOME_SCENARIO() {
		return NOME_SCENARIO;
	}

	public void setNOME_SCENARIO(String nOME_SCENARIO) {
		NOME_SCENARIO = nOME_SCENARIO;
	}

	public int getESITO() {
		return ESITO;
	}

	public void setESITO(int eSITO) {
		ESITO = eSITO;
	}

	public String getVALORE_ESITO() {
		return VALORE_ESITO;
	}

	public void setVALORE_ESITO(String vALORE_ESITO) {
		VALORE_ESITO = vALORE_ESITO;
	}

	public String getDESCRIZIONE_ESITO() {
		return DESCRIZIONE_ESITO;
	}

	public void setDESCRIZIONE_ESITO(String dESCRIZIONE_ESITO) {
		DESCRIZIONE_ESITO = dESCRIZIONE_ESITO;
	}

	public int getID_SCRIPT() {
		return ID_SCRIPT;
	}

	public void setID_SCRIPT(int iD_SCRIPT) {
		ID_SCRIPT = iD_SCRIPT;
	}

	public String getNOME_SCRIPT() {
		return NOME_SCRIPT;
	}

	public void setNOME_SCRIPT(String nOME_SCRIPT) {
		NOME_SCRIPT = nOME_SCRIPT;
	}

	public String getVERSIONE_SCRIPT() {
		return VERSIONE_SCRIPT;
	}

	public void setVERSIONE_SCRIPT(String vERSIONE_SCRIPT) {
		VERSIONE_SCRIPT = vERSIONE_SCRIPT;
	}

	public String getNOME_DATABASE_SCRIPT() {
		return NOME_DATABASE_SCRIPT;
	}

	public void setNOME_DATABASE_SCRIPT(String nOME_DATABASE_SCRIPT) {
		NOME_DATABASE_SCRIPT = nOME_DATABASE_SCRIPT;
	}

	public LocalDateTime getDATA_ORA_INIZIO_SCRIPT() {
		return DATA_ORA_INIZIO_SCRIPT;
	}

	public void setDATA_ORA_INIZIO_SCRIPT(LocalDateTime dATA_ORA_INIZIO_SCRIPT) {
		DATA_ORA_INIZIO_SCRIPT = dATA_ORA_INIZIO_SCRIPT;
	}

	public LocalDateTime getDATA_ORA_INIZIO_TEST() {
		return DATA_ORA_INIZIO_TEST;
	}

	public void setDATA_ORA_INIZIO_TEST(LocalDateTime dATA_ORA_INIZIO_TEST) {
		DATA_ORA_INIZIO_TEST = dATA_ORA_INIZIO_TEST;
	}

	public LocalDateTime getDATA_ORA_FINE_TEST() {
		return DATA_ORA_FINE_TEST;
	}

	public void setDATA_ORA_FINE_TEST(LocalDateTime dATA_ORA_FINE_TEST) {
		DATA_ORA_FINE_TEST = dATA_ORA_FINE_TEST;
	}

	public String getDURATA_TEST() {
		return DURATA_TEST;
	}

	public void setDURATA_TEST(String dURATA_TEST) {
		DURATA_TEST = dURATA_TEST;
	}

}
