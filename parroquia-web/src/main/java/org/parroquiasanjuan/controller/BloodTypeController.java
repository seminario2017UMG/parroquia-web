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
import org.parroquiasanjuan.facade.BloodTypeFacadeLocal;
import org.parroquiasanjuan.mdl.BloodType;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author lveliz
 */
@Named(value = "bloodTypeController")
@RequestScoped
public class BloodTypeController implements Serializable {

    private static final long serialVersionUID = 2623206316484351519L;

    @EJB
    private BloodTypeFacadeLocal facadeLocal;
    private BloodType bloodType;
    private List<BloodType> bloodTypes = new ArrayList();
    private FacesContext context;

    @PostConstruct
    public void init() {
        this.bloodType = new BloodType();
        this.bloodTypes = this.facadeLocal.findAll();
        this.context = FacesContext.getCurrentInstance();
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public List<BloodType> getBloodTypes() {
        return bloodTypes;
    }

    public void setBloodTypes(List<BloodType> bloodTypes) {
        this.bloodTypes = bloodTypes;
    }

    public void create() {
        try {
            this.facadeLocal.create(this.bloodType);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
        }
    }

    public void edit(RowEditEvent event) {
        try {

            BloodType b = (BloodType) event.getObject();
            b.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.facadeLocal.edit(b);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
        }
    }

    public void remove(RowEditEvent event) {

        try {

            BloodType b = (BloodType) event.getObject();
            b.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            b.setStatus(false);

            this.facadeLocal.edit(b);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUMARY", "DETAIL"));

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SUMARY", e.getMessage()));
        }

    }

}
