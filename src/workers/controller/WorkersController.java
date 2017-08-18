package workers.controller;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import common.BaseController;
import workers.model.WorkersEnum;
import workers.model.WorkersModel;
import workers.view.WorkersTemp;

public class WorkersController extends BaseController {
	
	private List<WorkersModel> workerAddListWM;
	
	public WorkersController() {
		
		workerAddListWM = new LinkedList<WorkersModel>();
	}
	
	public List<WorkersModel> getWorkersList() {
		return Collections.unmodifiableList(workerAddListWM);
	}
	
	public void saveWorker(WorkersTemp workerTemp) throws SQLException {
		
		// provjera dali već imamo radnika sa istim ID-om ili OIB-om
		WorkersModel workersModelCheck = new WorkersModel(workerTemp.getId(), workerTemp.getOib());
		WorkersModel workersModelTrue = new WorkersModel(workerTemp.getId(), workerTemp.getName(), workerTemp.getSurname(), workerTemp.getOib(), workerTemp.getBirthYear(), workerTemp.getSex(), workerTemp.getPassword());	
				
		if (checkWorkerBeforeSave(workersModelCheck) == 0) {
			workerAddListWM.add(workersModelTrue);
			
			String procCountSql = "{ call zavrsni.countWorkers(?) }";
			CallableStatement csCount = con.prepareCall(procCountSql);
			
			String procInsertSql = "{ call zavrsni.saveWorker(?,?,?,?,?,?,?) }";
			CallableStatement csInsert = con.prepareCall(procInsertSql);
			
			ResultSet checkResult = null;
			
			for(WorkersModel worker : workerAddListWM) {
		
				csCount.setInt(1, worker.getId());
				
				checkResult = csCount.executeQuery();
				checkResult.next();
				
				int count = checkResult.getInt(1);
				
				if(count == 0) {
					csInsert.setInt(WorkersEnum.WORKER_ID.getKey(), worker.getId());
					csInsert.setString(WorkersEnum.WORKER_NAME.getKey(), worker.getName());
					csInsert.setString(WorkersEnum.WORKER_SURNAME.getKey(), worker.getSurname());
					csInsert.setString(WorkersEnum.WORKER_OIB.getKey(), worker.getOib());
					csInsert.setInt(WorkersEnum.BIRTH_YEAR.getKey(), worker.getBirthYear());
					csInsert.setString(WorkersEnum.SEX.getKey(), worker.getSex());
					csInsert.setString(WorkersEnum.PASSWORD.getKey(), worker.getPassword());
					
					csInsert.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "RADNIK USPIJEŠNO UNESEN! ID: " + worker.getId(), "INFO", JOptionPane.INFORMATION_MESSAGE);
				}
					
			}
			
			checkResult.close();
			csInsert.close();
			csCount.close();
		}
		else if (checkWorkerBeforeSave(workersModelCheck) == 1) {
			JOptionPane.showMessageDialog(null, "OBI VEĆ POSTOJI!!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
		}
		else if (checkWorkerBeforeSave(workersModelCheck) == 2) {
			JOptionPane.showMessageDialog(null, "ID VEĆ POSTOJI!!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public int checkWorkerBeforeSave(WorkersModel workersModel) {
		
		for (int i = 0; i < workerAddListWM.size(); i++) {			
			if ( (workersModel.getOib().equals((workerAddListWM.get(i).getOib()))) ) {
				return 1;
			}
			else if ( (workersModel.getId() == (workerAddListWM.get(i).getId())) ) {
				return 2;
			}
		}
		return 0;
	}
		
	public void loadWorkers() throws SQLException {
		workerAddListWM.clear();
		
		String procSql = "{ call zavrsni.loadWorkers() }";
		CallableStatement cs = con.prepareCall(procSql);
		
		ResultSet result = cs.executeQuery();
		
		while(result.next()) {

			WorkersModel radnik = new WorkersModel(result.getInt(WorkersEnum.WORKER_ID.getValue()),
					result.getString(WorkersEnum.WORKER_NAME.getValue()), result.getString(WorkersEnum.WORKER_SURNAME.getValue()),
					result.getString(WorkersEnum.WORKER_OIB.getValue()), result.getInt(WorkersEnum.BIRTH_YEAR.getValue()),
					result.getString(WorkersEnum.SEX.getValue()), result.getString(WorkersEnum.PASSWORD.getValue()));
			
			workerAddListWM.add(radnik);
		}
		
		result.close();
		cs.close();
	}
	
	public void deleteWorker(int row_index, int workerId) throws SQLException {
		
		workerAddListWM.remove(row_index);
			
		String procSql = "{ call zavrsni.deleteWorker('"+workerId+"') }";
		CallableStatement csDelete = con.prepareCall(procSql);
		
		JOptionPane.showMessageDialog(null, "RADNIK USPIJEŠNO IZBRISAN! ID: " + workerId, "INFO", JOptionPane.INFORMATION_MESSAGE);
		
		csDelete.executeUpdate();
		csDelete.close();
	}
	
	public String showWorkerPassword(int workerId) throws SQLException {
		
		String password = null;
		
		String procSql = "{ call zavrsni.passwordWorker('"+workerId+"') }";
		CallableStatement csPassword = con.prepareCall(procSql);
		
		ResultSet result = csPassword.executeQuery();
		
		while (result.next()) {
			password = result.getString("password");
		}
		
		result.close();
		csPassword.close();
		
		return password;
	}
	
}
