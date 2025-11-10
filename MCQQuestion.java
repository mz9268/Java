/**
 * GITHUB LINKS:
 * RISHIKA : https://github.com/rishika23011
 * SALONI : https://github.com/Salonirit/python.git
 * REBECCA : https://github.com/rebecca2394
 * ZAYED : https://github.com/mz9268/Classroom
 */

import java.util.Scanner;
import java.util.Arrays;
public class MCQQuestion {
    private String question;
    private String[] answers;
    private String correctanswer;
    /**
     * Constructor which initializes question answers and correctanswer
     * @param question
     * @param answers
     * @param correctanswer
     */
    public MCQQuestion(String question, String[] answers, String correctanswer) {
        this.question = question;
        this.answers = answers;
        this.correctanswer = correctanswer;
    }

    /**
     * tostring method which returns question and answer without showing the correct answer.
     */

    @Override
    public String toString() {
        return "MCQ question is " + question + " and the options are :" + Arrays.toString(answers);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MCQQuestion other) {
            return this.question.equals(other.getQuestion()) &&
               Arrays.equals(this.answers, other.getAnswers()) &&
               this.correctanswer.equals(other.getCorrectanswer());
    }
    return false;
}

    /**
     * getters and setters for question , answers and correctanswer
     * @return
     */
    
    public String getQuestion() {
        return question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    public String[] getAnswers() {
        return answers;
    }
    public void setAnswers(String[] answers) {
        this.answers = answers;
    }
    public String getCorrectanswer() {
        return correctanswer;
    }
    public void setCorrectanswer(String correctanswer) {
        this.correctanswer = correctanswer;
    }

    /**
     * method to verify the users answer with the correct answer ,
     * it will return true if the answer is correct and false if it isnt
     * @param userAnswer
     * @return
     */

    public boolean Checkifansweriscorrect(String userAnswer) {
        return userAnswer.equals(correctanswer);
    }

    /**
     * Main function which creates object questionone
     * it also includes the implementation of scanner which is used for useranswer which helps in Checkifansweriscorrect method
     * @param args
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String[] options1 = {"A.Object-oriented","B.Use of pointers", "C.Portable", "D.Dynamic and Extensible"};
        MCQQuestion questionone = new MCQQuestion("Which one of the following is not a Java feature ? ", options1, "B.Use of pointers");
        System.out.println(questionone);
        System.out.print("Enter your answer:");
        String userAnswer = scan.nextLine();
        System.out.println(questionone.Checkifansweriscorrect(userAnswer));
        scan.close();
    }

}
