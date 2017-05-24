/**
 *
 */
package com.wevioo.mules.activiti.config;

import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.display.Placement;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.param.Default;

@Configuration(friendlyName = "Configuration")
public class ConnectorConfig {

    /**
     * Greeting message
     */
    @Configurable
	@Placement(group = "Connection Settings", order = 1)
    private String host;
    @Configurable
	@Placement(group = "Connection Settings", order = 2)
    private int port;
    @Configurable
	@Placement(group = "Authentication", order = 1)
    private String username;
    @Configurable
    @Password
	@Placement(group = "Authentication", order = 2)
    private String password;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    
    
}