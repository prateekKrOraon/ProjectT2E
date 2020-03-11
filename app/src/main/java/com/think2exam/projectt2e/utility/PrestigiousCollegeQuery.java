package com.think2exam.projectt2e.utility;

import com.think2exam.projectt2e.Constants;
import com.think2exam.projectt2e.R;

public class PrestigiousCollegeQuery {

    public PrestigiousCollegeQuery(){}

    String reqUrl = Constants.BASE_API_URL;

    public int catdId;

    public void setreqURL(int id)
    {
        if(id == R.string.iit)
        {
           reqUrl+="getTop10IIT.php";
           catdId = R.string.engineering;
        }
        else if(id == R.string.nit)
        {
            reqUrl+="getTop10NIT.php";
            catdId = R.string.engineering;

        }
        else if(id == R.string.aiims)
        {
            reqUrl+="getTop10AIIMS.php";
            catdId = R.string.medical_and_dental;

        }
        else if(id == R.string.iim)
        {
            reqUrl+="getTop10IIM.php";
            catdId = R.string.management;

        }
        else if(id == R.string.university)
        {
            reqUrl+="getTop10University.php";
            catdId = R.string.university;

        }

    }
    public String request()
    {
        HttpHandler httpHandler = new HttpHandler();
        return httpHandler.getTop10Colleges(reqUrl);
    }

    public int getCatId()
    {
        return catdId;
    }


}
