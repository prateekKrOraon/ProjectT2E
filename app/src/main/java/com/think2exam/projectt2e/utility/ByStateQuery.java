package com.think2exam.projectt2e.utility;

import com.think2exam.projectt2e.Constants;
import com.think2exam.projectt2e.R;

public class ByStateQuerySelector {

    String reqUrl = Constants.BASE_API_URL;
    public ByStateQuerySelector(){}

    public String setreqURL(int catId)
    {

        if(catId== R.string.engineering)
        {
            reqUrl=reqUrl+"getByStateAllEngineering.php";
        }
        else if(catId== R.string.agriculture)
        {
            reqUrl+="getByStateAllAgriculture.php";

        }
        else if(catId== R.string.management)
        {
            reqUrl+="getByStateAllManagement.php";

        }
        else if(catId== R.string.medical_and_dental)
        {
            reqUrl+="getByStateAllMedicalAndDental.php";

        }
        else if(catId== R.string.pharmacy)
        {
            reqUrl+="getByStateAllPharmacy.php";

        }
        else if(catId== R.string.nursing_and_paramedical)
        {
            reqUrl+="getByStateAllNursingAndParamedical.php";

        }
        else if(catId== R.string.education)
        {
            reqUrl+="getByStateAllEducation.php";

        }
        else
        {
            reqUrl+="getByStateAllUniversity.php";

        }

        return reqUrl;
    }


}
