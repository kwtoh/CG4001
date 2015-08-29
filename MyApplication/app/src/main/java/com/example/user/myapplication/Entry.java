package com.example.user.myapplication;

/**
 * Created by user on 3/30/2015.
 */
public class Entry {

    private int _id;
    private String _entryName;
    private int _entryMoodScore;
    private String _entryOtherFeeling;
    private String _entryThoughts;
    private String _entryEvent;
    private String _entryAction;
    private String _entryDate;


    public Entry(){}

    public Entry(String entryName, int entryMoodScore, String entryOtherFeeling,
                 String entryThoughts, String entryEvent, String entryAction, String entryDate) {
        this._entryName = entryName;
        this._entryMoodScore = entryMoodScore;
        this._entryOtherFeeling = entryOtherFeeling;
        this._entryThoughts = entryThoughts;
        this._entryEvent = entryEvent;
        this._entryAction = entryAction;
        this._entryDate = entryDate;
    }


    // ID Getters and Setters
    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    // entry name  Getters and Setters
    public String get_entryName() {
        return _entryName;
    }

    public void set_entryName(String _entryName) {
        this._entryName = _entryName;
    }

    // mood score Getters and Setters
    public int get_entryMoodScore() {
        return _entryMoodScore;
    }

    public void set_entryMoodScore(int _entryMoodScore) {
        this._entryMoodScore = _entryMoodScore;
    }

    // other feelings Getters and Setters
    public String get_entryOtherFeeling() {
        return _entryOtherFeeling;
    }

    public void set_entryOtherFeeling(String _entryOtherFeeling) {
        this._entryOtherFeeling = _entryOtherFeeling;
    }

    // Thoughts Getters and Setters
    public String get_entryThoughts() {
        return _entryThoughts;
    }

    public void set_entryThoughts(String _entryThoughts) {
        this._entryThoughts = _entryThoughts;
    }

    // Event Getters and Setters
    public String get_entryEvent() {
        return _entryEvent;
    }

    public void set_entryEvent(String _entryEvent) {
        this._entryEvent = _entryEvent;
    }

    // Action Getters and Setters
    public String get_entryAction() {
        return _entryAction;
    }

    public void set_entryAction(String _entryAction) {
        this._entryAction = _entryAction;
    }

    // Action Getters and Setters
    public String get_entryDate() {
        return _entryDate;
    }

    public void set_entryDate(String _entryDate) {
        this._entryDate = _entryDate;
    }
}
