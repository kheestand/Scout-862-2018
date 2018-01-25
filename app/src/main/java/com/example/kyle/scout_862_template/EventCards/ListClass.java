package com.example.kyle.scout_862_template.EventCards;

import org.json.simple.JSONObject;

/**
 * Created by kyle on 9/7/17.
 */

public class ListClass {
    String eventName;
    String eventDate;
    String eventCode;
    String status;

    public ListClass(JSONObject simpleEventObj, JSONObject teamEventObj, JSONObject teamAwardObj) {

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
