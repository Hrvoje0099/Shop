package admin.controller;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import admin.model.ExceptionsEnum;
import admin.model.ExceptionsModel;
import common.BaseController;

public class AdminController extends BaseController {
	
	private List<ExceptionsModel> exceptionsListEM;
	
	public AdminController() {
		
		exceptionsListEM = new LinkedList<ExceptionsModel>();
	}
	
	public List<ExceptionsModel> getExceptionsList() {
		return Collections.unmodifiableList(exceptionsListEM);
	}
	
	public void loadExceptions() throws SQLException {
		exceptionsListEM.clear();
		
		String procSql = "{ call zavrsni.loadExceptions() }";
		CallableStatement cs = con.prepareCall(procSql);
		
		ResultSet result = cs.executeQuery();
		
		while(result.next()) {
			ExceptionsModel exception = new ExceptionsModel(result.getInt(ExceptionsEnum.ID.getValue()), result.getDate(ExceptionsEnum.TIME.getValue()), result.getTime(ExceptionsEnum.TIME.getValue()), result.getString(ExceptionsEnum.MESSAGE.getValue()));
			exceptionsListEM.add(exception);
		}
		
		result.close();
		cs.close();
	}
		
	public String loadExceptionsStackTrace(int id) throws SQLException {
		
		String procSql = "{ call zavrsni.loadExceptionsStackTrace('"+id+"') }";
		CallableStatement csLoad = con.prepareCall(procSql);
		ResultSet result = csLoad.executeQuery();
		
		String stackTrace = null;
		
		while(result.next()) {
			stackTrace = result.getString(ExceptionsEnum.STACK_TRACE.getValue());
		}
		
		result.close();
		csLoad.close();
		
		return stackTrace;
	}

}
