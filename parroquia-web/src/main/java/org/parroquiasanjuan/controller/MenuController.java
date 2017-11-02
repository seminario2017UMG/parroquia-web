package org.parroquiasanjuan.controller;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.parroquiasanjuan.mdl.ResourceAccess;
import org.parroquiasanjuan.mdl.User;

/**
 *
 * @author lveliz
 */
@Named(value = "menuController")
@SessionScoped
public class MenuController implements Serializable {

    private static final long serialVersionUID = -2608246203484128897L;

    private User user;
    private FacesContext context;

    @PostConstruct
    public void init() {

        this.context = FacesContext.getCurrentInstance();
        this.user = (User) this.context.getExternalContext().getSessionMap().get("logedInUser");

    }

    public List<ResourceAccess> getMenuItems() {
        return this.user.getIdRole().getResourceAccessList();
    }

}
