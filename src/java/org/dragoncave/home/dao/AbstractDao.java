/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dragoncave.home.dao;

/**
 *
 * @author Rider1
 */
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao <PK extends Serializable, T>{
    
    private final Class<T> persistentClass;
    
    @SuppressWarnings("unchecked")
    public AbstractDao(){
        this.persistentClass = (Class<T>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
    /*
    @Autowired
    private SessionFactory sessionFactory;
    
    protected SessionFactory getSessionFactory(){
        
    }*/
}
