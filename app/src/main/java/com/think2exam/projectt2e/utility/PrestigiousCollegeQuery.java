package com.think2exam.projectt2e.utility;

import com.think2exam.projectt2e.Constants;
import com.think2exam.projectt2e.R;

public class PrestigiousCollegeQuerySelector {

    public PrestigiousCollegeQuerySelector(){}

    String reqUrl = Constants.BASE_API_URL;

    public String setreqURL(int id)
    {
        if(id == R.string.iit)
        {
           reqUrl+="getTop10IIT.php";
        }
        else if(id == R.string.nit)
        {
            reqUrl+="getTop10NIT.php";
        }
        else if(id == R.string.aiims)
        {
            reqUrl+="getTop10AIIMS.php";
        }
        else if(id == R.string.iim)
        {
            reqUrl+="getTop10IIM.php";
        }
        else if(id == R.string.university)
        {
            reqUrl+="getTop10University.php";
        }



        return reqUrl;
    }


}
