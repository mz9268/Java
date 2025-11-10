import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class QuizQuestions {
    ArrayList<Question> allQuestions = new ArrayList<Question>();
    ArrayList<Question> selectedQuestions = new ArrayList<Question>();

    // Method to load questions from file
    public void load(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
    
        // Read lines in groups of 3 (Question, Answers, Correct Answer)
        while (scanner.hasNextLine()) {
            String questionText = scanner.nextLine();
            String answersLine = "";
            String correctAnswer = "";
    
            if (scanner.hasNextLine()) {
                answersLine = scanner.nextLine();
            }
    
            if (scanner.hasNextLine()) {
                correctAnswer = scanner.nextLine();
            }
    
            // Split answersLine by commas to create an array of answers
            String[] answers = answersLine.split(",");  // Create answers array from the answersLine
    
            // If it's a True/False question
            if (answers.length == 2 && 
                (answers[0].equals("True") && answers[1].equals("False") || 
                answers[0].equals("False") && answers[1].equals("True"))) {
                TFQQuestion tfq = new TFQQuestion(questionText, correctAnswer);
                allQuestions.add(tfq);
            } else {
                MCQQuestion mcq = new MCQQuestion(questionText, answers, correctAnswer);
                allQuestions.add(mcq);
            }
        }
        scanner.close();
    }

    public ArrayList<Question> getSelectedQuestions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSelectedQuestions'");
    }
}
    