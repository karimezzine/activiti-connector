/**
 *
 */
package com.wevioo.mules.activiti;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.commons.net.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.display.FriendlyName;
import org.mule.api.annotations.display.Placement;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.wevioo.mules.activiti.config.ConnectorConfig;
import com.wevioo.mules.activiti.error.ActivitiException;

@Connector(name = "activiti", friendlyName = "Activiti", keywords = "wevioo,activiti,bpm,bpmn", minMuleVersion = "3.6", description = "Mule Connector for Activiti(BPM) ")
public class ActivitiConnector {

	@Config
	ConnectorConfig config;
	OkHttpClient client = new OkHttpClient();
	protected final String PROCESS_DEFINITION = "/activiti-app/api/enterprise/process-definitions";
	protected final String PROCESS_INSTANSIATE = "/activiti-app/api/enterprise/process-instances";
	protected final String PROCESS_TASKS = "/activiti-app/api/enterprise/tasks/query";
	protected final String TASK_FORM = "/activiti-app/api/enterprise/tasks/{taskId}/variables";
	protected final String TASK_ACTION = "/activiti-app/api/enterprise/task-forms/{taskId}";

	/**
	 * Custom processor
	 *
	 * @param friend
	 *            Name to be used to generate a greeting message.
	 * @return A greeting message
	 * @throws ActivitiException
	 * @throws IOException
	 */
	
	@Processor(friendlyName = "Complete Task")
	public String completeTask(String taskId,
			@Placement(group = "Parameters") @FriendlyName("Data") Map<String, String> map)
			throws ActivitiException, IOException {
			JSONObject data = new JSONObject();
			data.put("values", new JSONObject(map));
			System.out.println(data);
			MediaType mediaType = MediaType.parse("application/json");
			Request requestBody = new Request.Builder().url(buildResource(TASK_ACTION).replace("{taskId}", taskId))
					.post(RequestBody.create(mediaType, data.toString())).addHeader("content-type", "application/json")
					.addHeader("cache-control", "no-cache").addHeader("Authorization", getToken())
					.addHeader("Accept", "application/json").build();
			Response response = client.newCall(requestBody).execute();

			switch (response.code()) {
			case 401:
				throw new ActivitiException("401", "NotAuthorizedAccess", "Authentication failure ");
			case 500:
				throw new ActivitiException("500", "InternalServerError", "General server error");
			case 404:
				throw new ActivitiException("404", "NoTaskFound", "No Task Found with the given id : " + taskId);
			}
			JSONObject result = new JSONObject();
			result.put("task_id", taskId);
			result.put("status", "COMPLETED");

			return result.toString();
		
	}

	
	@Processor(friendlyName = "Find Task Form")
	public String getTaskForm(String taskId) throws ActivitiException {
		try {
			Request requestBody = new Request.Builder().url(buildResource(TASK_FORM).replace("{taskId}", taskId)).get()
					.addHeader("content-type", "application/json").addHeader("cache-control", "no-cache")
					.addHeader("Authorization", getToken()).addHeader("Accept", "application/json").build();

			Response response;

			response = client.newCall(requestBody).execute();
			switch (response.code()) {
			case 401:
				throw new ActivitiException("401", "NotAuthorizedAccess", "Authentication failure ");
			case 500:
				throw new ActivitiException("500", "InternalServerError", "General server error");
			case 404:
				throw new ActivitiException("404", "NoTaskFound", "No Task Found with the given id : " + taskId);
			}
			JSONObject obj = new JSONObject();
			obj.put("task_id", taskId);
			obj.put("fields", new JSONArray(response.body().string()));
			return  obj.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ActivitiException("500", "InternalServerError", "General connector error");
		}

	}

