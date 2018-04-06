package model.dto.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlertaDTO {

    private String id;
    private String deviceId;
    private Date time;
    private int type;
    private InmuebleDTO inmueble;

    public AlertaDTO() {
    }

    public AlertaDTO(String id, String deviceId, Date time, int type, InmuebleDTO inmueble) {
        this.id = id;
        this.deviceId = deviceId;
        this.time = time;
        this.type = type;
        this.inmueble = inmueble;
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

    public InmuebleDTO getInmueble() {
        return inmueble;
    }

    public void setInmueble(InmuebleDTO inmueble) {
        this.inmueble = inmueble;
    }
}
