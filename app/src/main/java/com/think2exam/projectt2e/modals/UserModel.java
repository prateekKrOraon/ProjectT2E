package com.think2exam.projectt2e.modals;

public class UserModel {

    int id;
    String fname,lname,mobile,email,image;
    int totalMatches,totalPoints,wins,correctAns,wrongAns,noAns;

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public int getTotalMatches() {
        return totalMatches;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getWins() {
        return wins;
    }

    public int getCorrectAns() {
        return correctAns;
    }

    public int getWrongAns() {
        return wrongAns;
    }
    public int getNoAns() {
        return noAns;
    }


    public UserModel(int id, String fname, String lname, String mobile, String email, String image, int totalMatches, int totalPoints, int wins, int correctAns, int wrongAns,int noAns) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.mobile = mobile;
        this.email = email;
        this.image = image;
        this.totalMatches = totalMatches;
        this.totalPoints = totalPoints;
        this.wins = wins;
        this.correctAns = correctAns;
        this.wrongAns = wrongAns;
        this.noAns = noAns;
    }


}


