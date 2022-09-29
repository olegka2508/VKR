
package ru.rsreu.app.utils;

import ru.rsreu.app.model.QuestionWithEmbodiments;
import ru.rsreu.app.model.Quiz;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public class QuizCombinatorialUtil {
    public List<Quiz> generate(Quiz quiz) {
        return questionCombine(answerCombine(quiz), 0, 0);
    }

    public List<Quiz> generate(Quiz quiz, int variantLimit, int questionLimit) {
        return questionCombine(answerCombine(quiz), variantLimit, questionLimit);
    }

    private static List<Quiz> questionCombine(List<Quiz> inputQuizList, int variantLimit, int questionLimit) {
        final List<Quiz> combinedQuizList = new ArrayList<>();
        for (Quiz quiz : inputQuizList) {
            if (variantLimit != 0 && combinedQuizList.size() == variantLimit) {
                break;
            } else {
                Collections.shuffle(quiz.getQuestions());
                Quiz newQuiz = new Quiz();
                for (QuestionWithEmbodiments question : quiz.getQuestions()) {
                    if (questionLimit != 0 && newQuiz.getQuestions().size() == questionLimit) {
                        break;
                    } else {
                        newQuiz.addNewQuestion(question);
                    }
                }
                combinedQuizList.add(newQuiz);
            }

        }
        return combinedQuizList;
    }

    private List<Quiz> answerCombine(Quiz quiz) {
        final List<Quiz> combinedQuizList = new ArrayList<>();
        boolean flag = true;
        for (QuestionWithEmbodiments question : quiz.getQuestions()) {
            final String questionText = question.getQuestionText();
            final String trueAnswer = question.getAnswers().get(question.getIndexOfTrueAnswer());
            List<List<String>> answersList = generateAnswerList(question.getAnswers());
            for (List<String> answerList : answersList) {
                int numberOfTrueAnswer = 0;
                for (int i = 0; i < answerList.size(); i++) {
                    if (answerList.get(i).equals(trueAnswer)) {
                        numberOfTrueAnswer = i + 1;
                    }
                }
                if (flag) {
                    Quiz newQuiz = new Quiz();
                    newQuiz.addNewQuestion(new QuestionWithEmbodiments(questionText,
                            answerList,
                            numberOfTrueAnswer));
                    combinedQuizList.add(newQuiz);
                }
            }
            if (!flag)
                for (int i = 0; i < answersList.size(); i++) {
                    int numberOfTrueAnswer = 0;
                    for (int j = 0; j < answersList.get(0).size(); j++) {
                        if (answersList.get(i).get(j).equals(trueAnswer)) {
                            numberOfTrueAnswer = j + 1;
                        }
                    }
                    combinedQuizList.get(i)
                            .addNewQuestion(new QuestionWithEmbodiments(questionText,
                                    answersList.get(i),
                                    numberOfTrueAnswer));
                }
            flag = false;
        }
        return combinedQuizList;
    }
    

    private List<List<String>> generateAnswerList(List<String> inputAnswer) {
        List<List<Integer>> indexLists = getIndexCombinations(inputAnswer.size());
        List<List<String>> answersList = new ArrayList<>();
        for (List<Integer> indexes : indexLists) {
            List<String> answerList = new ArrayList<>();
            for (Integer i : indexes) {
                answerList.add(inputAnswer.get(i - 1));
            }
            answersList.add(answerList);
        }
        return answersList;
    }

    private List<List<Integer>> getIndexCombinations(int size) {
        List<List<Integer>> indexLists = new ArrayList<>();
        int[][] indexArrays = new int[calculateFactorial(size)][size];
        int indexToInsert = 0;
        for (int[] array : indexArrays) {
            array = getNextUniceArray(indexArrays, indexToInsert++);
            indexLists.add(Arrays.stream(array).boxed().collect(Collectors.toList()));
        }
        return indexLists;
    }


    private int[] getNextUniceArray(int[][] arrays, int indexToInsert) {
        int[] array = getArray(arrays[0].length);
        if (isArrayContainsInArray(array, arrays)) {
            return getNextUniceArray(arrays, indexToInsert);
        } else {
            arrays[indexToInsert] = array;
            return array;
        }
    }

    private int[] getArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = getNextUniceValue(array, size);
        }
        return array;
    }

    private boolean isArrayContainsInArray(int[] interior, int[][] external) {
        List<Integer> list = Arrays.stream(interior).boxed().collect(Collectors.toList());
        for (int[] v : external) {
            List<Integer> list2 = Arrays.stream(v).boxed().collect(Collectors.toList());
            if (list.toString().equals(list2.toString())) {
                return true;
            }
        }
        return false;
    }

    private int getNextUniceValue(int[] array, int size) {
        Random random = new Random();
        int i = 1 + random.nextInt(size);
        return isValueContainsInArray(array, i) ? getNextUniceValue(array, size) : i;
    }

    private boolean isValueContainsInArray(int[] array, int value) {
        for (int v : array) {
            if (value == v) {
                return true;
            }
        }
        return false;
    }

    private int calculateFactorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++) {
            result = result * i;
        }
        return result;
    }
}

