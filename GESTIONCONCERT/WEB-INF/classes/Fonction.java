package utils;


import java.util.Vector;

import event.Zone;

public class Fonction {
    public void checkIfZoneExists(Vector<String[]> str) throws Exception{
        Zone z = new Zone();
        for(String[] s : str){
            Vector<Zone> matchZones = z.objects(null, "SELECT * FROM ZONES WHERE nom='"+s[0]+"'");
            if(matchZones.size() < 1){ 
                String mess = "Pas de zone nommee "+s[0];
                throw new Exception(mess);
            }
        }
    }
//FONCTION MANAMBOATRA FORMAT DATE AVU AM LOCALDATE TIME
    public static java.sql.Timestamp convertStringToTimestamp(String date){
        date = date.replace("T", " ");
        date = date + ":00";
        return java.sql.Timestamp.valueOf(date);
    }

    public static Vector<String> filledBlank(String str){
        Vector<String> retour = new Vector<>();
        String[] g = str.split(">>");
        String[] debut = getCharNumber(g[0]);
        String[] fin = getCharNumber(g[1]);
        int a = Integer.parseInt(debut[1]);
        int b = Integer.parseInt(fin[1]);
        while(a <= b){
            String newS = debut[0]+(a);
            retour.add(newS);
            a++;
        }
        return retour;
    }
    public static String[] manambatra(String[] org, Vector<String> vide){
        String[] retour = new String[org.length+vide.size()];
        for(int i =0;i< org.length-1;i++){
            retour[i] = org[i];
        }
        for(int i=org.length; i<vide.size();i++){
            retour[i] = vide.get(i);
        }
        return retour;
    } 
    public static Vector<String> arrayVectString(String[] aa){
        Vector<String> e = new Vector<>();
        for(String a : aa) { e.add(a); }
        return e;
    }
//HITO NO MIANTSO
    public static Vector<String[]> separate(String chaine){
        String[] gr = chaine.split(",");
        Vector<String> group = arrayVectString(gr);
        for (int i = 0; i < group.size(); i++) {
            if(group.get(i).contains(">>")){
                Vector<String> blank = filledBlank(group.get(i));
                group.remove(group.get(i));
                group.addAll(blank);
            }
        }
        Vector<String[]> zoneXnumero = new Vector<>();
        for(String s : group){
            String[] ss = getCharNumber(s);
            zoneXnumero.add(ss);
        }
        return zoneXnumero;
    }
    public static String[] getCharNumber(String test){
        test = test.replace("[", "");
        test = test.replace("]", "");
        String[] retour = new String[2];
        int x = separer(test);
        String zone = test.substring(0, x+1);
        String nombre = test.substring(x+1, test.length());
        retour[0] = zone;
        retour[1] = nombre;
        return retour;
    }
    public static int separer(String a){
        for(int i=a.length()-1; i>=0; i--){
            try{
                String x = (a.charAt(i))+"";
                int xx = Integer.valueOf(x);
            }
            catch(Exception e){
                return i;
            }
        }
        return 0;
    }
}
