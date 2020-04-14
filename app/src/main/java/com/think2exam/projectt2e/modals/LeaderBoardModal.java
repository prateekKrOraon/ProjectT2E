package com.think2exam.projectt2e.modals;

public class LeaderBoardModal {

    public int image;
    public String name;
    public String points;
    public String rank;

    public LeaderBoardModal(String rank, int image, String name, String points){
        this.image = image;
        this.name = name;
        this.points = points;
        this.rank = rank;
    }

}
