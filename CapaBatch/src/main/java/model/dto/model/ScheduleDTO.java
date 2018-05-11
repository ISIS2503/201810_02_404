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
package model.dto.model;

import java.util.Date;

/**
 *
 * @author Dexcrash
 */
public class ScheduleDTO {
    
    private String id;    
    private Date minHour;
    private Date maxHour;
    private String idUser;
    private String idLock;

    public ScheduleDTO() {
    }

    public ScheduleDTO(String id, Date minHour, Date maxHour, String idUser, String idLock) {
        this.id = id;
        this.minHour = minHour;
        this.maxHour = maxHour;
        this.idUser = idUser;
        this.idLock = idLock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getMinHour() {
        return minHour;
    }

    public void setMinHour(Date minHour) {
        this.minHour = minHour;
    }

    public Date getMaxHour() {
        return maxHour;
    }

    public void setMaxHour(Date maxHour) {
        this.maxHour = maxHour;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdLock() {
        return idLock;
    }

    public void setIdLock(String idLock) {
        this.idLock = idLock;
    }
    
}
