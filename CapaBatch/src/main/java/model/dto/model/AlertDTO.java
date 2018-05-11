package model.dto.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlertDTO {

    private String id;
    private int type;
    private Date date;
    private String idProperty;
    private String idLock;
    private String idResidentialUnity;

    public AlertDTO(){
    }

    public AlertDTO(String id, int type, Date date, String idProperty, String idLock, String idResidentialUnity) {
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