	@Processor(friendlyName = "Current Task")
	public String getCurrentTask(String processInstanceId) throws ActivitiException, IOException {
		if (!processInstanceId.equals("")) {
			JSONObject object = new JSONObject();
			object.put("processInstanceId", processInstanceId);
			MediaType mediaType = MediaType.parse("application/json");
			Request requestBody = new Request.Builder().url(buildResource(PROCESS_TASKS))
					.post(RequestBody.create(mediaType, object.toString()))
					.addHeader("content-type", "application/json").addHeader("cache-control", "no-cache")
					.addHeader("Authorization", getToken()).addHeader("Accept", "application/json").build();
			Response response = client.newCall(requestBody).execute();

			switch (response.code()) {
			case 401:
				throw new ActivitiException("401", "NotAuthorizedAccess", "Authentication failure ");
			case 500:
				throw new ActivitiException("500", "InternalServerError", "General server error");
			case 404:
				throw new ActivitiException("404", "ProcessIstanceNotFound",
						"Couldn't find resource " + processInstanceId);
			}
			object = new JSONObject(response.body().string());
			try {
				JSONArray lineItems = object.getJSONArray("data");
				object = new JSONObject();
				object.put("task_id", lineItems.getJSONObject(0).get("id"));
				object.put("task_name", lineItems.getJSONObject(0).get("name"));
				object.put("task_description", lineItems.getJSONObject(0).get("description"));
				return object.toString();
			} catch (Exception e) {
				// TODO: handle exception
				throw new ActivitiException("400", "NoCurrentTaskFound",
						"Couldn't find current Task, either the process is not started or completed");
			}

		} else {
			throw new ActivitiException("404", "ProcessIstanceNotFound", "Couldn't find resource " + processInstanceId);
		}
	}

	/**
	 * 
	 * @return
	 * @throws ActivitiException
	 */
	@Processor(friendlyName = "Find All Process")
	public String getAllProcess() throws ActivitiException {
		try {
			Request requestBody = new Request.Builder().url(buildResource(PROCESS_DEFINITION)).get()
					.addHeader("content-type", "application/json").addHeader("cache-control", "no-cache")
					.addHeader("Authorization", getToken()).addHeader("Accept", "application/json").build();

			Response response;

			response = client.newCall(requestBody).execute();
			switch (response.code()) {
			case 401:
				throw new ActivitiException("401", "NotAuthorizedAccess", "Authentication failure ");
			case 500:
				throw new ActivitiException("500", "InternalServerError", "General server error");
			}
			return response.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ActivitiException("500", "InternalServerError", "General connector error");
		}

	}

	@Processor(friendlyName = "Get Process ID")
	public String verifyProcessDefinition(String processDefinitionName) throws ActivitiException {
		JSONObject process = new JSONObject(getAllProcess());
		JSONArray lineItems = process.getJSONArray("data");
		int found = -1;
		for (int i = 0; i < lineItems.length(); i++) {
			JSONObject obj = lineItems.getJSONObject(i);
			if (obj.get("name").equals(processDefinitionName)) {
				found = i;
			}
		}
		if (found == -1)
			throw new ActivitiException("404", "ProcessDefinitionNotFound",
					"Couldn't find resource " + processDefinitionName);
		return lineItems.getJSONObject(found).getString("id");
	}

	@Processor(friendlyName = "Instanciate Process")
	public String instanciateProcess(String processDefinitionName,
			@Placement(group = "Parameters") @FriendlyName("Data") Map<String, String> map)
			throws ActivitiException, IOException {
		String processId = verifyProcessDefinition(processDefinitionName);
		if (!processId.equals("")) {
			JSONObject json = new JSONObject(map);
			JSONObject data = new JSONObject();
			data.put("values", json);
			data.put("processDefinitionId", processId);
			System.out.println(data);
			MediaType mediaType = MediaType.parse("application/json");
			Request requestBody = new Request.Builder().url(buildResource(PROCESS_INSTANSIATE))
					.post(RequestBody.create(mediaType, data.toString())).addHeader("content-type", "application/json")
					.addHeader("cache-control", "no-cache").addHeader("Authorization", getToken())
					.addHeader("Accept", "application/json").build();
			Response response = client.newCall(requestBody).execute();

			switch (response.code()) {
			case 401:
				throw new ActivitiException("401", "NotAuthorizedAccess", "Authentication failure ");
			case 500:
				throw new ActivitiException("500", "InternalServerError", "General server error");
			}
			JSONObject obj = new JSONObject(response.body().string());
			JSONObject result = new JSONObject();
			result.put("process_id", processId);
			result.put("instance_id", obj.get("id"));
			result.put("status", "STARTED");

			return result.toString();
		} else {
			throw new ActivitiException("500", "InternalServerError", "General server error");

		}
	}

	public ConnectorConfig getConfig() {
		return config;
	}

	public void setConfig(ConnectorConfig config) {
		this.config = config;
	}

	protected String buildResource(String resource) {
		return "http://" + config.getHost() + ":" + config.getPort() + resource;
	}

	protected String getToken() {
		String auth = config.getUsername() + ":" + config.getPassword();
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(encodedAuth);
	}

}