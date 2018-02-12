package com.example.kyle.scout_862_template.BlueAllianceData;

import com.example.kyle.scout_862_template.BlueAllianceData.BinarySearchTree.BinarySearchTree;
import com.example.kyle.scout_862_template.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataFetcher {

    public final String FILE_EXTENSION = ".tbadata";
    public final String ERROR_CODE = "error_0025";
    private final String BASE_URL = "https://www.thebluealliance.com/api/v3";
    private final String AUTH_KEY = "6Yc2aQ9kyHaY2KBqN85g9PEQHZdPaZRTr1VMk3lKAp6jhunovmO0mDI8AdoFAke7";
    BinarySearchTree eventKeyCache = new BinarySearchTree();
    BinarySearchTree robotFileCache = new BinarySearchTree();
    private OkHttpClient client = new OkHttpClient();

    private static JsonObject searchTeam(int team, JsonArray data) {
        for (int index = 0; index < data.size(); index++) {
            JsonObject objectAtIndex = (JsonObject) data.get(index);
            if (objectAtIndex.get("key").equals("frc" + team))
                return objectAtIndex;
        }
        return null;
    }

    private String run(String extension) throws Exception {
        Request request = new Request.Builder()
                .url(BASE_URL + extension)
                .header("X-TBA-Auth-Key", AUTH_KEY)
                .build();
        Response response = client.newCall(request).execute();
        Headers responseHeaders = response.headers();
        return response.body().string();
    }

    public void makeCache() throws IOException {
        JsonParser parser = new JsonParser();
        Reader reader = new FileReader(Constants.blueAlliancePath + "event_list_" + Constants.year + FILE_EXTENSION);
        JsonArray eventKeys = (JsonArray) parser.parse(reader);
        eventKeyCache.root = eventKeyCache.JsonArrayToBST(eventKeys, 0, eventKeys.size() - 1);

        FileFilter directoryFileFilter = new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory();
            }
        };
        File teamFolderDir = new File(Constants.blueAlliancePath);
        File[] allTeamFolders = teamFolderDir.listFiles(directoryFileFilter);
        robotFileCache.root = robotFileCache.FileArrayToBST(allTeamFolders, 0, allTeamFolders.length - 1);
    }

    public String fetchTeamDataByPage(int pageNumber) {
        // Note: pages are zero-indexed
        try {
            return run("/teams/" + pageNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR_CODE;
    }

    public String fetchCompsCompetedKey(int teamNumber, int year) {
        String teamKey = "frc" + teamNumber;
        try {
            return run("/team/" + teamKey + "/events/" + year + "/keys");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR_CODE;
    }

    public String fetchCompsCompetedKey(String teamKey, int year) {
        try {
            return run("/team/" + teamKey + "/events/" + year + "/keys");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR_CODE;
    }

    public String fetchCompetitionInfo(String eventKey) {
        try {
            return run("/event/" + eventKey + "/simple");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR_CODE;
    }

    public String fetchCompetitionKeys(String year) {
        return fetchCompetitionKeys(Integer.parseInt(year));
    }

    public String fetchCompetitionKeys(int year) {
        try {
            return run("/events/" + year + "/keys");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR_CODE;
    }

    public String fetchTeamPerformanceForComp(String teamKey, String eventKey) {
        return fetchTeamPerformanceForComp(Integer.parseInt(teamKey), eventKey);
    }

    public String fetchTeamPerformanceForComp(int teamKey, String eventKey) {
        try {
            return run("/team/" + teamKey + "/event/" + eventKey + "/status");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ERROR_CODE;
    }

    public JsonObject getSimpleEventObject(String eventKey) throws IOException {
        JsonParser parser = new JsonParser();
        Reader reader = new FileReader(Constants.blueAlliancePath + "event_list_" + Constants.year + FILE_EXTENSION);
        JsonArray JsonArray = (JsonArray) parser.parse(reader);
        for (int index = 0; index < JsonArray.size(); index++) {
            JsonObject currentEvent = (JsonObject) JsonArray.get(index);
            String currentEventKey = currentEvent.get("event_code").toString();
            if (eventKey.contains(currentEventKey))
                return currentEvent;
        }
        return null;
    }

    public JsonObject getSimpleEventFromCache(String eventKey) {
        return (JsonObject) eventKeyCache.search(eventKey);
    }

    public JsonObject getTeamObject(String team) throws IOException {
        return getTeamObject(Integer.parseInt(team));
    }

    public JsonObject getTeamObject(int team) throws IOException {
        int pageNumber = team / 500;
        JsonParser parser = new JsonParser();
        Reader reader = new FileReader(Constants.blueAlliancePath + "team_list_page_" + pageNumber + FILE_EXTENSION);
        Object jsonObj = parser.parse(reader);
        JsonArray teamData = (JsonArray) jsonObj;
        return searchTeam(team, teamData);
    }

    public JsonObject getRobotEventObject(String team, String eventKey) throws IOException {
        return getRobotEventObject(Integer.parseInt(team), eventKey);
    }

    public JsonObject getRobotEventObject(int team, String eventKey) throws IOException {
        JsonParser parser = new JsonParser();
        Reader reader = new FileReader(Constants.blueAlliancePath + "/" + team + "/" + "status_" + eventKey + FILE_EXTENSION);
        Object jsonObj = parser.parse(reader);
        return (JsonObject) jsonObj;
    }

    public JsonArray getRobotEventKeys(String team) throws IOException {
        return getRobotEventKeys(Integer.parseInt(team));
    }

    public JsonArray getRobotEventKeys(int team) throws IOException {
        JsonParser parser = new JsonParser();
        Reader reader = new FileReader(Constants.blueAlliancePath + "/" + team + "/" + "event_keys" + FILE_EXTENSION);
        Object jsonObj = parser.parse(reader);
        return (JsonArray) jsonObj;
    }

    public JsonObject getRobotEventObjectFromCache(String team, String currentEvent) throws IOException {
        return getRobotEventObjectFromCache(Integer.parseInt(team), currentEvent);
    }

    public JsonObject getRobotEventObjectFromCache(int team, String currentEvent) throws IOException {
        JsonParser parser = new JsonParser();
        File[] files = (File[]) robotFileCache.search(team);
        Reader reader = new FileReader(findFile(files, currentEvent));
        Object jsonObj = parser.parse(reader);
        return (JsonObject) jsonObj;
    }

    private File findFile(File[] fileArray, String key) {
        for (File aFileArray : fileArray) {
            if (aFileArray.getName().contains(key))
                return aFileArray;
        }
        return null;
    }
}
