package com.udacity.sandwichclub.utils;

import android.nfc.Tag;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

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
            JSONObject mainJsonObject = new JSONObject(json);

            JSONObject name = mainJsonObject.getJSONObject(NAME);

            String mainName = name.getString(MAIN_NAME);

            JSONArray alsoKnownAsJSONArray = name.getJSONArray(ALSO_KNOWN_AS); // also known j array

            List<String> alsoKnowAsList = MakelistOfJsonObjects(alsoKnownAsJSONArray); //also know list done


            String placeOfOrigin = mainJsonObject.optString(PLACE_OF_ORIGIN); // place Of origin done

            String description = mainJsonObject.getString(DESCRIPTION); // des done

            String image = mainJsonObject.getString(IMAGE); //Image done

            JSONArray ingredientsJSONArray = mainJsonObject.getJSONArray(INGREDIENTS);

            List<String> ingredientsList = MakelistOfJsonObjects(ingredientsJSONArray);


            Log.e(TAG, "name" + name + " description" + description);

            return new Sandwich(mainName, alsoKnowAsList, placeOfOrigin, description, image, ingredientsList);


        } catch (JSONException e) {
            Log.e(TAG, "something happened");
            e.printStackTrace();
        }

        return null;
    }

    private static List<String> MakelistOfJsonObjects(JSONArray jsonArray) throws JSONException {

        List<String> listOfString = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            listOfString.add(jsonArray.getString(i));
        }

        return listOfString;
    }
}
