import java.util.ArrayList;
import java.util.Scanner;

class Quiz {
    private QuizQuestions quizQuestions;
    private int score;

    public Quiz(QuizQuestions quizQuestions) {
        this.quizQuestions = quizQuestions;
    }

    public void startQuiz() {
        try (Scanner scanner = new Scanner(System.in)) {
            score = 0;

            ArrayList<Question> questions = quizQuestions.getSelectedQuestions();
            for (int i = 0; i < questions.size(); i++) {
                Question q = questions.get(i);
                System.out.println((i + 1) + ". " + q.getQuestion());
                String[] options = q.getAllAnswers();
                for (int j = 0; j < options.length; j++) {
                    System.out.println((char) ('A' + j) + ". " + options[j]);
                }

                System.out.print("Your answer: ");
                String userAnswer = scanner.nextLine().trim();

                if (userAnswer.equalsIgnoreCase(q.getCorrectAnswer())) {
                    score++;
                }
            }

            System.out.println("Quiz finished! Your score: " + score + "/" + questions.size());
        }
    }

    public int getScore() {
        return score;
    }
}