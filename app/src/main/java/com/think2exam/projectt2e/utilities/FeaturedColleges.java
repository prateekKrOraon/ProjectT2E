package com.think2exam.projectt2e.utilities;

import com.think2exam.projectt2e.modals.FeaturedCollegeModel;

import java.util.ArrayList;

public class FeaturedColleges {


    public static FeaturedColleges featuredColleges;
    public ArrayList<FeaturedCollegeModel> featuredCollegeModels = new ArrayList<>();
    private FeaturedColleges() {
    }

    public static FeaturedColleges getInstance() {
        if (featuredColleges == null){
            featuredColleges = new FeaturedColleges();
        }
        return featuredColleges;
    }


    public void setFeaturedColleges(FeaturedCollegeModel featuredCollegeModels){
        this.featuredCollegeModels.add(featuredCollegeModels);
    }

    public ArrayList<FeaturedCollegeModel> getFeaturedColleges(){
        return featuredCollegeModels;
    }


}
