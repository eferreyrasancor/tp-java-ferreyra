/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mavha.cursos.java.app.jpa.dao;

import com.mavha.cursos.java.app.jpa.modelo.Empleado;
import com.mavha.cursos.java.app.jpa.modelo.Tarea;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mdominguez
 */
public class EmpleadoDaoJPA implements EmpleadoDao {

    @PersistenceContext(unitName = "lab01")
    private EntityManager em;

    @Override
    public Empleado guardar(Empleado e) {
        //TODO 2.1 implementar el guardado
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        Empleado empGuardado = em.merge(e);
        em.getTransaction().commit();
        em.close();
        return empGuardado;
    }

    @Override
    public void borrar(Integer e) {
        //TODO 2.2 implementar el guardado
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        em.remove(e);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Empleado actualizar(Empleado e) {
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        Empleado empActualizado = em.merge(e);
        em.getTransaction().commit();
        em.close();
        return empActualizado;
    }

    @Override
    public Empleado buscarPorId(Integer id) {
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        Empleado e = em.find(Empleado.class, id);
        em.getTransaction().commit();
        em.close();
        return e;
    }

    @Override
    public List<Empleado> buscarTodos() {
        String consulta = "SELECT e FROM Empleado e";
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        Query query = em.createQuery(consulta);
        List<Empleado> resultado = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return resultado;
    }

    @Override
    public Double salarioPromedioTodos() {
        ////TODO 2.3 Ejecutar una consulta para conocer el salario promedio de todos los empleados de una empresa
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT AVG(e.salarioHora) FROM Empleado e", Double.class);
        Double res = (Double) query.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return res;
    }

    @Override
    public List<Tarea> tareasPendientes(Integer idEmpleado) {
        //TODO 2.4 ejecutar una consulta que retorne todas las tareas que tiene pendiente un Empleado. 
        // Las tareas pendientes son todas las tareas asignadas al empleado, que tiene fecha de fin NULL
        em = ConexionJPA.getInstance().em();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT t FROM Tarea t "
                + "WHERE t.fechaFin IS NULL "
                + "AND t.responsable.id = :empleadoId", Tarea.class);
        query.setParameter("empleadoId", idEmpleado);
        List<Tarea> res = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return res;
    }

}
