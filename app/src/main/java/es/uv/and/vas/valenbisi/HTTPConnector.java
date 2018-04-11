package es.uv.and.vas.valenbisi;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import uk.me.jstott.jcoord.UTMRef;

public class HTTPConnector extends AsyncTask<String, Void, ArrayList> {

    @Override
    protected ArrayList<Station> doInBackground(String... params) {
        ArrayList<Station> stations = new ArrayList<>();
        String jsonString;
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        // get json
        String url = "http://mapas.valencia.es/lanzadera/opendata/Valenbisi/JSON";
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            //add request header
            con.setRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64)AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
            con.setRequestProperty("accept", "application/json;");
            con.setRequestProperty("accept-language", "es");
            con.connect();
            int responseCode = con.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(con.getInputStream(), "UTF-8"));
            int n;
            while ((n = in.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // parse json to stations
        jsonString = writer.toString();

        // json array of features from jsonString
        try {
            jsonObject = new JSONObject(jsonString);
            jsonArray = new JSONArray(jsonObject.get("features").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // clone data from jsonArray to stations array
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    // coordinates conversion
                    double[] coordinates = {
                            obj.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0),
                            obj.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1)
                    };

                    UTMRef utm = new UTMRef(coordinates[0], coordinates[1], 'N', 30);
                    coordinates[0] = utm.toLatLng().getLat();
                    coordinates[1] = utm.toLatLng().getLng();

                    stations.add(new Station(
                            new Properties(
                                    obj.getJSONObject("properties").getString("name"),
                                    obj.getJSONObject("properties").getInt("number"),
                                    obj.getJSONObject("properties").getString("address"),
                                    obj.getJSONObject("properties").getString("open"),
                                    obj.getJSONObject("properties").getInt("available"),
                                    obj.getJSONObject("properties").getInt("free"),
                                    obj.getJSONObject("properties").getInt("total"),
                                    obj.getJSONObject("properties").getString("ticket"),
                                    obj.getJSONObject("properties").getString("updated_at")
                            ),
                            new Geometry(
                                    obj.getJSONObject("geometry").getString("type"),
                                    coordinates
                            )
                    ));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return stations;
    }

    @Override
    protected void onPostExecute(ArrayList stations) {
        // Crear la ListView
    }
}
