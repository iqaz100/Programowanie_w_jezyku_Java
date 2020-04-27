package JavaBean;

import java.beans.PropertyEditorSupport;

public class MessageLengthEditor extends PropertyEditorSupport {
    private String tempString;

    public String getAsText(){
        String value = ((Integer) this.getValue()).toString();
        return value;
    }

    public void setAsText(String s){
        tempString=s;
        this.setValue(new Integer(Integer.parseInt(s)));
        this.firePropertyChange();
    }

    public String getJavaInitializationString() {
        StringBuffer s = new StringBuffer();
        s.append("new Integer(");
        s.append(tempString);
        s.append(")");
        return s.toString();
    }

}
