package automaticePlanner.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import automaticPlanner.beans.Courses;
import automaticPlanner.beans.UserAccount;
import automaticPlanner.utils.DBUtils;
import automaticPlanner.utils.MyUtils;
 
@WebServlet(urlPatterns = { "/genEds" })
public class GenEdServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public GenEdServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpServletRequest req = (HttpServletRequest) request;
    	HttpSession session = req.getSession();
    	// Check User has logged on
        UserAccount loginedUser = MyUtils.getLoginedUser(session);
        // Not logged in
        if (loginedUser == null) {
            // Redirect to login page.
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        Connection conn = MyUtils.getStoredConnection(request);
        String errorString = null;
        List<Courses> list = null;
        try {
            list = DBUtils.getRecordOfCourses(conn, loginedUser.getStudentID());
            DBUtils.coreRequirementCheck(list, request);		
            //checks for cs requirements, will probaly need to send to own view.jsp
            
            DBUtils.genEdsRequirementCheck(list, request);
            
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }
        //Store info in request attribute, before forward to views
        request.setAttribute("errorString", errorString);
        // Forward to /WEB-INF/views/genEdView.jsp
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/genEdView.jsp");
        dispatcher.forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
}







//old class format
//@WebServlet(urlPatterns = { "/genEds" })
//public class GenEdServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
// 
//    public GenEdServlet() {
//        super();
//    }
// 
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//    	HttpServletRequest req = (HttpServletRequest) request;
//    	HttpSession session = req.getSession();
//    	// Check User has logged on
//        UserAccount loginedUser = MyUtils.getLoginedUser(session);
// 
//        // Not logged in
//        if (loginedUser == null) {
//            // Redirect to login page.
//            response.sendRedirect(request.getContextPath() + "/login");
//            return;
//        }
//    	
//        Connection conn = MyUtils.getStoredConnection(request);
//        UserAccount user= MyUtils.getLoginedUser(session);
//        String errorString = null;
//        List<Courses> list = null;
//        try {
//            list = DBUtils.getRecordOfCourses(conn, user.getStudentID());
//        } catch (SQLException e) {
//            e.printStackTrace();
//            errorString = e.getMessage();
//        }
//     	//Once the records are pulled up simply go through and check
//        //to see that requirements are being met
//        //this loop will check part a section 1 composition
//        //this can be a two parter so be careful of that
//        //needs ENG 110, or (ENG 108 and ENG 109) or (ENG 112)
//        boolean Composition=false;
//        boolean Eng108=false;
//        boolean Eng109=false;
//        for(Courses CR : list) {
//        	if(CR.getClassType().equals("ENG") && CR.getClassNumber().equals("110")) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Composition=true;
//        		}
//        	}
//        	if(CR.getClassType().equals("ENG") && CR.getClassNumber().equals("112")) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Composition=true;
//        		}
//        	}
//        	if(CR.getClassType().equals("ENG") && CR.getClassNumber().equals("108")) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Eng108=true;
//        		}
//        	}
//        	if(CR.getClassType().equals("ENG") && CR.getClassNumber().equals("109")) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Eng109=true;
//        		}
//        	}
//        	if(Eng108 && Eng109) {
//        		Composition=true;
//        	}
//        	//System.out.println("FROM SERVLET "+Composition);
//        }
//        request.setAttribute("Composition", Composition);
//        
//        //this loop checks part a section 2
//        boolean Logic=false;
//        for(Courses CR : list) {
//        	if(CR.getClassType().equals("MAT") && CR.getClassNumber().equals("271")) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Logic=true;
//        		}
//        	}
//        	if(CR.getClassType().equals("PHI") && CR.getClassNumber().equals("120")) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Logic=true;
//        		}
//        	}
//        	if(CR.getClassType().equals("PSY") && CR.getClassNumber().equals("110")) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Logic=true;
//        		}
//        	}
//        }
//        request.setAttribute("Logic", Logic);
//        
//        //this loop checks part a section 3
//        boolean Oral=false;
//        for(Courses CR : list) {
//        	if(CR.getClassType().equals("THE") && CR.getClassNumber().equals("120")) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Oral=true;
//        		}
//        	}
//        }
//        request.setAttribute("Oral", Oral);
//        
//        //this loop checks part b section 1
//        boolean Physical=false;
//        for(Courses CR : list) {
//        	if(CR.getClassType().equals("CHE") && CR.getClassNumber().equals("102")) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Physical=true;
//        		}
//        	}
//        	if(CR.getClassType().equals("EAR") && CR.getClassNumber().equals("100")) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Physical=true;
//        		}
//        	}
//        	if(CR.getClassType().equals("GEO") && CR.getClassNumber().equals("200")) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Physical=true;
//        		}
//        	}
//        	if(CR.getClassType().equals("PHY") && CR.getClassNumber().equals("100")) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Physical=true;
//        		}
//        	}
//        }
//        request.setAttribute("Physical", Physical);
//        
//        //this loop checks part b sec 2
//        boolean Life=false;
//        for(Courses CR : list) {
//        	if(CR.getClassType().equals("ANT") && CR.getClassNumber().equals("101")) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Life=true;
//        		}
//        	}
//        	if(CR.getClassType().equals("BIO") && CR.getClassNumber().equals("102")) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Life=true;
//        		}
//        	}
//        }
//        request.setAttribute("Life", Life);
//        
//        //
//        boolean ScienceLab=false;
//        for(Courses CR : list) {
//        	if(CR.getClassType().equals("BIO") && CR.getClassNumber().equals("103")) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			ScienceLab=true;
//        		}
//        	}
//        	if(CR.getClassType().equals("EAR") && CR.getClassNumber().equals("101")) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			ScienceLab=true;
//        		}
//        	}
//        	if(CR.getClassType().equals("CHE") && CR.getClassNumber().equals("103")) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			ScienceLab=true;
//        		}
//        	}
//        }
//        request.setAttribute("ScienceLab", ScienceLab);
//        
//        //part b sec 4
//        boolean Quantitative=false;
//        for(Courses CR : list) {
//        	if(CR.getClassType().equals("MAT")) {
//        		if(CR.getClassNumber().contentEquals("105")||CR.getClassNumber().contentEquals("131")||
//        				CR.getClassNumber().contentEquals("132")||CR.getClassNumber().contentEquals("151")||
//        				CR.getClassNumber().contentEquals("153")||CR.getClassNumber().contentEquals("171")||
//        				CR.getClassNumber().contentEquals("191")||CR.getClassNumber().contentEquals("193")) {
//        			if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        				Quantitative=true;
//            		}
//        			
//        		}
//        	}
//        }
//        request.setAttribute("Quantitative", Quantitative);
//        
//        //part c sec 1,2,3
//        int artCounter=0, letterCounter=0;
//        boolean Letters=false;
//        boolean Arts=false;
//        boolean Humanities=false;
//        for(Courses CR : list) {
//        	if(		  (CR.getClassType().equals("AFS") && CR.getClassNumber().equals("205"))
//        			||(CR.getClassType().equals("APP") && CR.getClassNumber().equals("225"))
//        			||(CR.getClassType().equals("ART") && CR.getClassNumber().equals("100"))
//        			||(CR.getClassType().equals("ART") && CR.getClassNumber().equals("101"))
//        			||(CR.getClassType().equals("CHS") && CR.getClassNumber().equals("125"))
//        			||(CR.getClassType().equals("COM") && CR.getClassNumber().equals("130"))
//        			||(CR.getClassType().equals("DAN") && CR.getClassNumber().equals("130"))
//        			||(CR.getClassType().equals("ENG") && CR.getClassNumber().equals("271"))
//        			||(CR.getClassType().equals("MUS") && CR.getClassNumber().equals("101"))
//        			||(CR.getClassType().equals("MUS") && CR.getClassNumber().equals("110"))
//        			||(CR.getClassType().equals("MUS") && CR.getClassNumber().equals("201"))
//        			||(CR.getClassType().equals("MUS") && CR.getClassNumber().equals("250"))
//        			||(CR.getClassType().equals("THE") && CR.getClassNumber().equals("100"))
//        			||(CR.getClassType().equals("THE") && CR.getClassNumber().equals("160"))) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Arts=true;
//        			artCounter++;
//        		}
//        	}
//        	if(		  (CR.getClassType().equals("AFS") && CR.getClassNumber().equals("200"))
//        			||(CR.getClassType().equals("AFS") && CR.getClassNumber().equals("231"))
//        			||(CR.getClassType().equals("APP") && CR.getClassNumber().equals("101"))
//        			||(CR.getClassType().equals("APP") && CR.getClassNumber().equals("201"))
//        			||(CR.getClassType().equals("CHS") && CR.getClassNumber().equals("100"))
//        			||(CR.getClassType().equals("CHS") && CR.getClassNumber().equals("205"))
//        			||(CR.getClassType().equals("COM") && CR.getClassNumber().equals("100"))
//        			||(CR.getClassType().equals("ENG") && CR.getClassNumber().equals("230"))
//        			||(CR.getClassType().equals("FRE") && CR.getClassNumber().equals("220"))
//        			||(CR.getClassType().equals("HUM") && CR.getClassNumber().equals("200"))
//        			||(CR.getClassType().equals("HUM") && CR.getClassNumber().equals("212"))
//        			||(CR.getClassType().equals("JPN") && CR.getClassNumber().equals("110"))
//        			||(CR.getClassType().equals("JPN") && CR.getClassNumber().equals("111"))
//        			||(CR.getClassType().equals("PHI") && CR.getClassNumber().equals("101"))
//        			||(CR.getClassType().equals("PHI") && CR.getClassNumber().equals("102"))
//        			||(CR.getClassType().equals("PHI") && CR.getClassNumber().equals("201"))
//        			||(CR.getClassType().equals("PHI") && CR.getClassNumber().equals("202"))
//        			||(CR.getClassType().equals("SPA") && CR.getClassNumber().equals("151"))
//        			||(CR.getClassType().equals("SPA") && CR.getClassNumber().equals("221"))
//        			||(CR.getClassType().equals("WMS") && CR.getClassNumber().equals("100"))) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Letters=true;
//        			letterCounter++;
//        		}
//        	}
//        	if(artCounter>=2||letterCounter>=2) {
//        		if(Letters && Arts) {
//        			Humanities=true;
//        		}
//        	}
//        }
//        request.setAttribute("Arts", Arts);
//        request.setAttribute("Letters", Letters);
//        request.setAttribute("Humanities", Humanities);
//        
//        //part d sec 1 2 3 
//        int societyCounter=0, globalCounter=0;
//        boolean Society=false;
//        boolean Global=false;
//        boolean Social=false;
//        for(Courses CR : list) {
//        	if(		  (CR.getClassType().equals("AFS") && CR.getClassNumber().equals("100"))
//        			||(CR.getClassType().equals("AFS") && CR.getClassNumber().equals("220"))
//        			||(CR.getClassType().equals("ANT") && CR.getClassNumber().equals("100"))
//        			||(CR.getClassType().equals("APP") && CR.getClassNumber().equals("212"))
//        			||(CR.getClassType().equals("CHS") && CR.getClassNumber().equals("212"))
//        			||(CR.getClassType().equals("ECO") && CR.getClassNumber().equals("210"))
//        			||(CR.getClassType().equals("ECO") && CR.getClassNumber().equals("211"))
//        			||(CR.getClassType().equals("IDS") && CR.getClassNumber().equals("210"))
//        			||(CR.getClassType().equals("LBS") && CR.getClassNumber().equals("205"))
//        			||(CR.getClassType().equals("LAW") && CR.getClassNumber().equals("240"))
//        			||(CR.getClassType().equals("PSY") && CR.getClassNumber().equals("101"))
//        			||(CR.getClassType().equals("SOC") && CR.getClassNumber().equals("101"))
//        			||(CR.getClassType().equals("SOC") && CR.getClassNumber().equals("102"))
//        			||(CR.getClassType().equals("WMS") && CR.getClassNumber().equals("250"))) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Society=true;
//        			societyCounter++;
//        		}
//        	}
//        	if(		  (CR.getClassType().equals("AFS") && CR.getClassNumber().equals("201"))
//        			||(CR.getClassType().equals("ANT") && CR.getClassNumber().equals("102"))
//        			||(CR.getClassType().equals("CHS") && CR.getClassNumber().equals("200"))
//        			||(CR.getClassType().equals("ENG") && CR.getClassNumber().equals("150"))
//        			||(CR.getClassType().equals("GEO") && CR.getClassNumber().equals("100"))
//        			||(CR.getClassType().equals("HIS") && CR.getClassNumber().equals("120"))
//        			||(CR.getClassType().equals("HIS") && CR.getClassNumber().equals("121"))
//        			||(CR.getClassType().equals("LBR") && CR.getClassNumber().equals("200"))
//        			||(CR.getClassType().equals("MGT") && CR.getClassNumber().equals("200"))
//        			||(CR.getClassType().equals("POL") && CR.getClassNumber().equals("100"))) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Global=true;
//        			globalCounter++;
//        		}
//        	}
//        	if(societyCounter>=2||globalCounter>=2) {
//        		if(Global && Society) {
//        			Social=true;
//        		}
//        	}
//        }
//        request.setAttribute("Society", Society);
//        request.setAttribute("Global", Global);
//        request.setAttribute("Social", Social);
//        
//        //part e 
//        boolean Lifelong=false;
//        for(Courses CR : list) {
//        	if(		  (CR.getClassType().equals("BUS") && CR.getClassNumber().equals("100"))
//        			||(CR.getClassType().equals("CIS") && CR.getClassNumber().equals("275"))
//        			||(CR.getClassType().equals("FIN") && CR.getClassNumber().equals("200"))
//        			||(CR.getClassType().equals("HEA") && CR.getClassNumber().equals("100"))
//        			||(CR.getClassType().equals("HEA") && CR.getClassNumber().equals("201"))
//        			||(CR.getClassType().equals("KIN") && CR.getClassNumber().equals("235"))
//        			||(CR.getClassType().equals("LIB") && CR.getClassNumber().equals("151"))
//        			||(CR.getClassType().equals("REC") && CR.getClassNumber().equals("100"))
//        			||(CR.getClassType().equals("UNV") && CR.getClassNumber().equals("101"))) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			Lifelong=true;
//        		}
//        	}
//        }
//        request.setAttribute("Lifelong", Lifelong);
//        
//        //part f sec 1 2 3
//        boolean IntegrativeHumanities=false;
//        boolean IntegrativeNatural=false;
//        boolean IntegrativeSocial=false;
//        for(Courses CR : list) {
//        	if(		  (CR.getClassType().equals("AFS") && CR.getClassNumber().equals("331"))
//        			||(CR.getClassType().equals("AFS") && CR.getClassNumber().equals("332"))
//        			||(CR.getClassType().equals("AFS") && CR.getClassNumber().equals("333"))
//        			||(CR.getClassType().equals("AFS") && CR.getClassNumber().equals("334"))
//        			||(CR.getClassType().equals("APP") && CR.getClassNumber().equals("314"))
//        			||(CR.getClassType().equals("APP") && CR.getClassNumber().equals("315"))
//        			||(CR.getClassType().equals("APP") && CR.getClassNumber().equals("325"))
//        			||(CR.getClassType().equals("APP") && CR.getClassNumber().equals("339"))
//        			||(CR.getClassType().equals("APP") && CR.getClassNumber().equals("343"))
//        			||(CR.getClassType().equals("ARH") && CR.getClassNumber().equals("370"))
//        			||(CR.getClassType().equals("CHS") && CR.getClassNumber().equals("340"))
//        			||(CR.getClassType().equals("CHS") && CR.getClassNumber().equals("345"))
//        			||(CR.getClassType().equals("ENG") && CR.getClassNumber().equals("308"))
//        			||(CR.getClassType().equals("HIS") && CR.getClassNumber().equals("355"))
//        			||(CR.getClassType().equals("HIS") && CR.getClassNumber().equals("375"))
//        			||(CR.getClassType().equals("HIS") && CR.getClassNumber().equals("376"))
//        			||(CR.getClassType().equals("HUM") && CR.getClassNumber().equals("310"))
//        			||(CR.getClassType().equals("HUM") && CR.getClassNumber().equals("312"))
//        			||(CR.getClassType().equals("HUM") && CR.getClassNumber().equals("314"))
//        			||(CR.getClassType().equals("IDS") && CR.getClassNumber().equals("312"))
//        			||(CR.getClassType().equals("LBR") && CR.getClassNumber().equals("310"))
//        			||(CR.getClassType().equals("LBR") && CR.getClassNumber().equals("312"))
//        			||(CR.getClassType().equals("LBR") && CR.getClassNumber().equals("314"))
//        			||(CR.getClassType().equals("MUS") && CR.getClassNumber().equals("312"))
//        			||(CR.getClassType().equals("MUS") && CR.getClassNumber().equals("345"))
//        			||(CR.getClassType().equals("MUS") && CR.getClassNumber().equals("486"))
//        			||(CR.getClassType().equals("PHI") && CR.getClassNumber().equals("351"))
//        			||(CR.getClassType().equals("PHI") && CR.getClassNumber().equals("352"))
//        			||(CR.getClassType().equals("PHI") && CR.getClassNumber().equals("353"))
//        			||(CR.getClassType().equals("PHI") && CR.getClassNumber().equals("383"))
//        			||(CR.getClassType().equals("SPA") && CR.getClassNumber().equals("310"))
//        			||(CR.getClassType().equals("SPA") && CR.getClassNumber().equals("312"))
//        			||(CR.getClassType().equals("SPA") && CR.getClassNumber().equals("313"))
//        			||(CR.getClassType().equals("THE") && CR.getClassNumber().equals("313"))
//        			||(CR.getClassType().equals("THE") && CR.getClassNumber().equals("315"))
//        			||(CR.getClassType().equals("THE") && CR.getClassNumber().equals("317"))
//        			||(CR.getClassType().equals("THE") && CR.getClassNumber().equals("319"))
//        			||(CR.getClassType().equals("WMS") && CR.getClassNumber().equals("310"))
//        			||(CR.getClassType().equals("WMS") && CR.getClassNumber().equals("311"))
//        			||(CR.getClassType().equals("WMS") && CR.getClassNumber().equals("314"))
//        			||(CR.getClassType().equals("WMS") && CR.getClassNumber().equals("315"))) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			IntegrativeHumanities=true;
//        		}
//        	}
//        	if(		  (CR.getClassType().equals("BIO") && CR.getClassNumber().equals("336"))
//        			||(CR.getClassType().equals("BIO") && CR.getClassNumber().equals("340"))
//        			||(CR.getClassType().equals("CSC") && CR.getClassNumber().equals("301"))
//        			||(CR.getClassType().equals("EAR") && CR.getClassNumber().equals("312"))
//        			||(CR.getClassType().equals("EAR") && CR.getClassNumber().equals("416"))
//        			||(CR.getClassType().equals("IDS") && CR.getClassNumber().equals("310"))
//        			||(CR.getClassType().equals("LBS") && CR.getClassNumber().equals("380"))
//        			||(CR.getClassType().equals("SMT") && CR.getClassNumber().equals("310"))
//        			||(CR.getClassType().equals("SMT") && CR.getClassNumber().equals("314"))) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			IntegrativeNatural=true;
//        		}
//        	}
//        	if(		  (CR.getClassType().equals("AFS") && CR.getClassNumber().equals("310"))
//        			||(CR.getClassType().equals("AFS") && CR.getClassNumber().equals("311"))
//        			||(CR.getClassType().equals("AFS") && CR.getClassNumber().equals("312"))
//        			||(CR.getClassType().equals("ANT") && CR.getClassNumber().equals("312"))
//        			||(CR.getClassType().equals("ANT") && CR.getClassNumber().equals("334"))
//        			||(CR.getClassType().equals("ANT") && CR.getClassNumber().equals("336"))
//        			||(CR.getClassType().equals("ANT") && CR.getClassNumber().equals("337"))
//        			||(CR.getClassType().equals("ANT") && CR.getClassNumber().equals("338"))
//        			||(CR.getClassType().equals("ANT") && CR.getClassNumber().equals("342"))
//        			||(CR.getClassType().equals("ANT") && CR.getClassNumber().equals("371"))
//        			||(CR.getClassType().equals("APP") && CR.getClassNumber().equals("311"))
//        			||(CR.getClassType().equals("APP") && CR.getClassNumber().equals("318"))
//        			||(CR.getClassType().equals("APP") && CR.getClassNumber().equals("327"))
//        			||(CR.getClassType().equals("APP") && CR.getClassNumber().equals("350"))
//        			||(CR.getClassType().equals("APP") && CR.getClassNumber().equals("335"))
//        			||(CR.getClassType().equals("CHS") && CR.getClassNumber().equals("323"))
//        			||(CR.getClassType().equals("CHS") && CR.getClassNumber().equals("330"))
//        			||(CR.getClassType().equals("CHS") && CR.getClassNumber().equals("335"))
//        			||(CR.getClassType().equals("GEO") && CR.getClassNumber().equals("318"))
//        			||(CR.getClassType().equals("HEA") && CR.getClassNumber().equals("468"))
//        			||(CR.getClassType().equals("HIS") && CR.getClassNumber().equals("340"))
//        			||(CR.getClassType().equals("HIS") && CR.getClassNumber().equals("348"))
//        			||(CR.getClassType().equals("HIS") && CR.getClassNumber().equals("352"))
//        			||(CR.getClassType().equals("HIS") && CR.getClassNumber().equals("354"))
//        			||(CR.getClassType().equals("HIS") && CR.getClassNumber().equals("380"))
//        			||(CR.getClassType().equals("IDS") && CR.getClassNumber().equals("304"))
//        			||(CR.getClassType().equals("IDS") && CR.getClassNumber().equals("318"))
//        			||(CR.getClassType().equals("LBS") && CR.getClassNumber().equals("370"))
//        			||(CR.getClassType().equals("SBS") && CR.getClassNumber().equals("318"))
//        			||(CR.getClassType().equals("WMS") && CR.getClassNumber().equals("318"))) {
//        		if(CR.getGrade().equals("A")||CR.getGrade().equals("B")||CR.getGrade().equals("C")) {
//        			IntegrativeSocial=true;
//        		}
//        	}
//        }
//        request.setAttribute("IntegrativeHumanities", IntegrativeHumanities);
//        request.setAttribute("IntegrativeNatural", IntegrativeNatural);
//        request.setAttribute("IntegrativeSocial", IntegrativeSocial);
//           
//        //Store info in request attribute, before forward to views
//        request.setAttribute("errorString", errorString);
//        //request.setAttribute("courseList", list);
//         
//        // Forward to /WEB-INF/views/productListView.jsp
//        RequestDispatcher dispatcher = request.getServletContext()
//                .getRequestDispatcher("/WEB-INF/views/genEdView.jsp");
//        dispatcher.forward(request, response);
//    }
// 
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        doGet(request, response);
//    }
// 
//}