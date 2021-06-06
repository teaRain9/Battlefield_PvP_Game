package battleship;

import java.util.Scanner;


public class Main {

    private final static Scanner scanner = new Scanner(System.in);

    private final static BattleField battleField1 = new BattleField();
    private final static BattleField battleField2 = new BattleField();

    private static GameState state = GameState.DEPLOY_SHIP_1;

    public static void main(String[] args) {

        while (true) {
            if (state == GameState.DEPLOY_SHIP_1) {
                System.out.println("Player 1, place your ships on the game field");
                battleField1.print();
                deploy(battleField1);
                changeTurn();
                state = GameState.DEPLOY_SHIP_2;
            } else if (state == GameState.DEPLOY_SHIP_2) {
                System.out.println("Player 2, place your ships on the game field");
                battleField2.print();
                deploy(battleField2);
                changeTurn();
                state = GameState.ATTACK_1;
            } else if (state == GameState.ATTACK_1) {
                battleField1.setHiddenMode(false);
                battleField2.setHiddenMode(true);
                battleField2.print();
                System.out.println("---------------------");
                battleField1.print();
                System.out.println("Player 1, it's your turn:");
                attack(battleField2);
                if (battleField2.gameOver()) {
                    System.out.println("Player 1, won the battle!");
                    break;
                }
                state = GameState.ATTACK_2;
                changeTurn();
                continue;
            } else if (state == GameState.ATTACK_2) {
                battleField2.setHiddenMode(false);
                battleField1.setHiddenMode(true);
                battleField1.print();
                System.out.println("---------------------");
                battleField2.print();
                System.out.println("Player 2, it's your turn:");
                attack(battleField1);
                if (battleField1.gameOver()) {
                    System.out.println("Player 2, won the battle!");
                    break;
                }
                state = GameState.ATTACK_1;
                changeTurn();
                continue;

            }
        }

    }

    public static void deploy (BattleField battleField) {
            boolean shouldMsgPrt = true;
            while (true) {
                try {
                    if (shouldMsgPrt) {
                        System.out.println("Enter the coordinates of the " + battleField.shipName() + "(" + battleField.shipLength() + " cells):");
                    }
                    String[] input = scanner.nextLine().split(" ");
                    Coordinate input1 = new Coordinate(input[0].toUpperCase().charAt(0), Integer.parseInt(input[0].substring(1)));
                    Coordinate input2 = new Coordinate(input[1].toUpperCase().charAt(0), Integer.parseInt(input[1].substring(1)));
                    Coordinate startPoint;
                    Coordinate endPoint;
                    if ((input1.getRow() == input2.getRow() && input1.getCol() < input2.getCol()) || (input1.getCol() == input2.getCol() && input1.getRow() < input2.getRow())) {
                        startPoint = input1;
                        endPoint = input2;
                    } else {
                        startPoint = input2;
                        endPoint = input1;
                    }

                    battleField.newCoordinate(startPoint, endPoint);
                    battleField.print();
                    shouldMsgPrt = true;
                    if (battleField.endShips()) {
                        break;
                    }
                } catch (BattleFieldException e) {
                    System.out.println(e.getMessage());
                    shouldMsgPrt = false;
                } catch (Exception e) {
                    System.out.println("Error! coordinates should be a letter followed by a digit, Try Again:");
                    shouldMsgPrt = false;
                }
            }
    }

    public static void attack(BattleField battleField) {
        while (true) {
            try {
                String inputShot = scanner.nextLine().toUpperCase();
                Coordinate shot = new Coordinate(inputShot.charAt(0), Integer.parseInt(inputShot.substring(1)));
                battleField.takeShot(shot);
                break;
            } catch (BattleFieldException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Error! coordinates should be a letter followed by a digit, Try Again:");
            }
        }
    }

    public static void changeTurn() {
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
    }
}
