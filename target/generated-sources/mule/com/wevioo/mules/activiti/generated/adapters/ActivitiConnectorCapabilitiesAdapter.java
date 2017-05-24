
package com.wevioo.mules.activiti.generated.adapters;

import com.wevioo.mules.activiti.ActivitiConnector;
import javax.annotation.Generated;
import org.mule.api.devkit.capability.Capabilities;
import org.mule.api.devkit.capability.ModuleCapability;


/**
 * A <code>ActivitiConnectorCapabilitiesAdapter</code> is a wrapper around {@link ActivitiConnector } that implements {@link org.mule.api.Capabilities} interface.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2017-05-24T03:42:50+01:00", comments = "Build UNNAMED.2793.f49b6c7")
public class ActivitiConnectorCapabilitiesAdapter
    extends ActivitiConnector
    implements Capabilities
{


    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(ModuleCapability capability) {
        if (capability == ModuleCapability.LIFECYCLE_CAPABLE) {
            return true;
        }
        return false;
    }

}
