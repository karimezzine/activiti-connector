
package com.wevioo.mules.activiti.generated.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/activiti</code>.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2017-05-24T03:42:50+01:00", comments = "Build UNNAMED.2793.f49b6c7")
public class ActivitiNamespaceHandler
    extends NamespaceHandlerSupport
{

    private static Logger logger = LoggerFactory.getLogger(ActivitiNamespaceHandler.class);

    private void handleException(String beanName, String beanScope, NoClassDefFoundError noClassDefFoundError) {
        String muleVersion = "";
        try {
            muleVersion = MuleManifest.getProductVersion();
        } catch (Exception _x) {
            logger.error("Problem while reading mule version");
        }
        logger.error(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [activiti] is not supported in mule ")+ muleVersion));
        throw new FatalBeanException(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [activiti] is not supported in mule ")+ muleVersion), noClassDefFoundError);
    }

    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        try {
            this.registerBeanDefinitionParser("config", new ActivitiConnectorConnectorConfigConfigDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("config", "@Config", ex);
        }
        try {
            this.registerBeanDefinitionParser("complete-task", new CompleteTaskDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("complete-task", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-task-form", new GetTaskFormDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-task-form", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-current-task", new GetCurrentTaskDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-current-task", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("get-all-process", new GetAllProcessDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("get-all-process", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("verify-process-definition", new VerifyProcessDefinitionDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("verify-process-definition", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("instanciate-process", new InstanciateProcessDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("instanciate-process", "@Processor", ex);
        }
    }

}
