import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuizScoringTest {

    private TestableQuiz quiz;

    @BeforeEach
    public void setup() {
        quiz = new TestableQuiz();
        quiz.questions.add("Who invented Java Programming?");
        quiz.options.add(new String[]{"Guido van Rossum", "James Gosling", "Dennis Ritchie"});
        quiz.correctAnswers.add(1); // "James Gosling" is correct

        quiz.questions.add("What is the default value of an int variable in Java?");
        quiz.options.add(new String[]{"0", "1", "null"});
        quiz.correctAnswers.add(0); // "0" is correct

        quiz.questions.add("Which keyword is used to create a subclass in Java?");
        quiz.options.add(new String[]{"super", "extends", "implements"});
        quiz.correctAnswers.add(1); // "extends" is correct

        quiz.questions.add("Which keyword is used to define a constant variable in Java?");
        quiz.options.add(new String[]{"final", "static", "const"});
        quiz.correctAnswers.add(0); // "final" is correct



    }

    @Test
    public void testAllCorrectAnswers() {
        quiz.answer(1); // correct
        quiz.answer(1); // correct
        quiz.answer(0); // correct
        assertEquals(3, quiz.score);
    }

    private void assertEquals(int i, int score) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }

    @Test
    public void testSomeWrongAnswers() {
        quiz.answer(0); // wrong
        quiz.answer(1); // correct
        quiz.answer(2); // wrong
        assertEquals(1, quiz.score);
    }

    @Test
    public void testEmptyAnswerSimulation() {
        // simulate not answering at all by skipping answer()
        quiz.currentQuestionIndex += 3; // skip all questions
        assertEquals(0, quiz.score);
    }

    @Test
    public void testMultipleAnswersSameQuestion() {
        quiz.answer(1); // correct first time
        quiz.answer(0); // second answer to same question (should be ignored in real app)
        quiz.answer(1);
        quiz.answer(0);
        assertEquals(2, quiz.score); // based on how many times checkAnswer is called, not UI
    }

    /**
     * Minimal subclass to allow isolated logic testing
     */
    static class TestableQuiz {
        int currentQuestionIndex = 0;
        int score = 0;
        List<String> questions = new ArrayList<>();
        List<String[]> options = new ArrayList<>();
        List<Integer> correctAnswers = new ArrayList<>();

        public void answer(int selectedIndex) {
            if (currentQuestionIndex >= questions.size()) return;
            if (selectedIndex == correctAnswers.get(currentQuestionIndex)) {
                score++;
            }
            currentQuestionIndex++;
        }
    }
}