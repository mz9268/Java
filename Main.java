import java.io.FileNotFoundException;

/**
 * Main class to execute the Quiz Application.
 * It loads questions from a file, selects a subset, and starts the quiz.
 */
public class Main {

      /**
     * Main method to run the program.
     *
     * @param args Command-line arguments (not used).
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException {
        QuizQuestions quizQuestions = new QuizQuestions();
        quizQuestions.load("questionsBase.txt");  // Load questions
        quizQuestions.select(10);                 // Select first 5 questions

        Quiz quiz = new Quiz(quizQuestions);    
        quiz.startQuiz();                        // Start quiz
    }
}

