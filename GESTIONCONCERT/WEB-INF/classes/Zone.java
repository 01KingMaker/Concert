package event;
import java.sql.Timestamp;

import utils.Correspondance;
import utils.GenericDAO;
import java.util.Vector;

@Correspondance(nomTable = "Zones")
public class Zone extends GenericDAO{

    @Correspondance(primarykey = true)
    String idZone;
    @Correspondance
    String nom;
    

    public Boolean ifZoneExist(){
        return true;
    }
    public void checkIfInZone(Vector<String[]> places, String idEvent) throws Exception{
        DispositionPlace dispositionPlace = new DispositionPlace();
        for(String[] s : places){
            Zone z = new Zone();
            z.setNom(s[0]);
            Vector<Zone> o = z.objects(null, "SELECT * FROM ZONES WHERE nom='"+s[0]+"'");
            if(o.size() < 1){
                String ex = "La zone "+s[0]+" n'existe pas";
                throw new Exception(ex);
            }
            z = o.get(0);
            dispositionPlace.setIdEvent(idEvent);
            dispositionPlace.setIdZone(z.getIdZone());
            Vector<DispositionPlace> dp = dispositionPlace.objects(null, "SELECT * FROM dispositionPlace WHERE idEvent='"+idEvent+"' and idZone='"+z.getIdZone()+"'");
           // Object[] x = dispositionPlace.findBy(null);
            DispositionPlace dispositionPlace2 = dp.get(0);
            if(Integer.valueOf(s[1]) > dispositionPlace2.getFin() || Integer.valueOf(s[1]) < dispositionPlace2.getDebut()){ 
                String error = "Place n°"+s[1]+" de la zone "+s[0]+" n'existe pas";
                throw new Exception(error); 
            }
        }
    }

    public Zone( String nom) throws Exception{
        this.setNomFonction("seq_zone");
        this.setPrefixe("ZN");
        this.setIdZone(this.construirePK(null));
        this.setNom(nom);
    }
    public Zone(){}

    public void setIdZone(String idZone) {
        this.idZone = idZone;
    }
    public String getIdZone() {
        return idZone;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getNom() {
        return nom;
    }
}
