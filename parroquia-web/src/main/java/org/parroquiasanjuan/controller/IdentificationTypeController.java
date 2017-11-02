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
import org.parroquiasanjuan.mdl.User;
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
    private User user;

    @PostConstruct
    public void init() {

        this.identificationType = new IdentificationType();
        this.identificationTypes = this.facadeLocal.findAll();
        this.context = FacesContext.getCurrentInstance();
        this.user = (User) context.getExternalContext().getSessionMap().get("logedInUser");

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

            this.identificationType.setStatus(true);
            this.identificationType.setUpdatedBy(this.user.getIdUser());
            this.identificationType.setInsertedBy(this.user.getIdUser());
            this.identificationType.setInsertedOn(new Timestamp(System.currentTimeMillis()));
            this.identificationType.setUpdatedOn(new Timestamp(System.currentTimeMillis()));

            this.facadeLocal.create(this.identificationType);
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

            IdentificationType i = (IdentificationType) event.getObject();
            i.setUpdatedOn(new Timestamp(System.currentTimeMillis()));

            this.facadeLocal.edit(i);
            this.context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Algo salio mal",
                    "Lo sentimos ha ocurrido un error."
            ));

        } catch (Exception e) {
            this.context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Algo salio mal",
                    "Lo sentimos ha ocurrido un error."
            ));
        }
    }

    public void remove(IdentificationType i) {

        try {

            i.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            i.setStatus(false);

            this.facadeLocal.edit(i);
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
