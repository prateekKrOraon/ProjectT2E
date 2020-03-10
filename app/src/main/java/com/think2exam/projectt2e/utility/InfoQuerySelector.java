package com.think2exam.projectt2e.utility;

import com.think2exam.projectt2e.Constants;
import com.think2exam.projectt2e.R;

public class InfoTableSelector {

    String reqUrl = Constants.BASE_API_URL;
    public InfoTableSelector(){}

    public String setreqURL(int catId)
    {

        if(catId== R.string.engineering)
        {
            reqUrl=reqUrl+"getInfoEngineering.php";
        }
        else if(catId== R.string.agriculture)
        {
            reqUrl+="getInfoAgriculture.php";

        }
        else if(catId== R.string.management)
        {
            reqUrl+="getInfoManagement.php";

        }
        else if(catId== R.string.medical_and_dental)
        {
            reqUrl+="getInfoMedicalAndDental.php";

        }
        else if(catId== R.string.pharmacy)
        {
            reqUrl+="getInfoPharmacy.php";

        }
        else if(catId== R.string.nursing_and_paramedical)
        {
            reqUrl+="getInfoNursingAndParamedical.php";

        }
        else if(catId== R.string.education)
        {
            reqUrl+="getInfoEducation.php";

        }
        else
        {
            reqUrl+="getInfoUniversity.php";

        }

        return reqUrl;
    }


}
