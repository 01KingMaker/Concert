package event;
import utils.GenericDAO;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import utils.Correspondance;

public class Reservation extends GenericDAO{
    
    @Correspondance(primarykey = true)
    String idReservation;
    @Correspondance
    String idEvent;
    @Correspondance
    String idZone;
    @Correspondance
    Timestamp dateReservation;
    @Correspondance
    int numero;

    public Reservation(String idEvent, String idZone, Timestamp dateReservation, int numero) throws Exception{
        this.setPrefixe("RST");
        this.setNomFonction("seq_Reservation");
        this.setLongPK(7);
        this.setIdReservation(this.construirePK(null));
        this.setIdEvent(idEvent);
        this.setIdZone(idZone);
        this.setDateReservation(dateReservation);
        this.setNumero(numero);
    }
    public String getIdReservation() {
        return idReservation;
    }
    public void setIdReservation(String idReservation) {
        this.idReservation = idReservation;
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
    public Timestamp getDateReservation() {
        return dateReservation;
    }
    public void setSDateReservation(String dateReservation) {
        if(dateReservation == null || dateReservation == "") {
            LocalDateTime localDateTime = LocalDateTime.now();
            String ld = localDateTime.toString();
            ld.replace("T", " ");
            dateReservation = ld;
        }
        this.setDateReservation(Timestamp.valueOf(dateReservation));
    }
    public void setDateReservation(Timestamp dateReservation) {
        this.dateReservation = dateReservation;
    }
    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
}
