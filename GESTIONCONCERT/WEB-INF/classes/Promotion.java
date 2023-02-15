package event;
import utils.GenericDAO;

import java.sql.Timestamp;

import utils.Correspondance;

@Correspondance(nomTable = "promotion")
public class Promotion extends GenericDAO{

    @Correspondance(primarykey = true)
    String idPromotion;
    @Correspondance
    String idEvent;
    @Correspondance
    String idZone;
    @Correspondance
    int promotion;
    @Correspondance
    Timestamp debutPromotion;
    @Correspondance
    Timestamp finPromotion;

    public Promotion(String idEvent, String idZone, int promotion, Timestamp debutPromotion, Timestamp finPromotion) throws Exception{
        this.setNomFonction("seq_promo");
        this.setLongPK(7);
        this.setPrefixe("PM");
        this.setIdPromotion(this.construirePK(null));
        this.setIdEvent(idEvent);
        this.setIdZone(idZone);
        this.setPromotion(promotion);
        this.setDebutPromotion(debutPromotion);
        this.setFinPromotion(finPromotion);
    }
    public String getIdPromotion() {
        return idPromotion;
    }
    public void setIdPromotion(String idPromotion) {
        this.idPromotion = idPromotion;
    }
    public String getIdEvent() {
        return idEvent;
    }
    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }
    public String getIdZone() {
        return idZone;
    }
    public void setIdZone(String idZone) {
        this.idZone = idZone;
    }
    public int getPromotion() {
        return promotion;
    }
    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }
    public Timestamp getDebutPromotion() {
        return debutPromotion;
    }
    public void setDebutPromotion(Timestamp debutPromotion) {
        this.debutPromotion = debutPromotion;
    }
    public Timestamp getFinPromotion() {
        return finPromotion;
    }
    public void setFinPromotion(Timestamp finPromotion) {
        this.finPromotion = finPromotion;
    }
    
}
