package org.parroquiasanjuan.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import org.parroquiasanjuan.facade.RoleFacadeLocal;
import org.parroquiasanjuan.mdl.Role;
import org.parroquiasanjuan.mdl.User;

/**
 *
 * @author lveliz
 */
@Named(value = "roleController")
@RequestScoped
public class RoleController implements Serializable {

    private static final long serialVersionUID = -5535117898372940414L;

    @EJB
    private RoleFacadeLocal facadeLocal;
    private Role role;
    private List<Role> roles;
    private FacesContext context;
    private User user;

    @PostConstruct
    public void init() {

        this.role = new Role();
        this.roles = new ArrayList();
        this.roles = this.facadeLocal.findAll();
        this.context = FacesContext.getCurrentInstance();
        this.user = (User) this.context.getExternalContext().getSessionMap().get("logedInUser");

    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
