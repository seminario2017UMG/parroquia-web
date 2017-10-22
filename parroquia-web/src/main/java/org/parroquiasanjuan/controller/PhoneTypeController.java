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
import org.parroquiasanjuan.facade.PhoneTypeFacadeLocal;
import org.parroquiasanjuan.mdl.PhoneType;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author lveliz
 */
@Named(value = "phoneTypeController")
@RequestScoped
public class PhoneTypeController implements Serializable {

    private static final long serialVersionUID = 3266171823510580593L;

    @EJB
    private PhoneTypeFacadeLocal facadeLocal;
    private PhoneType phoneType;
    private List<PhoneType> phoneTypes = new ArrayList();
    private FacesContext context;

    @PostConstruct
    public void init() {
        this.phoneType = new PhoneType();
        this.phoneTypes = this.facadeLocal.findAll();
        this.context = FacesContext.getCurrentInstance();
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public List<PhoneType> getPhoneTypes() {
        return phoneTypes;
    }

    public void setPhoneTypes(List<PhoneType> phoneTypes) {
        this.phoneTypes = phoneTypes;
    }

    public void create() {

        try {
            this.facadeLocal.create(this.phoneType);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUMARY", "DETAIL"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SUMARY", e.getMessage()));
        }

    }

    public void edit(RowEditEvent event) {

        try {

            PhoneType p = (PhoneType) event.getObject();
            p.setUpdatedOn(new Timestamp(System.currentTimeMillis()));

            this.facadeLocal.edit(p);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUMARY", "DETAIL"));

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SUMARY", "DETAIL"));
        }

    }

    public void remove(RowEditEvent event) {

        try {

            PhoneType p = (PhoneType) event.getObject();
            p.setStatus(false);
            p.setUpdatedOn(new Timestamp(System.currentTimeMillis()));

            this.facadeLocal.edit(p);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUMARY", "DETAIL"));

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SUMARY", e.getMessage()));
        }

    }

}
