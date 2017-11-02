package org.parroquiasanjuan.converter;

import controller.util.JsfUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.parroquiasanjuan.facade.IdentificationTypeFacade;
import org.parroquiasanjuan.mdl.IdentificationType;

/**
 *
 * @author lveliz
 */
@FacesConverter(value = "identificationTypeConverter")
public class IdentificationTypeConverter implements Converter {

    private IdentificationTypeFacade ejbFacade = new IdentificationTypeFacade();

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        try {

            if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
                return null;
            }

            System.out.println("******************" + getKey(value));

        return this.ejbFacade.find("3");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    java.lang.Integer getKey(String value) {
        java.lang.Integer key;
        key = Integer.valueOf(value);
        return key;
    }

    String getStringKey(java.lang.Integer value) {
        StringBuffer sb = new StringBuffer();
        sb.append(value);
        return sb.toString();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof IdentificationType) {
            IdentificationType o = (IdentificationType) object;
            return getStringKey(o.getIdIdentificationType());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), IdentificationType.class.getName()});
            return null;
        }
    }

    private IdentificationTypeFacade getEjbFacade() {
        this.ejbFacade = CDI.current().select(IdentificationTypeFacade.class).get();
        return this.ejbFacade;
    }

}
