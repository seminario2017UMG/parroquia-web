package org.parroquiasanjuan.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.parroquiasanjuan.facade.AddressFacadeLocal;
import org.parroquiasanjuan.mdl.Address;

/**
 *
 * @author lveliz
 */
@Named(value = "addressController")
@RequestScoped
public class AddressController implements Serializable {

    private static final long serialVersionUID = 4947055399510502899L;

    @EJB
    private AddressFacadeLocal facadeLocal;
    private Address addres;
    private List<Address> addresses = new ArrayList();

    @PostConstruct
    public void init() {
        this.addres = new Address();
        this.addresses = this.facadeLocal.findAll();
    }

    public Address getAddres() {
        return addres;
    }

    public void setAddres(Address addres) {
        this.addres = addres;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

}
