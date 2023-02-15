package event;

import event.Promotion;
import utils.Correspondance;
import utils.GenericDAO;

@Correspondance(nomTable = "dispositionplace")
public class DispositionPlace extends GenericDAO{

    @Correspondance(primarykey = true)
    String idDisposition;
    @Correspondance
    String idEvent;
    @Correspondance
    String idZone;
    @Correspondance
    int debut;
    @Correspondance
    int fin;
    @Correspondance
    String idPromotion;

    public DispositionPlace(String idEvent ,String idZone, int debut, int fin, String idPromotion) throws Exception{
        this.setNomFonction("seq_Disposition");
        this.setPrefixe("DP");
        this.setIdDisposition(this.construirePK(null));
        this.setIdZone(idZone);
        this.setDebut(debut);
        this.setIdPromotion(idPromotion);
        this.setFin(fin);
        this.setIdEvent(idEvent);
    }
    public DispositionPlace(){}
    public void setIdPromotion(String idPromotion) {
        this.idPromotion = idPromotion;
    }
    public String getIdPromotion() {
        return idPromotion;
    }
    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }
    public String getIdEvent() {
        return idEvent;
    }
    public String getIdDisposition() {
        return idDisposition;
    }
    public void setIdDisposition(String idDisposition) {
        this.idDisposition = idDisposition;
    }
    public String getIdZone() {
        return idZone;
    }
    public void setIdZone(String idZone) {
        this.idZone = idZone;
    }
    public int getDebut() {
        return debut;
    }
    public void setDebut(int debut) {
        this.debut = debut;
    }
    public int getFin() {
        return fin;
    }
    public void setFin(int fin) {
        this.fin = fin;
    }
    
}
