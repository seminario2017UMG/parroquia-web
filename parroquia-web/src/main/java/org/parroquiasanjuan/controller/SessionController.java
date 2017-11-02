package org.parroquiasanjuan.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.parroquiasanjuan.facade.UserFacadeLocal;
import org.parroquiasanjuan.mdl.Permission;
import org.parroquiasanjuan.mdl.ResourceAccess;
import org.parroquiasanjuan.mdl.User;

/**
 *
 * @author lveliz
 */
@Named(value = "sessionController")
@RequestScoped
public class SessionController implements Serializable {

    private static final long serialVersionUID = -9136905495098090988L;

    @EJB
    private UserFacadeLocal facadeLocal;
    private User user;
    private String password = "";
    private FacesContext context;
    private List<ResourceAccess> menu;

    @PostConstruct
    public void init() {
        this.user = new User();
        this.context = FacesContext.getCurrentInstance();
        this.menu = new ArrayList();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ResourceAccess> getMenu() {
        return menu;
    }

    public void setMenu(List<ResourceAccess> menu) {
        this.menu = menu;
    }

    public String login() {

        String redirect = "";

        try {

            User u = this.facadeLocal.login(this.user, this.password);

            if (u != null) {

                this.context.getExternalContext().getSessionMap().put("logedInUser", u);
                this.menu = getLogedInUser().getIdRole().getResourceAccessList();
                redirect = "protected/main/index.xhtml?faces-redirect=true";
                
            } else {
                this.context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "CREDENCIALES ERRONEAS O NO REGISTRADAS", "HO"));
            }

        } catch (Exception e) {
            this.context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "NO", "HO"));
        }

        return redirect;

    }

    public void checkLogin() {

        User lUser = (User) this.context.getExternalContext().getSessionMap().get("logedInUser");

        if (lUser == null) {
        }

    }

    public User getLogedInUser() {
        return (User) this.context.getExternalContext().getSessionMap().get("logedInUser");
    }

    public boolean hasPermission(Integer resource, Integer action) {

//        try {
//            for (Permission permission : getLogedInUser().getIdRole().getPermissionList()) {
//                if (permission.getStatus() == true) {
//
//                    if (Objects.equals(resource, permission.getIdPermission())
//                            && (Objects.equals(action, permission.getIdActivity().getIdAction()))) {
//                        return true;
//                    }
//
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }

        return true;

    }

    public void logout() {
        context.getExternalContext().invalidateSession();
    }

}
