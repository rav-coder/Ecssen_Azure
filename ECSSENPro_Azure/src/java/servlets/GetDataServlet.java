package servlets;


import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Store;
import models.User;
import services.AccountServices;
import services.CompanyService;
import services.StoreServices;

/**
 *
 * @author lixia
 */
public class GetDataServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		CompanyService cs = new CompanyService();		
		String op = request.getParameter("operation");
            
		if (op.equals("store")) {
			String companyId = request.getParameter("companyId");

                List<Store> storelist = null;
			try {
				storelist = cs.get(Short.parseShort(companyId)).getStoreList();
			} catch (Exception ex) {
				Logger.getLogger(GetDataServlet.class.getName()).log(Level.SEVERE, null, ex);
			}
                
			StringBuilder storeJSON = new StringBuilder();
			storeJSON.append('[');
			if (storelist != null) {
				for (Store store : storelist)
				{
					storeJSON.append('{');
					storeJSON.append("\"store_name\":" + "\"" + store.getStoreName() + "\",");
					storeJSON.append("\"store_id\":" + "\"" + store.getStoreId() + "\"");
					storeJSON.append("},");
				}
			}
				if (storeJSON.length() > 2) {
					storeJSON.setLength(storeJSON.length() - 1);
				}

				storeJSON.append(']');

                response.setContentType("text/html");
                response.getWriter().write(storeJSON.toString());
            }
                
                AccountServices as = new AccountServices();
    List<User> allSupervisors = null;
    
    if (op.equals("program")) {
        
            String programAdd = (String) request.getParameter("programId");
            
            //String[] parts = programAdd.split(";");
            
            //String programAddName = parts[0];
            Short pogramAddId = Short.valueOf(programAdd);
        
        try {
            allSupervisors = as.getAllActiveSupervisorsByProgram(pogramAddId);

        } catch (Exception ex) {
            Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        StringBuilder programJSON = new StringBuilder();
	programJSON.append('[');
        
        if (allSupervisors != null) {
        
            for (User user : allSupervisors)
		{
                    programJSON.append('{');
                    programJSON.append("\"user_name\":" + "\"" + user.getFirstName() + " " + user.getLastName()+ "\",");
                    programJSON.append("\"user_id\":" + "\"" + user.getUserId() + "\"");
                    programJSON.append("},");
		}
	}
            
        if (programJSON.length() > 2) {
            programJSON.setLength(programJSON.length() - 1);
	}
        
//        if (programJSON.length() == 1) {
//            programJSON.setLength(programJSON.length() - 1);
//	}

	programJSON.append(']');

        response.setContentType("text/html");
        response.getWriter().write(programJSON.toString());
    }
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

	}

}
