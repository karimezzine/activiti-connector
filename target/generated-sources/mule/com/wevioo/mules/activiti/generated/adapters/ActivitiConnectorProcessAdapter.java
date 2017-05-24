
package com.wevioo.mules.activiti.generated.adapters;

import com.wevioo.mules.activiti.ActivitiConnector;
import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>ActivitiConnectorProcessAdapter</code> is a wrapper around {@link ActivitiConnector } that enables custom processing strategies.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2017-05-24T03:42:50+01:00", comments = "Build UNNAMED.2793.f49b6c7")
public class ActivitiConnectorProcessAdapter
    extends ActivitiConnectorLifecycleInjectionAdapter
    implements ProcessAdapter<ActivitiConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, ActivitiConnectorCapabilitiesAdapter> getProcessTemplate() {
        final ActivitiConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,ActivitiConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, ActivitiConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, ActivitiConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
