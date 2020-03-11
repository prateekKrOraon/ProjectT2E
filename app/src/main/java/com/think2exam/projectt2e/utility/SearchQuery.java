package com.think2exam.projectt2e.utility;

import android.content.Context;

import com.think2exam.projectt2e.Constants;
import com.think2exam.projectt2e.R;

public class SearchQuery {

    public String req_URL = Constants.BASE_API_URL;

    public SearchQuery(){}

    public String setreqURL(Context context, String category, String State, String City, String keyword) {

        String jsonStr = "";
        HttpHandler httpHandler = new HttpHandler();

        req_URL = "";
        int carId;
        if (category.equals(context.getString(R.string.engineering)))
            carId = R.string.engineering;
        else if (category.equals(context.getString(R.string.agriculture)))
            carId = R.string.agriculture;
        else if (category.equals(context.getString(R.string.education)))
            carId = R.string.education;
        else if (category.equals(context.getString(R.string.university)))
            carId = R.string.university;
        else if (category.equals(context.getString(R.string.management)))
            carId = R.string.management;
        else if (category.equals(context.getString(R.string.medical_and_dental)))
            carId = R.string.medical_and_dental;
        else if (category.equals(context.getString(R.string.nursing_and_paramedical)))
            carId = R.string.nursing_and_paramedical;
        else
            carId = R.string.pharmacy;

        if (State.equals("All State"))
            {
                CompleteTableQuery querySelector = new CompleteTableQuery();
                querySelector.setreqURL(carId);
                jsonStr = querySelector.request(keyword);
            }
         else if (!State.equals("All State") && City.equals("All City"))
            {
                ByStateQuery byStateQuery = new ByStateQuery();
                byStateQuery.setreqURL(carId);
                jsonStr = byStateQuery.request(State, keyword);
            }
        else if (!City.equals("All City"))
            {
                ByCityQuery byCityQuery = new ByCityQuery();
                byCityQuery.setreqURL(carId);
                jsonStr = byCityQuery.request(City, keyword);
            }


        return jsonStr;

    }


}
