package JavaBean;

import java.beans.PropertyEditorSupport;

public class DefaultMessageEditor extends PropertyEditorSupport {

    private String tempString;

    public String getAsText(){
        String value = ((String) this.getValue()).toString();
        return value;
    }

    public void setAsText(String s){
        tempString=s;
        this.setValue(new String(s));
        this.firePropertyChange();
    }

    public String getJavaInitializationString() {
        StringBuffer s = new StringBuffer();
        s.append("new String(");
        s.append(tempString);
        s.append(")");
        return s.toString();
    }

}
