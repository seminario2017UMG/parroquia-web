package org.parroquiasanjuan.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.parroquiasanjuan.facade.ResourceFacadeLocal;
import org.parroquiasanjuan.mdl.Resource;

/**
 *
 * @author lveliz
 */
@Named(value = "resourceController")
@RequestScoped
public class ResourceController implements Serializable {

    private static final long serialVersionUID = 7569172920934179386L;

    @EJB
    private ResourceFacadeLocal facadeLocal;
    private Resource resource;
    private List<Resource> resources = new ArrayList();

    @PostConstruct
    public void init() {
        this.resource = new Resource();
        this.resources = facadeLocal.findAll();
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

}
