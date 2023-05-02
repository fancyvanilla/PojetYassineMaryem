import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class DynamicPanelGridInfo extends SimpleBeanInfo {
    
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor labels = new PropertyDescriptor("labels", DynamicPanelGrid.class);
            return new PropertyDescriptor[] {labels};
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
