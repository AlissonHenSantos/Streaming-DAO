package model.data;

import java.net.ConnectException;
import java.sql.SQLException;

import model.ModelException;

public class DAOUtils {

	private DAOUtils() {}

	
	public static void sqlExceptionTratement(String mensage, SQLException sqle) throws ModelException {
		
		if(sqle.getCause() != null) {
			if(sqle.getCause().getCause() != null) {
				if(sqle.getCause().getCause() instanceof ConnectException) {
					throw new ModelException("Banco fora do ar ", sqle.getCause().getCause());
				}
			}
		}
			
		throw new ModelException(mensage, sqle);
			
		
	}
	
	public static void close(AutoCloseable resource) throws ModelException {
		   if(resource != null) {
			   try {
				   resource.close();
			   } catch (Exception me) {
				throw new ModelException("Erro ao fechar o recurso " + resource);
			}
		   }
	}
}
