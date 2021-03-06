package com.example.kyle.scout_862_template.EventCards;


import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyle on 9/7/17.
 */

public class ListClass {
    String eventName;
    String eventDate;
    String eventCode;
    String status;

    public ListClass(JsonObject simpleEventObj, JsonObject teamEventObj ) {

        if (simpleEventObj == null)
            return;
        else {
            eventName = simpleEventObj.get("name").toString();
            eventDate = simpleEventObj.get("start_date").toString();
            eventCode = simpleEventObj.get("event_type_string").toString();
        }

        if (teamEventObj == null)
            return;
        else {
            String statusString = "";
            statusString = teamEventObj.get("overall_status_str").toString();
            statusString = statusString.replace("<b>", "");
            statusString = statusString.replace("</b>", "");
            status = statusString;
        }
    }
}
