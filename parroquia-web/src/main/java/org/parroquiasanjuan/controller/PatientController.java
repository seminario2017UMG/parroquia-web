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
import org.parroquiasanjuan.facade.PatientFacadeLocal;
import org.parroquiasanjuan.facade.PersonFacadeLocal;
import org.parroquiasanjuan.mdl.Patient;
import org.parroquiasanjuan.mdl.Person;
import org.parroquiasanjuan.mdl.User;
import org.primefaces.event.RowEditEvent;

@Named(value = "patientController")
@RequestScoped
public class PatientController implements Serializable {

    private static final long serialVersionUID = -8768388885205572156L;

    @EJB
    private PatientFacadeLocal patientFL;
    private List<Patient> filteredPatients;
    private Patient patient;
    private List<Patient> patients;

    @EJB
    private PersonFacadeLocal personFL;
    private Person person;
    private List<Person> people = new ArrayList();

    private FacesContext context;
    private User user;

    @PostConstruct
    public void init() {

        this.patient = new Patient();
        this.patients = this.patientFL.findAll();
        this.person = new Person();
        this.people = this.personFL.findAll();
        this.context = FacesContext.getCurrentInstance();
        this.user = (User) context.getExternalContext().getSessionMap().get("logedInUser");

    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public List<Patient> getFilteredPatients() {
        return filteredPatients;
    }

    public void setFilteredPatients(List<Patient> filteredPatients) {
        this.filteredPatients = filteredPatients;
    }

    public void create() {

        try {

            this.person.setInsertedBy(this.user.getIdUser());
            this.person.setInsertedOn(new Timestamp(System.currentTimeMillis()));
            this.person.setUpdatedBy(this.user.getIdUser());
            this.person.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.person.setStatus(true);
            this.personFL.create(this.person);

            this.patient.setInsertedBy(this.user.getIdUser());
            this.patient.setInsertedOn(new Timestamp(System.currentTimeMillis()));
            this.patient.setUpdatedBy(this.user.getIdUser());
            this.patient.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.patient.setStatus(true);
            this.patient.setIdPerson(this.person);
            this.patientFL.create(this.patient);

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

            Patient editedPatient = (Patient) event.getObject();
            editedPatient.getIdPerson().setIdMaritalStatus(this.person.getIdMaritalStatus());
            editedPatient.getIdPerson().setGender(this.person.getGender());
            editedPatient.getIdPerson().setIdIdentificationType(this.person.getIdIdentificationType());
            editedPatient.setUpdatedBy(this.user.getIdUser());
            editedPatient.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            editedPatient.setStatus(true);

            this.personFL.edit(editedPatient.getIdPerson());
            this.patientFL.edit(editedPatient);

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

    public void remove(Patient p) {

        try {

            p.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            p.setUpdatedBy(this.user.getIdUser());
            p.setStatus(false);

            this.patientFL.edit(p);
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
