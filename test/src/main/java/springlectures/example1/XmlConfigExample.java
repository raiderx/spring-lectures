package springlectures.example1;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

public class XmlConfigExample {

    public static void main(String[] args) {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);

        reader.loadBeanDefinitions("classpath:transfer-service.xml");

        TransferService service = factory.getBean(TransferService.class);
        service.transfer("Kaluga", "Moscow");
    }
}
