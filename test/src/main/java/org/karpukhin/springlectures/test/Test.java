package org.karpukhin.springlectures.test;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author Pavel Karpukhin
 * @since 09.11.14.
 */
public class Test {

    public static void main(String[] args) {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);

        reader.loadBeanDefinitions("classpath:beans.xml");

        TransferService service = factory.getBean(TransferService.class);
        service.transfer("A", "B");
    }
}
