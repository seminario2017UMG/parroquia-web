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
import org.parroquiasanjuan.facade.IdentificationTypeFacadeLocal;
import org.parroquiasanjuan.mdl.IdentificationType;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author lveliz
 */
@Named(value = "identificationTypeController")
@RequestScoped
public class IdentificationTypeController {

    @EJB
    private IdentificationTypeFacadeLocal facadeLocal;
    private IdentificationType identificationType;
    private List<IdentificationType> identificationTypes = new ArrayList();
    FacesContext context;

    @PostConstruct
    public void init() {
        this.identificationType = new IdentificationType();
        this.identificationTypes = this.facadeLocal.findAll();
        this.context = FacesContext.getCurrentInstance();
    }

    public IdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(IdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public List<IdentificationType> getIdentificationTypes() {
        return identificationTypes;
    }

    public void setIdentificationTypes(List<IdentificationType> identificationTypes) {
        this.identificationTypes = identificationTypes;
    }

    public void create() {
        try {
            this.facadeLocal.create(this.identificationType);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
        }
    }

    public void edit(RowEditEvent event) {
        try {

            IdentificationType i = (IdentificationType) event.getObject();
            i.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.facadeLocal.edit(i);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
        }
    }

    public void remove(RowEditEvent event) {

        try {

            IdentificationType i = (IdentificationType) event.getObject();
            i.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            i.setStatus(false);

            this.facadeLocal.edit(i);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUMARY", "DETAIL"));

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SUMARY", e.getMessage()));
        }

    }

}
