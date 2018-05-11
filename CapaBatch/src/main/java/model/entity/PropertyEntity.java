/*
 * The MIT License
 *
 * Copyright 2017 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package model.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name = "PROPERTY")
public class PropertyEntity implements Serializable {

    @Id
    private String id;
    private String name;
    private int floor;
    private String idResidentialUnit;
    //@ManyToOne(cascade = CascadeType.ALL)
    //private ResidentialUnitEntity residentialUnity;
    //@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<HubEntity> hubs;

    
    public PropertyEntity() {
    }

    public PropertyEntity(String id, String name, int floor, String idResidentialUnit) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.idResidentialUnit = idResidentialUnit;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the floor
     */
    public int getFloor() {
        return floor;
    }

    /**
     * @param floor the floor to set
     */
    public void setFloor(int floor) {
        this.floor = floor;
    }

    /**
     * @return the idResidentialUnit
     */
    public String getIdResidentialUnit() {
        return idResidentialUnit;
    }

    /**
     * @param idResidentialUnit the idResidentialUnit to set
     */
    public void setIdResidentialUnit(String idResidentialUnit) {
        this.idResidentialUnit = idResidentialUnit;
    }
    
}
