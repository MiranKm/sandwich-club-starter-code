package com.udacity.sandwichclub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    ImageView ingredientsIv;
    TextView originTv, descriptionTv, alsoKnownTv, ingredientsTv, imageErrorTv;
    ProgressBar imageProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imageProgressBar = findViewById(R.id.image_pb);
        imageErrorTv = findViewById(R.id.image_error_tv);

        ingredientsIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
        alsoKnownTv = findViewById(R.id.also_known_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        } else
            populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        setTitle(sandwich.getMainName());
        
        originTv.setText(sandwich.getMainName());
        descriptionTv.setText(sandwich.getDescription());

        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {

            for (int i = 0; i < sandwich.getIngredients().size(); i++) {

                ingredientsTv.append(sandwich.getIngredients().get(i));

            }
        }

        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {

            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                alsoKnownTv.append(sandwich.getAlsoKnownAs().get(i));
            }

        } else {
            alsoKnownTv.setText("other names not available");
        }


        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            originTv.setText(sandwich.getPlaceOfOrigin());

        } else
            originTv.setText("origin not available");


        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                ingredientsIv.setImageBitmap(bitmap);
                imageProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                imageErrorTv.setVisibility(View.VISIBLE);
                ingredientsIv.setImageDrawable(errorDrawable);
                imageProgressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                ingredientsIv.setImageDrawable(placeHolderDrawable);
                imageProgressBar.setVisibility(View.VISIBLE);

            }
        };


        Picasso.with(this).load(sandwich.getImage()).into(target);
    }
}
