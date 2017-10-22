package org.parroquiasanjuan.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.parroquiasanjuan.facade.PermissionFacadeLocal;
import org.parroquiasanjuan.mdl.Permission;

/**
 *
 * @author lveliz
 */
@Named(value = "permissionController")
@RequestScoped
public class PermissionController implements Serializable {

    private static final long serialVersionUID = 7734481930737312832L;

    @EJB
    private PermissionFacadeLocal facadeLocal;
    private Permission permission;
    private List<Permission> permissions = new ArrayList();

    @PostConstruct
    public void init() {
        this.permission = new Permission();
        this.permissions = facadeLocal.findAll();
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

}
