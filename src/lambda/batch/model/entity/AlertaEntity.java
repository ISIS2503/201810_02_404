package lambda.batch.model.entity;

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

    @Temporal(TemporalType.DATE)
    private Date time;
    
    private int type;

    public AlertaEntity() {
    }

    public AlertaEntity(String id, Date time, int type) {
        this.id = id;
        this.time = time;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
