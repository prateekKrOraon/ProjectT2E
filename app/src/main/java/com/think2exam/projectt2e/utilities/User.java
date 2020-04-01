package com.think2exam.projectt2e.utilities;

public class User {

    private static User user;
    public int id=-1;
    public String fname;
    public String lname;
    public String phoneNo;
    public String email;
    public String image;
    public String totalMatches="0";
    public String wins="0";
    public String totalPoints="0";
    public String correctAns="0";
    public String wrongAns="0";
    public String noAns="0";
    public String avgPoints="0";

    private User() {
    }

    public static User getInstance() {
        if (user == null){
            user = new User();
        }
        return user;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setFname(String name){
        this.fname = name;
    }
    public void setLname(String name){
        this.lname = name;
    }

    public void setPhoneNo(String phoneNo){
        this.phoneNo = phoneNo;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public void setImage(String image){
        this.image = image;
    }
    public void setTotalMatches(int totalMatches) {
        this.totalMatches = String.valueOf(Integer.parseInt(this.totalMatches)+totalMatches);
    }

    public void setWins(int wins) {
        this.wins = String.valueOf(Integer.parseInt(this.wins)+wins);
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = String.valueOf(Integer.parseInt(this.totalPoints)+totalPoints);
    }

    public void setCorrectAns(int correctAns) {
        this.correctAns = String.valueOf(Integer.parseInt(this.correctAns)+correctAns);
    }

    public void setWrongAns(int wrongAns) {
        this.wrongAns = String.valueOf(Integer.parseInt(this.wrongAns)+wrongAns);
    }

    public void setNoAns(int noAns) {
        this.noAns = String.valueOf(Integer.parseInt(this.noAns)+noAns);
    }

    public void setAvgPoints() {
        if (totalMatches.equals("0")){
            this.avgPoints = "0";
        }else{
            double avg = Integer.parseInt(totalPoints)/Integer.parseInt(totalMatches);
            this.avgPoints = String.valueOf(avg);
        }
    }
}
