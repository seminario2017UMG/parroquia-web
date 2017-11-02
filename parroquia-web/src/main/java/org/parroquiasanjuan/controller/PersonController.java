package org.parroquiasanjuan.controller;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.parroquiasanjuan.facade.PersonFacadeLocal;
import org.parroquiasanjuan.mdl.Person;
import org.parroquiasanjuan.mdl.User;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author lveliz
 */
@Named(value = "personController")
@RequestScoped
public class PersonController implements Serializable {

    private static final long serialVersionUID = 7834514596073425182L;

    @EJB
    private PersonFacadeLocal facadeLocal;
    private Person person;
    private List<Person> people = new ArrayList();
    private FacesContext context;
    private User user;

    @PostConstruct
    public void init() {
        this.person = new Person();
        this.people = this.facadeLocal.findAll();
        this.context = FacesContext.getCurrentInstance();
        this.user = (User) context.getExternalContext().getSessionMap().get("logedInUser");
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public void create() {

        try {

            this.person.setStatus(true);
            this.person.setUpdatedBy(this.user.getIdUser());
            this.person.setInsertedBy(this.user.getIdUser());
            this.person.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.person.setInsertedOn(new Timestamp(System.currentTimeMillis()));

            this.facadeLocal.create(this.person);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SI", ""));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", ""));
        }

    }

    public void edit(RowEditEvent event) {
        try {

            Person p = (Person) event.getObject();
            p.setUpdatedBy(this.user.getIdUser());
            p.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.facadeLocal.edit(p);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
        }
    }
    
    
    public void remove(RowEditEvent event) {

        try {

            Person p = (Person) event.getObject();
            p.setUpdatedBy(this.user.getIdUser());
            p.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            p.setStatus(false);

            this.facadeLocal.edit(p);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUMARY", "DETAIL"));

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SUMARY", e.getMessage()));
        }

    }

}
