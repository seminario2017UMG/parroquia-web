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
import org.parroquiasanjuan.mdl.User;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author lveliz
 */
@RequestScoped
@Named(value = "bloodTypeController")
public class BloodTypeController implements Serializable {

    private static final long serialVersionUID = 2623206316484351519L;

    @EJB
    private BloodTypeFacadeLocal facadeLocal;
    private BloodType bloodType;
    private List<BloodType> bloodTypes = new ArrayList();
    private FacesContext context;
    private User user;

    @PostConstruct
    public void init() {
        this.bloodType = new BloodType();
        this.bloodTypes = this.facadeLocal.findAll();
        this.context = FacesContext.getCurrentInstance();
        this.user = (User) context.getExternalContext().getSessionMap().get("logedInUser");
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

            this.bloodType.setStatus(true);
            this.bloodType.setUpdatedBy(this.user.getIdUser());
            this.bloodType.setInsertedBy(this.user.getIdUser());
            this.bloodType.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.bloodType.setInsertedOn(new Timestamp(System.currentTimeMillis()));

            this.facadeLocal.create(this.bloodType);
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

            BloodType b = (BloodType) event.getObject();
            b.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            b.setInsertedBy(this.user.getIdUser());

            this.facadeLocal.edit(b);
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

    public void remove(BloodType b) {

        try {

            b.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            b.setUpdatedBy(this.user.getIdUser());
            b.setStatus(false);

            this.facadeLocal.edit(b);
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
