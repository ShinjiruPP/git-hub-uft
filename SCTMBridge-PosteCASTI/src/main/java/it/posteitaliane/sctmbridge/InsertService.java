package it.posteitaliane.sctmbridge;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsertService {

	@Autowired
	private static SctmCrudRepository myRepo;

	@SuppressWarnings("static-access")
	public InsertService(SctmCrudRepository myRepo) {
		this.myRepo = myRepo;
	}

	public static void insertResult(ArrayList<InsertResultClass> myResultIterable) {
		try {
			for(int i = 0; i < myResultIterable.size(); i++) {
				InsertResultClass singleResultClass = myResultIterable.get(i);
				LocalDateTime DATA_ORA_ESITO = LocalDateTime.now();
				String NOME_UTENTE = singleResultClass.getNOME_UTENTE();
				String NOME_COMPUTER = singleResultClass.getNOME_COMPUTER();
				int ID_PROGETTO_SCTM = singleResultClass.getID_PROGETTO_SCTM();
				int ID_ISTANZA_EP_SCTM = singleResultClass.getID_ISTANZA_EP_SCTM();
				int ID_TEST_SCTM = singleResultClass.getID_TEST_SCTM();
				String NOME_TEST = singleResultClass.getNOME_TEST();
				String NOME_SCENARIO = singleResultClass.getNOME_SCENARIO();
				int ESITO = singleResultClass.getESITO();
				String VALORE_ESITO = singleResultClass.getVALORE_ESITO();
				String DESCRIZIONE_ESITO = singleResultClass.getDESCRIZIONE_ESITO();
				int ID_SCRIPT = singleResultClass.getID_SCRIPT();
				String NOME_SCRIPT = singleResultClass.getNOME_SCRIPT();
				String VERSIONE_SCRIPT = singleResultClass.getVERSIONE_SCRIPT();
				String NOME_DATABASE_SCRIPT = singleResultClass.getNOME_DATABASE_SCRIPT();
				LocalDateTime DATA_ORA_INIZIO_SCRIPT = singleResultClass.getDATA_ORA_INIZIO_SCRIPT();
				LocalDateTime DATA_ORA_INIZIO_TEST = singleResultClass.getDATA_ORA_INIZIO_TEST();
				LocalDateTime DATA_ORA_FINE_TEST = singleResultClass.getDATA_ORA_FINE_TEST();
				String DURATA_TEST = singleResultClass.getDURATA_TEST();

				myRepo.insertResult(DATA_ORA_ESITO, NOME_UTENTE, NOME_COMPUTER, ID_PROGETTO_SCTM, ID_ISTANZA_EP_SCTM, ID_TEST_SCTM, NOME_TEST, NOME_SCENARIO, ESITO, VALORE_ESITO, 
						DESCRIZIONE_ESITO, ID_SCRIPT, NOME_SCRIPT, VERSIONE_SCRIPT, NOME_DATABASE_SCRIPT, DATA_ORA_INIZIO_SCRIPT, DATA_ORA_INIZIO_TEST, DATA_ORA_FINE_TEST, DURATA_TEST);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
