package org.parroquiasanjuan.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.parroquiasanjuan.facade.ActionFacadeLocal;
import org.parroquiasanjuan.mdl.Action;

/**
 *
 * @author lveliz
 */
@Named(value = "actionController")
@RequestScoped
public class ActionController implements Serializable {

    private static final long serialVersionUID = -8785371300750466725L;

    @EJB
    private ActionFacadeLocal facadeLocal;
    private Action action;
    private List<Action> actions = new ArrayList();

    @PostConstruct
    public void init() {
        this.action = new Action();
        this.actions = facadeLocal.findAll();
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

}
