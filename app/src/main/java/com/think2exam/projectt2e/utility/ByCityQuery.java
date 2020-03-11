package com.think2exam.projectt2e.utility;

import com.think2exam.projectt2e.Constants;
import com.think2exam.projectt2e.R;

public class ByCityQuery {

    String reqUrl = Constants.BASE_API_URL;
    public ByCityQuery(){}

    public void setreqURL(int catId)
    {

        if(catId== R.string.engineering)
        {
            reqUrl=reqUrl+"getByCityAllEngineering.php";
        }
        else if(catId== R.string.agriculture)
        {
            reqUrl+="getByCityAllAgriculture.php";

        }
        else if(catId== R.string.management)
        {
            reqUrl+="getByCityAllManagement.php";

        }
        else if(catId== R.string.medical_and_dental)
        {
            reqUrl+="getByCityAllMedicalAndDental.php";

        }
        else if(catId== R.string.pharmacy)
        {
            reqUrl+="getByCityAllPharmacy.php";

        }
        else if(catId== R.string.nursing_and_paramedical)
        {
            reqUrl+="getByCityAllNursingAndParamedical.php";

        }
        else if(catId== R.string.education)
        {
            reqUrl+="getByCityAllEducation.php";

        }
        else
        {
            reqUrl+="getByCityAllUniversity.php";

        }

    }

    public String request(String city,String keyword)
    {
        HttpHandler httpHandler = new HttpHandler();
        return httpHandler.getByCityColleges(reqUrl,city,keyword);
    }


}
