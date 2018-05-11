package model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ALERT")
public class AlertEntity implements Serializable {

    @Id
    private String id;
    private int type;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String idProperty;
    private String idLock;
    private String idResidentialUnity;

    public AlertEntity(){
        
    }

    public AlertEntity(String id, int type, Date date, String idProperty, String idLock, String idResidentialUnity) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.idProperty = idProperty;
        this.idLock = idLock;
        this.idResidentialUnity = idResidentialUnity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIdProperty() {
        return idProperty;
    }

    public void setIdProperty(String idProperty) {
        this.idProperty = idProperty;
    }

    public String getIdLock() {
        return idLock;
    }

    public void setIdLock(String idLock) {
        this.idLock = idLock;
    }

    public String getIdResidentialUnity() {
        return idResidentialUnity;
    }

    public void setIdResidentialUnity(String idResidentialUnity) {
        this.idResidentialUnity = idResidentialUnity;
    }
    
    
}
