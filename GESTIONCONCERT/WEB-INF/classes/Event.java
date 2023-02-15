package event;
import java.sql.Timestamp;

import event.DispositionPlace;
import utils.Correspondance;
import utils.GenericDAO;

@Correspondance(nomTable = "Event")
public class Event extends GenericDAO{

    @Correspondance(primarykey = true)
    String idEvent;
    @Correspondance
    String nom;
    @Correspondance
    Timestamp dateEvent;
    @Correspondance
    String lieu;
    
    public Event(String nom, String dateEvent, String lieu) throws Exception{
        this.setNomFonction("seq_zone");
        this.setLongPK(7);
        this.setPrefixe("EV");
        this.setIdEvent(this.construirePK(null));
        this.setStringDate(dateEvent);
        this.setNom(nom);
        this.setLieu(lieu);
    }

    public Event(){}

    
    public void setStringDate(String date){
        Timestamp timestamp = Timestamp.valueOf(date);
        this.setDateEvent(timestamp);
    }
    public void setDateEvent(Timestamp dateEvent) {
        this.dateEvent = dateEvent;
    }
    public Timestamp getDateEvent() {
        return dateEvent;
    }
    public String getIdEvent() {
        return idEvent;
    }
    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }
    
}