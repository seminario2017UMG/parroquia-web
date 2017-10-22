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
    private List<MaritalStatus> maritalStatuses = new ArrayList();
    FacesContext context;

    @PostConstruct
    public void init() {
        this.maritalStatus = new MaritalStatus();
        this.maritalStatuses = this.facadeLocal.findAll();
        this.context = FacesContext.getCurrentInstance();
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
            this.facadeLocal.create(this.maritalStatus);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
        }
    }

    public void edit(RowEditEvent event) {
        try {

            MaritalStatus m = (MaritalStatus) event.getObject();
            m.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.facadeLocal.edit(m);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
        }
    }

    public void remove(RowEditEvent event) {

        try {

            MaritalStatus m = (MaritalStatus) event.getObject();
            m.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            m.setStatus(false);

            this.facadeLocal.edit(m);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUMARY", "DETAIL"));

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SUMARY", e.getMessage()));
        }

    }
}
