package com.green.kinsomy.interview.model;

/**
 * Created by kinsomy on 2017/10/19.
 */

public class SingleWord {
    private float start;
    private float end;
    private String wordText;

    public SingleWord(float start, String wordText) {
        super();
        this.start = start;
        this.wordText = wordText;
    }

    public float getStart() {
        return start;
    }

    public float getEnd() {
        return end;
    }

    public String getWordText() {
        return wordText;
    }

    public void setStart(float start) {
        this.start = start;
    }

    public void setEnd(float end) {
        this.end = end;
    }

    public void setWordText(String wordText) {
        this.wordText = wordText;
    }

}
