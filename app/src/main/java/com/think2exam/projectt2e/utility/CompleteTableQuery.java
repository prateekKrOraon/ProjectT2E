package com.think2exam.projectt2e.utility;

import com.think2exam.projectt2e.Constants;
import com.think2exam.projectt2e.R;

import java.util.logging.Handler;

public class CompleteTableQuery {

    String reqUrl = Constants.BASE_API_URL;
    public CompleteTableQuery(){}

    public void setreqURL(int catId)
    {

        if(catId== R.string.engineering)
        {
            reqUrl=reqUrl+"getAllEngineering.php";
        }
        else if(catId== R.string.agriculture)
        {
            reqUrl+="getAllAgriculture.php";

        }
        else if(catId== R.string.management)
        {
            reqUrl+="getAllManagement.php";

        }
        else if(catId== R.string.medical_and_dental)
        {
            reqUrl+="getAllMedicalAndDental.php";

        }
        else if(catId== R.string.pharmacy)
        {
            reqUrl+="getAllPharmacy.php";

        }
        else if(catId== R.string.nursing_and_paramedical)
        {
            reqUrl+="getAllNursingAndParamedical.php";

        }
        else if(catId== R.string.education)
        {
            reqUrl+="getAllEducation.php";

        }
        else
        {
            reqUrl+="getAllUniversity.php";

        }

    }

    public String request(String keyword)
    {
        HttpHandler httpHandler = new HttpHandler();
        String jsonStr = httpHandler.getByCategoryColleges(reqUrl,keyword);
        return jsonStr;
    }


}
