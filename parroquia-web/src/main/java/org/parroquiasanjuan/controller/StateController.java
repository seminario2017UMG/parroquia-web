package org.parroquiasanjuan.controller;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.parroquiasanjuan.facade.StateFacadeLocal;
import org.parroquiasanjuan.mdl.State;
import org.parroquiasanjuan.mdl.User;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author lveliz
 */
@Named(value = "stateController")
@RequestScoped
public class StateController implements Serializable {

    private static final long serialVersionUID = 5639175045493514349L;

    @EJB
    private StateFacadeLocal facadeLocal;
    private State state;
    private List<State> states;
    FacesContext context;
    User user;

    @PostConstruct
    public void init() {

        this.state = new State();
        this.states = new ArrayList();
        this.states = this.facadeLocal.findAll();
        this.context = FacesContext.getCurrentInstance();
        this.user = (User) this.context.getExternalContext().getSessionMap().get("logedInUser");

    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public void create() {
        try {

            this.state.setStatus(true);
            this.state.setUpdatedBy(this.user.getIdUser());
            this.state.setInsertedBy(this.user.getIdUser());
            this.state.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.state.setInsertedOn(new Timestamp(System.currentTimeMillis()));

            this.facadeLocal.create(this.state);
            this.context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Operación Exitosa",
                    "Se ha creado el nuevo registro en la base de datos."
            ));

        } catch (Exception e) {
            this.context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Algo salio mal",
                    "Lo sentimos ha ocurrido un error."
            ));
        }

    }

    public void edit(RowEditEvent event) {

        try {

            State s = (State) event.getObject();
            s.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            s.setUpdatedBy(this.user.getIdUser());

            this.facadeLocal.edit(s);
            this.context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Operación Exitosa",
                    "Se ha edito el registro."
            ));

        } catch (Exception e) {
            this.context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Algo salio mal",
                    "Lo sentimos ha ocurrido un error."
            ));
        }

    }

    public void remove(State s) {

        try {

            s.setStatus(false);
            s.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.state.setUpdatedBy(this.user.getIdUser());

            this.facadeLocal.edit(s);
            this.context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Operación Exitosa",
                    "Se ha removido el registro."
            ));

        } catch (Exception e) {
            this.context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Algo salio mal",
                    "Lo sentimos ha ocurrido un error."
            ));
        }

    }
}
