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
import org.parroquiasanjuan.facade.TownFacadeLocal;
import org.parroquiasanjuan.mdl.Town;
import org.parroquiasanjuan.mdl.TownPK;
import org.parroquiasanjuan.mdl.User;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author lveliz
 */
@Named(value = "townController")
@RequestScoped
public class TownController implements Serializable {

    private static final long serialVersionUID = 8536638064360876953L;

    @EJB
    private TownFacadeLocal facadeLocal;
    private Town town;
    private List<Town> towns;
    private FacesContext context;
    private User user;

    @PostConstruct
    public void init() {

        this.town = new Town();
        this.towns = new ArrayList();
        this.towns = this.facadeLocal.findAll();
        this.context = FacesContext.getCurrentInstance();
        this.user = (User) this.context.getExternalContext().getSessionMap().get("logedInUser");

    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public List<Town> getTowns() {
        return towns;
    }

    public void setTowns(List<Town> towns) {
        this.towns = towns;
    }

    public void create() {

        try {

            this.town.setInsertedBy(this.user.getIdUser());
            this.town.setInsertedOn(new Timestamp(System.currentTimeMillis()));
            this.town.setUpdatedBy(this.user.getIdUser());
            this.town.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.town.setStatus(true);
            System.out.println(this.town.getDescription());
            System.out.println(this.town.getState());
            
            this.facadeLocal.create(this.town);
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

            Town selectedTown = (Town) event.getObject();
            selectedTown.setUpdatedBy(this.user.getIdUser());
            selectedTown.setUpdatedOn(new Timestamp(System.currentTimeMillis()));

            System.out.println("=======================================");
            selectedTown.setTownPK(new TownPK(selectedTown.getTownPK().getIdTown(), this.town.getState().getIdState()));
            System.out.println("idState  " + selectedTown.getTownPK().getIdState());
            System.out.println("getDescription  " + selectedTown.getDescription());
            System.out.println("getInsertedBy  " + selectedTown.getInsertedBy());
            System.out.println("getInsertedOn  " + selectedTown.getInsertedOn());
            System.out.println("getStatus  " + selectedTown.getStatus());
            System.out.println("getUpdatedBy  " + selectedTown.getUpdatedBy());
            System.out.println("getUpdatedOn  " + selectedTown.getUpdatedOn());
            System.out.println("=======================================");

            this.facadeLocal.edit(selectedTown);
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

    public void remove(Town t) {

        try {

            t.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            t.setUpdatedBy(this.user.getIdUser());
            t.setStatus(false);

            this.facadeLocal.edit(t);
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
