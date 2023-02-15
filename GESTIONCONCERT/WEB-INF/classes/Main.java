package affi;
import base.ConnexionPOSTGRES;
import event.*;
import utils.Fonction;

import java.sql.Connection;
import java.util.Vector;

public class Main{
    
    public static void main(String[] args) throws Exception{
        String res = "[A2],[A23],[A14]>>[A20]";
        //String[] a = res.split(">>");
        //System.out.println(a[0]+" ok "+a[1]);
        Vector<String[]> rep = Fonction.separate(res);
        for(String[] s : rep){
            System.out.println(s[0]+" "+s[1]);
        }
        
        
            Zone z = new Zone();
            z.setNom("H");
        
            Vector<Zone> zs = z.objects(null, "SELECT * FROM ZONES WHERE nom='"+z.getNom()+"'");
            if(zs.size() < 1) System.out.println("PAS DE CORRESPONDANCE");
            Zone z1 = (Zone) zs.get(0);
            
            System.out.println(z1.getIdZone());
     
        // z.checkIfInZone(rep, "EV00001");
      //  String a = "1";
      
        //Object[] o = getCharNumber("[w1]");
        
        //System.out.println(( o[0])+" "+(o[1]));
        // Connection c = ConnexionPOSTGRES.enterToBdd();

        // Event mahaleo = new Event("Mahaleo", "2023-02-02 22:10:11", "MAHAMASINA - TANA");
        // mahaleo.save(c);
        // Event lola = new Event("Lola", "2023-03-12 16:20:11", "ANTSONJOBE - TANA");
        // lola.save(c);

        // Zone A = new Zone("A");
        // A.save(c);
        // Zone B = new Zone("B");
        // B.save(c);
        // Zone C = new Zone("C");
        // C.save(c);

        // DispositionPlace A1 = new DispositionPlace(mahaleo.getIdEvent() ,A.getIdZone(), 1, 50, null);
        // A1.save(c);
        // DispositionPlace B1 = new DispositionPlace(mahaleo.getIdEvent() ,B.getIdZone(), 51, 100, null);
        // B1.save(c);
        // DispositionPlace C1 = new DispositionPlace(mahaleo.getIdEvent() ,C.getIdZone(), 101, 150, null);
        // C1.save(c);
        
        // c.close();
    }
}