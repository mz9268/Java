import java.util.Arrays;

/**
 * Class representing a True/False type question.
 */
class TFQQuestion implements Question {
    private String question;
    private String[] answers;
    private String correctAnswer;

    /**
     * Constructor to create a True/False question.
     * 
     * @param question The question text.
     * @param correctAnswer The correct answer ("True" or "False").
     */
    public TFQQuestion(String question, String correctAnswer) {
        this.question = question;
        this.answers = new String[]{"True", "False"}; // Default options
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public String[] getAllAnswers() {
        return answers;
    }

    @Override
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    // Getters and Setters

    /**
     * Sets the question text.
     * 
     * @param question The new question text.
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Sets the correct answer.
     * 
     * @param correctAnswer The new correct answer ("True" or "False").
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "TFQ: " + question + "\nOptions: " + Arrays.toString(answers);
    }

    @Override
    public boolean equals(Object o) {
        // Check if o is null
        if (o == null) {
            return false;
        }
        // Try casting (assuming teacher allows)
        TFQQuestion other = (TFQQuestion) o;

        // Check if question text is equal
        if (this.question.equals(other.question)) {
            return true;
        }
        // If not equal
        return false;
    }

}
