/*
 * The MIT License
 *
 * Copyright 2018 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author Dexcrash
 */
@Entity
@Table(name = "PASS")
public class PassEntity implements Serializable{
    
    
    @Id
    private String id;
    private int passNumber;    
    private String idUser;
    private String idLock;
    //@ManyToOne(cascade = CascadeType.ALL)
    //private LockEntity lock;
    //@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    //private ScheduleEntity schedule;    

    public PassEntity() {
    }

    public PassEntity(String id, int passNumber, String idUser, String idLock) {
        this.id = id;
        this.passNumber = passNumber;
        this.idUser = idUser;
        this.idLock = idLock;
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
     * @return the passNumber
     */
    public int getPassNumber() {
        return passNumber;
    }

    /**
     * @param passNumber the passNumber to set
     */
    public void setPassNumber(int passNumber) {
        this.passNumber = passNumber;
    }

    /**
     * @return the idUser
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     * @param idUser the idUser to set
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    /**
     * @return the idLock
     */
    public String getIdLock() {
        return idLock;
    }

    /**
     * @param idLock the idLock to set
     */
    public void setIdLock(String idLock) {
        this.idLock = idLock;
    }
      
}
