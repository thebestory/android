package com.thebestory.android;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Октай on 18.10.2016.
 */

@Deprecated

public class Story {

    private String imageTopic;// TODO: 20.10.2016
    private String nameTopic;
    private int numberStory;
    private String textStory;
    private int numbersLikes;
    private String timeTopic;

    public Story(String nameTopic, int numberStory, String textStory, int numbersLikes, String timeTopic) {
        this.nameTopic = nameTopic;
        this.numberStory = numberStory;
        this.textStory = textStory;
        this.numbersLikes = numbersLikes;
        this.timeTopic = timeTopic;
        //this.imageTopic = imageTopic;
    }

    private List<Story> stories;

    public String getNameTopic() {
        return nameTopic;
    }

    public int getNumberStory() {
        return numberStory;
    }

    public String getTextStory() {
        return textStory;
    }

    public int getNumbersLikes() {
        return numbersLikes;
    }

    public String getTimeTopic() {
        return timeTopic;
    }

    /*public String getImageTopic() {
        return imageTopic;
    }*/
}
