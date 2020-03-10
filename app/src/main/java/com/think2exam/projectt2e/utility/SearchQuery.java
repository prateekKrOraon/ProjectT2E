package com.think2exam.projectt2e.utility;

import android.content.Context;

import com.think2exam.projectt2e.Constants;
import com.think2exam.projectt2e.R;

public class SearchQuerySelector {

    public String req_URL = Constants.BASE_API_URL;

    public SearchQuerySelector(){}

    public String setreqURL(Context context, String category, String State, String City, String keyword)
    {

        if(category.equals("All Category")) {
            if (State.equals("All State")) {
                req_URL += "getAllByKeyword.php";
            } else if (!State.equals("All State") && City.equals("All City")) {
                req_URL += "getAllByState.php";
            } else if (!City.equals("All City")) {
                req_URL += "getAllByCity.php";
            }
        }
        else
        {
            req_URL = "";
            int carId;
            if(category.equals(context.getString(R.string.engineering)))
                carId = R.string.engineering;
            else if(category.equals(context.getString(R.string.agriculture)))
                carId = R.string.agriculture;
            else if(category.equals(context.getString(R.string.education)))
                carId = R.string.education;
            else if(category.equals(context.getString(R.string.university)))
                carId = R.string.university;
            else if(category.equals(context.getString(R.string.management)))
                carId = R.string.management;
            else if(category.equals(context.getString(R.string.medical_and_dental)))
                carId = R.string.medical_and_dental;
            else if(category.equals(context.getString(R.string.nursing_and_paramedical)))
                carId = R.string.nursing_and_paramedical;
            else
                carId = R.string.pharmacy;

            if (State.equals("All State")) {
                CompleteQuerySelector querySelector = new CompleteQuerySelector();
                req_URL = querySelector.setreqURL(carId);
            } else if (!State.equals("All State") && City.equals("All City")) {
                ByStateQuerySelector byStateQuerySelector = new ByStateQuerySelector();
                req_URL = byStateQuerySelector.setreqURL(carId);
            } else if (!City.equals("All City")) {
                ByCityQuerySelector byCityQuerySelector = new ByCityQuerySelector();
                req_URL = byCityQuerySelector.setreqURL(carId);
            }
        }

        return req_URL;

    }


}
