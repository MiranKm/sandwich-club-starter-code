package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    final static String TAG = JsonUtils.class.getSimpleName();

    private final static String NAME = "name";
    private final static String MAIN_NAME = "mainName";
    private final static String ALSO_KNOWN_AS = "alsoKnownAs";
    private final static String PLACE_OF_ORIGIN = "placeOfOrigin";
    private final static String DESCRIPTION = "description";
    private final static String INGREDIENTS = "ingredients";
    private final static String IMAGE = "image";


    public static Sandwich parseSandwichJson(String json) {

        try {

            JSONObject root = new JSONObject(json);

            JSONObject name = root.getJSONObject(NAME);

            String mainName = name.getString(MAIN_NAME);

            JSONArray alsoKnownAsJSONArray = name.getJSONArray(ALSO_KNOWN_AS);

            List<String> alsoKnowAsList = MakelistOfJsonObjects(alsoKnownAsJSONArray);


            String placeOfOrigin = root.optString(PLACE_OF_ORIGIN);

            String description = root.getString(DESCRIPTION);

            String image = root.getString(IMAGE);

            JSONArray ingredientsJSONArray = root.getJSONArray(INGREDIENTS);

            List<String> ingredientsList = MakelistIngredients(ingredientsJSONArray);


            Log.e(TAG, "seeing if it show anything, name" + name + " description" + description);

            return new Sandwich(mainName, alsoKnowAsList, placeOfOrigin, description, image, ingredientsList);

        } catch (JSONException e) {
            Log.e(TAG, "something error in parsing the json");
            e.printStackTrace();
        }

        return null;
    }

    private static List<String> MakelistOfJsonObjects(JSONArray jsonArray) throws JSONException {

        List<String> listOfString = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            listOfString.add("\n");
            listOfString.add(jsonArray.getString(i));
        }

        return listOfString;
    }

    private static List<String> MakelistIngredients(JSONArray jsonArray) throws JSONException {

        List<String> listOfString = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            listOfString.add("\n");
            listOfString.add(jsonArray.getString(i));
        }

        return listOfString;
    }
}
