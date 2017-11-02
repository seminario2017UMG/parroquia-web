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
import org.parroquiasanjuan.mdl.User;
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
    private List<PhoneType> phoneTypes;
    private FacesContext context;
    private User user;

    @PostConstruct
    public void init() {

        this.phoneType = new PhoneType();
        this.phoneTypes = new ArrayList();
        this.phoneTypes = this.facadeLocal.findAll();
        this.context = FacesContext.getCurrentInstance();
        this.user = (User) this.context.getExternalContext().getSessionMap().get("logedInUser");

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

            this.phoneType.setInsertedBy(this.user.getIdUser());
            this.phoneType.setInsertedOn(new Timestamp(System.currentTimeMillis()));
            this.phoneType.setUpdatedBy(this.user.getIdUser());
            this.phoneType.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.phoneType.setStatus(true);

            this.facadeLocal.create(this.phoneType);
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

            PhoneType p = (PhoneType) event.getObject();
            p.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.phoneType.setUpdatedBy(this.user.getIdUser());

            this.facadeLocal.edit(p);
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

    public void remove(PhoneType p) {

        try {

            p.setStatus(false);
            p.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.phoneType.setUpdatedBy(this.user.getIdUser());

            this.facadeLocal.edit(p);
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
