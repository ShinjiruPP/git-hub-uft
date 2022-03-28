package it.posteitaliane.sctmbridge;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface SctmCrudRepository extends CrudRepository<InsertResultClass, Iterable<InsertResultClass>>{

	@Modifying
	@Query(value = "INSERT INTO TBL_ESITI "
			+ "("
			+ "DATA_ORA_ESITO, NOME_UTENTE, NOME_COMPUTER, ID_PROGETTO_SCTM, ID_ISTANZA_EP_SCTM,"
			+ "ID_TEST_SCTM, NOME_TEST, NOME_SCENARIO, ESITO, VALORE_ESITO, DESCRIZIONE_ESITO,"
			+ "ID_SCRIPT, NOME_SCRIPT, VERSIONE_SCRIPT, NOME_DATABASE_SCRIPT,"
			+ "DATA_ORA_INIZIO_SCRIPT, DATA_ORA_INIZIO_TEST, DATA_ORA_FINE_TEST, DURATA_TEST"
			+ ")"
			+ "VALUES"
			+ "("
			+ ":DATA_ORA_ESITO, :NOME_UTENTE, :NOME_COMPUTER, :ID_PROGETTO_SCTM, :ID_ISTANZA_EP_SCTM,"
			+ ":ID_TEST_SCTM, :NOME_TEST, :NOME_SCENARIO, :ESITO, :VALORE_ESITO, :DESCRIZIONE_ESITO,"
			+ ":ID_SCRIPT, :NOME_SCRIPT, :VERSIONE_SCRIPT, :NOME_DATABASE_SCRIPT,"
			+ ":DATA_ORA_INIZIO_SCRIPT, :DATA_ORA_INIZIO_TEST, :DATA_ORA_FINE_TEST, :DURATA_TEST"
			+ ")", nativeQuery = true)
	void insertResult(@Param("DATA_ORA_ESITO") LocalDateTime string, @Param("NOME_UTENTE") String NOME_UTENTE, @Param("NOME_COMPUTER") String NOME_COMPUTER,
			@Param("ID_PROGETTO_SCTM") int ID_PROGETTO_SCTM, @Param("ID_ISTANZA_EP_SCTM") int ID_ISTANZA_EP_SCTM, @Param("ID_TEST_SCTM") int ID_TEST_SCTM, @Param("NOME_TEST") String NOME_TEST,
			@Param("NOME_SCENARIO") String NOME_SCENARIO, @Param("ESITO") int ESITO, @Param("VALORE_ESITO") String VALORE_ESITO, @Param("DESCRIZIONE_ESITO") String DESCRIZIONE_ESITO,
			@Param("ID_SCRIPT") int ID_SCRIPT, @Param("NOME_SCRIPT") String NOME_SCRIPT, @Param("VERSIONE_SCRIPT") String VERSIONE_SCRIPT, @Param("NOME_DATABASE_SCRIPT") String NOME_DATABASE_SCRIPT,
			@Param("DATA_ORA_INIZIO_SCRIPT") LocalDateTime DATA_ORA_INIZIO_SCRIPT, @Param("DATA_ORA_INIZIO_TEST") LocalDateTime DATA_ORA_INIZIO_TEST, @Param("DATA_ORA_FINE_TEST") LocalDateTime DATA_ORA_FINE_TEST,
			@Param("DURATA_TEST") String DURATA_TEST);

}

