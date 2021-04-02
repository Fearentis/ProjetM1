import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class InsertData
{

  Connection cnx = null;
  
  public ArrayList<String> readDataFromFile(String nomF) {
	  ArrayList<String> res = new ArrayList<String>(); 
	  File f = new File(nomF);
	  try {
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String ligne ;

		while (br.ready()) 
			{
			ligne=br.readLine();
			res.add(ligne);			
			}
		
	} catch (IOException e) {
		e.printStackTrace();
	}
	  finally {
		  return res;
	  }  
  }
 
  
  
// ===========================================================
public void Init()
	{
	 String url = "jdbc:mysql://localhost:3306/cac40";
     System.out.println("tentative de connexion : \n\n");

  try
	{
        Class.forName("com.mysql.jdbc.Driver");
  	    cnx = DriverManager.getConnection(url,"root","");
  	    //con1 = DriverManager.getConnection(url,"root","");
	    System.out.println("Connected.");
    }
    catch(Exception ex)
    {
      System.out.println("SQLException: " + ex);
  	}

    System.out.println("MySql Connect.");
} // fin du constructeur


public void insereEnBase(ArrayList<String> data) {
	String[] morceaux;
	String idAction,jour,heure;
	Float p1,p2,p3,p4;
	int vol;
	Timestamp ts;
	
	try {
		Statement stmt = cnx.createStatement();
		int i=0;
		for (String ligne : data) {
		
			morceaux = ligne.split(";");
			idAction = morceaux[0];
			jour = morceaux[1];
				{
				String[] pieces=jour.split("/");
				jour=""+(Integer.parseInt(pieces[2])+2000);
				jour+="-"+pieces[1]+"-"+pieces[0];
				}
			heure = morceaux[2];
			heure=heure+":00";
			
			ts = Timestamp.valueOf(jour+" "+heure);
					
			p1 = Float.parseFloat(morceaux[3]);
			p2 = Float.parseFloat(morceaux[4]);
			p3 = Float.parseFloat(morceaux[5]);
			p4 = Float.parseFloat(morceaux[6]);
			vol = Integer.parseInt(morceaux[7]);
			
		
			String req=new String("INSERT INTO PRIXACTION VALUES ('"+ts+"','"+idAction+"','"+p4+"','"+vol+"');");
			System.out.println(req);
			stmt.executeQuery(req);
			}
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

// -- -- --- ---- ----- lancement de l'applilcation server
public static void main(String[] args)
  {
   java.sql.Date  ddj;
   InsertData app = new InsertData();
   ArrayList<String> content ;
   
   content=app.readDataFromFile("SRD_02032018.txt");
   
   File f = new File("liste2.txt");
	  try {
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		while (br.ready()) 
			{
			
			 content=app.readDataFromFile(br.readLine());
					
			}
		
	} catch (IOException e) {
		e.printStackTrace();
	}
  
   app.Init();
   app.insereEnBase(content);
   
   
   } // fin du main

} // fin de la classe

