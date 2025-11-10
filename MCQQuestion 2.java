import java.util.Arrays;

class MCQQuestion implements Question {
    private String question;
    private String[] answers;
    private String correctAnswer;

    public MCQQuestion(String question, String[] answers, String correctAnswer) {
        this.question = question;
        this.answers = answers;
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
    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "MCQ: " + question + "\nOptions: " + Arrays.toString(answers);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
    
        if (o instanceof MCQQuestion) {
            MCQQuestion other = (MCQQuestion) o;
    
            if (this.question.equals(other.question)) {
                return true;
            }
        }
    
        return false;
    }
    
}
