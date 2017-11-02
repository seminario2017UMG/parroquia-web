package org.parroquiasanjuan.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.parroquiasanjuan.facade.MaritalStatusFacadeLocal;
import org.parroquiasanjuan.mdl.MaritalStatus;
import org.parroquiasanjuan.mdl.User;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author lveliz
 */
@Named(value = "maritalStatusController")
@RequestScoped
public class MaritalStatusController {

    @EJB
    private MaritalStatusFacadeLocal facadeLocal;
    private MaritalStatus maritalStatus;
    private List<MaritalStatus> maritalStatuses;
    FacesContext context;
    User user;

    @PostConstruct
    public void init() {
        
        this.maritalStatus = new MaritalStatus();
        this.maritalStatuses = new ArrayList();
        this.maritalStatuses = this.facadeLocal.findAll();
        this.context = FacesContext.getCurrentInstance();
        this.user = (User) this.context.getExternalContext().getSessionMap().get("logedInUser");
        
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public List<MaritalStatus> getMaritalStatuses() {
        return maritalStatuses;
    }

    public void setMaritalStatuses(List<MaritalStatus> maritalStatuses) {
        this.maritalStatuses = maritalStatuses;
    }

    public void create() {
        
        try {

            this.maritalStatus.setStatus(true);
            this.maritalStatus.setUpdatedBy(this.user.getIdUser());
            this.maritalStatus.setInsertedBy(this.user.getIdUser());
            this.maritalStatus.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.maritalStatus.setInsertedOn(new Timestamp(System.currentTimeMillis()));

            this.facadeLocal.create(this.maritalStatus);
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

            MaritalStatus m = (MaritalStatus) event.getObject();
            m.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            m.setUpdatedBy(this.user.getIdUser());

            this.facadeLocal.edit(m);
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

    public void remove(MaritalStatus m) {

        try {

            m.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            m.setUpdatedBy(this.user.getIdUser());
            m.setStatus(false);

            this.facadeLocal.edit(m);
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
