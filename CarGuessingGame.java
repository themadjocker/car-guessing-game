import java.util.Scanner;

public class CarGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Car Guessing Game!");
        System.out.println("Think of a car, and I will try to guess it.");
        System.out.println("Please answer the following questions with 'yes' or 'no'.\n");

        // Game tree nodes
        Node rootNode = createTree();

        playGame(scanner, rootNode);
    }

    private static void playGame(Scanner scanner, Node currentNode) {
        if (currentNode.isQuestionNode()) {
            System.out.print(currentNode.getData() + " ");
            String answer = scanner.nextLine().toLowerCase();
            if (answer.equals("yes")) {
                playGame(scanner, currentNode.getYesNode());
            } else if (answer.equals("no")) {
                playGame(scanner, currentNode.getNoNode());
            } else {
                System.out.println("Invalid input. Please try again.");
                playGame(scanner, currentNode);
            }
        } else {
            System.out.print("Is it a " + currentNode.getData() + "? ");
            String answer = scanner.nextLine().toLowerCase();
            if (answer.equals("yes")) {
                System.out.println("I guessed it! Thanks for playing.");
            } else if (answer.equals("no")) {
                handleWrongGuess(scanner, currentNode);
            } else {
                System.out.println("Invalid input. Please try again.");
                playGame(scanner, currentNode);
            }
        }
    }

    private static void handleWrongGuess(Scanner scanner, Node currentNode) {
        System.out.print("Oops! What car were you thinking of? ");
        String userCar = scanner.nextLine();

        System.out.print("Please provide a question that can distinguish a "
                + userCar + " from a " + currentNode.getData() + ": ");
        String newQuestion = scanner.nextLine();

        System.out.print("What is the answer to the question for a " + userCar + "? ");
        String newAnswer = scanner.nextLine().toLowerCase();

        Node newQuestionNode = new Node(newQuestion);
        Node newAnswerNode = new Node(newAnswer.equals("yes") ? userCar : currentNode.getData());
        Node oldAnswerNode = new Node(newAnswer.equals("yes") ? currentNode.getData() : userCar);

        if (currentNode.getParent() != null) {
            if (currentNode.getParent().getYesNode() == currentNode) {
                currentNode.getParent().setYesNode(newQuestionNode);
            } else {
                currentNode.getParent().setNoNode(newQuestionNode);
            }
            newQuestionNode.setParent(currentNode.getParent());
        } else {
            newQuestionNode.setParent(null);
        }

        newQuestionNode.setYesNode(newAnswerNode);
        newQuestionNode.setNoNode(oldAnswerNode);
        newAnswerNode.setParent(newQuestionNode);
        oldAnswerNode.setParent(newQuestionNode);

        System.out.println("Thanks for teaching me. Let's play again!");
        playGame(scanner, newQuestionNode);
    }

    private static Node createTree() {
        Node rootNode = new Node("Does it have four wheels?");
        Node yesNode1 = new Node("Does it have two doors?");
        Node noNode1 = new Node("Does it have a trunk?");
        Node yesNode2 = new Node("Is it a sports car?");
        Node noNode2 = new Node("Is it a sedan?");
        Node yesNode3 = new Node("Is it a convertible?");
        Node noNode3 = new Node("Is it an SUV?");
        Node yesNode4 = new Node("Is it an electric car?");
        Node noNode4 = new Node("Is it a pickup truck?");

        rootNode.setYesNode(yesNode1);
        rootNode.setNoNode(noNode1);
        yesNode1.setParent(rootNode);
        noNode1.setParent(rootNode);

        yesNode1.setYesNode(yesNode2);
        yesNode1.setNoNode(noNode2);
        yesNode2.setParent(yesNode1);
        noNode2.setParent(yesNode1);

        noNode1.setYesNode(yesNode3);
        noNode1.setNoNode(noNode3);
        yesNode3.setParent(noNode1);
        noNode3.setParent(noNode1);

        noNode2.setYesNode(yesNode4);
        noNode2.setNoNode(noNode4);
        yesNode4.setParent(noNode2);
        noNode4.setParent(noNode2);

        return rootNode;
    }
}

class Node {
    private String data;
    private Node parent;
    private Node yesNode;
    private Node noNode;

    public Node(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data){
        this.data = data;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getYesNode() {
        return yesNode;
    }

    public void setYesNode(Node yesNode) {
        this.yesNode = yesNode;
    }

    public Node getNoNode() {
        return noNode;
    }

    public void setNoNode(Node noNode) {
        this.noNode = noNode;
    }

    public boolean isQuestionNode() {
        return yesNode != null && noNode != null;
    }
}
