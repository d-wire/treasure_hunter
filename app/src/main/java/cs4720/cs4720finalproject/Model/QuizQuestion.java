package cs4720.cs4720finalproject.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by liamj_000 on 11/7/2016.
 */
public class QuizQuestion {

    //Defines what the variables are called in the parsed JSON object
    @SerializedName("category")
    private String category;
    @SerializedName("type")
    private String type;
    @SerializedName("difficulty")
    private String difficulty;
    @SerializedName("question")
    private String question;
    @SerializedName("correct_answer")
    private String correctAnswer;
    @SerializedName("incorrect_answers")
    private ArrayList<String> incorrectAnswers;

    public QuizQuestion(String category, String type, String difficulty, String question, String correctAnswer, ArrayList<String> incorrectAnswers) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public ArrayList<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(ArrayList<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public String toString() {
        String questionStr = "Category: " + category + ", Type: " + type + ", Difficulty: " + difficulty + ", Question: " + question + ", Correct Answer: " + correctAnswer + ", Incorrect Answers: " + incorrectAnswers;
        return questionStr;
    }

    /*public static final Parcelable.Creator<QuizQuestion> CREATOR = new Creator<QuizQuestion>() {
        @Override
        public QuizQuestion createFromParcel(Parcel source) {
            QuizQuestion question = new QuizQuestion();
            question.category = source.readString();
            question.type = source.readString();
            question.difficulty = source.readString();
            question.question = source.readString();
            question.correctAnswer = source.readString();
            question.incorrectAnswers = source.readArrayList(QuizQuestion.class.getClassLoader());
            return question;
        }

        @Override
        public QuizQuestion[] newArray(int size) {
            return new QuizQuestion[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(category);
        parcel.writeString(type);
        parcel.writeString(difficulty);
        parcel.writeString(question);
        parcel.writeString(correctAnswer);
        parcel.writeList(incorrectAnswers);
    }*/
}
