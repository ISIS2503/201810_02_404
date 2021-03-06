/*
 * The MIT License
 *
 * Copyright 2016 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
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
package persistence;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import model.entity.ResidentialUnitEntity;

public class Persistencer<T, PK> {

    private static final Logger LOG = Logger.getLogger(Persistencer.class.getName());
    protected Class<T> entityClass;
    protected EntityManager entityManager;

    public Persistencer() {

        this.entityManager 
                = JPAConnection.CONNECTION.getEntityManager();

    }

    public T add(T entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entity;

    }

    public T update(T entity) {
        try {
            entityManager.merge(entity);
        } catch (Exception e) {
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entity;
    }

    public T find(PK id) {
        T entity;
        try {
            entity = entityManager.find(entityClass, id);
        } catch (NoResultException | NonUniqueResultException e) {
            entity = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entity;
    }

    public List<T> all() {
        List<T> entities;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c";
        Query query = entityManager.createQuery(queryString);
        try {
            entities = query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            entities = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entities;
    }

    public Boolean delete(PK id) {
        try {
            T entity = entityManager.find(entityClass, id);
            entityManager.getTransaction().begin();
            this.entityManager.remove(entity);
            entityManager.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            LOG.log(Level.WARNING, e.getMessage());
            return false;
        }
    }

    
    public ResidentialUnitEntity findResidentialUnitById(String id) {
        ResidentialUnitEntity entity;
        String queryString = "Select c FROM ResidentialUnitEntity c where c.id = :id";
        Query query = entityManager.createQuery(queryString).setParameter("id", id);
        try {
            entity = (ResidentialUnitEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException e) {
            entity = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entity;
    }
    
    
    
    public List<T> findAlertByResidentialUnitId(String id) {
        List<T> entities;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.idResidentialUnity= :id";
        Query query = entityManager.createQuery(queryString).setParameter("id", id);
        try {
            entities = query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            entities = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entities;
    }

    public List<T> findAlertByPropertyId(String id) {
        List<T> entities;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.idProperty = :id";
        Query query = entityManager.createQuery(queryString).setParameter("id", id);
        try {
            entities = query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            entities = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entities;
    }
     
    public List<T> findAlertByLockId(String id) {
        List<T> entities;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.idLock = :id";
        Query query = entityManager.createQuery(queryString).setParameter("id", id);
        try {
            entities = query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            entities = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entities;
    }
    
    public List<T> findScheduleByUserId(String id) {
        List<T> entities;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.userId = :id";
        Query query = entityManager.createQuery(queryString).setParameter("id", id);
        try {
            entities = query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            entities = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entities;
    }
    
    public List<T> findPassByNumber(String id) {
        List<T> entities;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.passNumber = :id";
        Query query = entityManager.createQuery(queryString).setParameter("id", id);
        try {
            entities = query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            entities = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entities;
    }

    public List<T> findPassByLockId(String id) {
        List<T> entities;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.idLock = :id";
        Query query = entityManager.createQuery(queryString).setParameter("id", id);
        try {
            entities = query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            entities = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entities;
    }

    public List<T> findLockByHubId(String id) {
        List<T> entities;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.idHub= :id";
        Query query = entityManager.createQuery(queryString).setParameter("id", id);
        try {
            entities = query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            entities = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entities;
    }
    
    public List<T> findHubByPropertyId(String id) {
        List<T> entities;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.idProperty = :id";
        Query query = entityManager.createQuery(queryString).setParameter("id", id);
        try {
            entities = query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            entities = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entities;
    }
    
    public List<T> findPropertyByResidentialUnityId(String id) {
        List<T> entities;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.idResidentialUnit = :id";
        Query query = entityManager.createQuery(queryString).setParameter("id", id);
        try {
            entities = query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            entities = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entities;
    }
    
    public List<T> findResidentialUnityByHood(String id) {
        List<T> entities;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.neighborhood = :id";
        Query query = entityManager.createQuery(queryString).setParameter("id", id);
        try {
            entities = query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            entities = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entities;
    }
    
    public List<T> findByConjuntoId(String id) {
        List<T> entities;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.conjunto.id = :id";
        Query query = entityManager.createQuery(queryString).setParameter("id", id);
        try {
            entities = query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            entities = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entities;
    }

    public List<T> findByInmuebleId(String id) {
        List<T> entities;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.inmueble.id = :id";
        Query query = entityManager.createQuery(queryString).setParameter("id", id);
        try {
            entities = query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            entities = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entities;
    }
    
    public List<T> findByneighborhoodId(String id) {
        List<T> entities;
        String queryString = "Select c FROM " + entityClass.getSimpleName() + " c where c.neighborhood = :id";
        Query query = entityManager.createQuery(queryString).setParameter("id", id);
        try {
            entities = query.getResultList();
        } catch (NoResultException | NonUniqueResultException e) {
            entities = null;
            LOG.log(Level.WARNING, e.getMessage());
        }
        return entities;
    }
}
