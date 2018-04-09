package model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ALERTA")
public class AlertaEntity implements Serializable {

    @Id
    private String id;
    
    private String deviceId;

    @Temporal(TemporalType.DATE)
    private Date time;
    
    private int type;
    
    public AlertaEntity() {
    }

    public AlertaEntity(String id, String deviceId, Date time, int type) {
        this.id = id;
        this.deviceId = deviceId;
        this.time = time;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
