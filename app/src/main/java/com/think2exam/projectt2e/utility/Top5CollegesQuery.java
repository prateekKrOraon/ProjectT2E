package com.think2exam.projectt2e.utility;

import com.think2exam.projectt2e.Constants;
import com.think2exam.projectt2e.R;

public class Top5CollegesQuerySelector {

    String reqUrl = Constants.BASE_API_URL;
    public Top5CollegesQuerySelector(){}

    public String setreqURL(int catId)
    {

        if(catId== R.string.engineering)
        {
            reqUrl=reqUrl+"getTop5Engineering.php";
        }
        else if(catId== R.string.agriculture)
        {
            reqUrl+="getTop5Agriculture.php";

        }
        else if(catId== R.string.management)
        {
            reqUrl+="getTop5Management.php";

        }
        else if(catId== R.string.medical_and_dental)
        {
            reqUrl+="getTop5MedicalAndDental.php";

        }
        else if(catId== R.string.pharmacy)
        {
            reqUrl+="getTop5Pharmacy.php";

        }
        else if(catId== R.string.nursing_and_paramedical)
        {
            reqUrl+="getTop5NursingAndParamedical.php";

        }
        else if(catId== R.string.education)
        {
            reqUrl+="getTop5Education.php";

        }
        else
        {
            reqUrl+="getTop5University.php";

        }

        return reqUrl;
    }


}
