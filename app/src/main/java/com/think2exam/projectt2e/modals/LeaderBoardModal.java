package com.think2exam.projectt2e.modals;

public class LeaderBoardModal {

    public int image;
    public String name;
    public String level;
    public String rank;
    public LeaderBoardModal(String rank, int image, String name, String level){
        this.image = image;
        this.name = name;
        this.level = level;
        this.rank = rank;
    }

}
