package org.parroquiasanjuan.controller;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.parroquiasanjuan.facade.EmployeeFacadeLocal;
import org.parroquiasanjuan.facade.PersonFacadeLocal;
import org.parroquiasanjuan.facade.UserFacadeLocal;
import org.parroquiasanjuan.mdl.Employee;
import org.parroquiasanjuan.mdl.Person;
import org.parroquiasanjuan.mdl.User;
import org.parroquiasanjuan.util.logging.LoggerUtil;
import org.parroquiasanjuan.util.security.Security;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author lveliz
 */
@Named(value = "userController")
@RequestScoped
public class UserController implements Serializable {

    private static final long serialVersionUID = 1426677106523708084L;

    @EJB
    private UserFacadeLocal userFL;
    private User user;
    private List<User> users;
    private String password;

    @EJB
    private PersonFacadeLocal personFL;
    private Person person;
    private List<Person> people;

    @EJB
    private EmployeeFacadeLocal employeeFL;
    private Employee employee;
    private List<Employee> employees;

    private SecretKeySpec key;
    private byte[] salt;
    private User logedInUser;
    private FacesContext context;

    @PostConstruct
    public void init() {

        this.password = Security.generateTempPassword();

        this.user = new User();
        this.users = new ArrayList();
        this.users = this.userFL.findAll();

        this.person = new Person();
        this.people = new ArrayList();

        this.employee = new Employee();
        this.employees = new ArrayList();
        this.employees = this.employeeFL.findAll();

        this.context = FacesContext.getCurrentInstance();
        this.logedInUser = (User) this.context.getExternalContext().getSessionMap().get("logedInUser");

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void create() {

        try {

            this.salt = Security.generatePasswordSalt();
            this.key = Security.createSecretKey(this.password.toCharArray(), this.salt, Security.ITERATION_COUNT, Security.KEY_LENGTH);
            String enc = Security.encrypt(this.password, this.key);

            this.user.setPasswordSalt(Security.base64Encode(this.salt));
            this.user.setPasswordHash(enc);
            this.user.setUsername(this.person.getEmailAddress());
            this.user.setInsertedBy(this.logedInUser.getIdUser());
            this.user.setInsertedOn(new Timestamp(System.currentTimeMillis()));
            this.user.setUpdatedBy(this.logedInUser.getIdUser());
            this.user.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.user.setStatus(true);

            this.person.setInsertedBy(this.logedInUser.getIdUser());
            this.person.setInsertedOn(new Timestamp(System.currentTimeMillis()));
            this.person.setUpdatedBy(this.logedInUser.getIdUser());
            this.person.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.person.setStatus(true);

            this.employee.setInsertedBy(this.logedInUser.getIdUser());
            this.employee.setInsertedOn(new Timestamp(System.currentTimeMillis()));
            this.employee.setUpdatedBy(this.logedInUser.getIdUser());
            this.employee.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            this.employee.setStatus(true);
            this.employee.setIdPerson(this.person);
            this.employee.setIdUser(this.user);
            
            LoggerUtil.log(Level.INFO, this.user.toString());
            this.userFL.create(this.user);
            this.personFL.create(this.person);
            this.employeeFL.create(this.employee);

            this.context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Operación Exitosa",
                    "Se ha creado el nuevo registro en la base de datos."
            ));

        } catch (UnsupportedEncodingException | GeneralSecurityException e) {

            LoggerUtil.log(Level.WARNING, "No se pudo crear el usuario");

            this.context.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Algo salio mal",
                    "Lo sentimos ha ocurrido un error."
            ));

        }

    }

    public void edit(RowEditEvent event) {

        try {

            User u = (User) event.getObject();
            u.setInsertedBy(this.logedInUser.getIdUser());
            u.setUpdatedOn(new Timestamp(System.currentTimeMillis()));

            this.userFL.edit(u);
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

    public void remove(User u) {

        try {

            u.setUpdatedBy(this.logedInUser.getIdUser());
            u.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            u.setStatus(false);

            this.userFL.edit(u);
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
