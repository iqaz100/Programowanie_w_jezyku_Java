package JavaBean;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.awt.Component;

public class ClockBeanBeanInfo extends SimpleBeanInfo {

    @Override
    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor bd = new BeanDescriptor(ClockBean.class, ClockBeanCustomizer.class);
        //bd.setDisplayName("Editor");
        return bd;
    }

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor defaultMessageDescriptor = new PropertyDescriptor("defaultMessage", ClockBean.class);
            defaultMessageDescriptor.setPropertyEditorClass(DefaultMessageEditor.class);
            //PropertyDescriptor colorDescriptor = new PropertyDescriptor("myBackground", ClockBean.class);
            //colorDescriptor.setPropertyEditorClass(ColorPanelEditor.class);
            PropertyDescriptor messageLengthDescriptor = new PropertyDescriptor("maxLengthMessage",ClockBean.class);
            messageLengthDescriptor.setPropertyEditorClass(MessageLengthEditor.class);
            PropertyDescriptor colorPanelDescriptor = new PropertyDescriptor("myBackground",ClockBean.class);
            colorPanelDescriptor.setPropertyEditorClass(ColorPanelEditor.class);

            return new PropertyDescriptor[] {
                    //new PropertyDescriptor("title", JavaBean.ClockBean.class, "getTitle", "setTitle"),
                    //new PropertyDescriptor("myBackground", JavaBean.ClockBean.class, "getMyBackground", "setMyBackground"),
                    new PropertyDescriptor("myFonts", JavaBean.ClockBean.class, "getMyFonts", "setMyFonts"),
                    //new PropertyDescriptor("defaultMessage", JavaBean.ClockBean.class), defaultMessageDescriptor,
                    //new PropertyDescriptor("myBackground", ClockBean.class), colorDescriptor
                    new PropertyDescriptor("title",ClockBean.class),
                    messageLengthDescriptor,colorPanelDescriptor,defaultMessageDescriptor
                    //wpisac tutaj te edytory
            };
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return null;
        }
    }


}
