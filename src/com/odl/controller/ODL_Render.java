package com.odl.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.odl.service.ODLService;
import com.odl.util.PropUtil;
import com.odl.util.Topology;

@Controller
public class ODL_Render {

	ODLService service = new ODLService();

	@RequestMapping(value = "/odl", method = RequestMethod.GET)
	public String render(Model model, @RequestParam("url") String url) {
		if (url.equals("") || url == "") {
			return "error";
		} else {
			String api = PropUtil.getProperty("prefix") + url.trim() + ":"
					+ PropUtil.getProperty("port")
					+ PropUtil.getProperty("topology_get_by_restconf");
			System.out.println(api);
			service.setTopology(new Topology(api));
			Topology topo = service.getTopology();
			if (api.length() <= url.length()) {
				return "error";
			} else {
				String response = topo.odl_http_get("GET", api,
						PropUtil.getProperty("contentType"), null,
						PropUtil.getProperty("accept"), "200");
				System.out.println(response);

				try {
					List<String> list = new ArrayList<String>();
					JSONObject jsonObject = new JSONObject(response);
					String topology_id = "";
					// JSONArray jsonArray =
					// jsonObject.getJSONArray("network-topology");
					JSONObject topoObject = (JSONObject) jsonObject
							.get("network-topology");
					JSONArray topologyArray = topoObject
							.getJSONArray("topology");

					JSONObject obj = (JSONObject) topologyArray.get(0);

					topology_id = obj.getString("topology-id");
					JSONArray array = obj.getJSONArray("node");
					for(int i=0;i<array.length();i++) {
						JSONObject o = array.getJSONObject(i);
						list.add(o.getString("node-id"));
					}
					model.addAttribute("topology_id", topology_id);
					model.addAttribute("nodeList",list);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}

		return "odl";
	}

}
