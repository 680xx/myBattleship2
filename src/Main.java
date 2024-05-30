import java.util.*;

public class Main {


    // Variabler
    private static int battleRounds = 0;
    private static int fireX;
    private static int fireY;
    private static int firstHitX;
    private static int firstHitY;
    private static int lastHitX;
    private static int lastHitY;
    private static int remainingFleet = 0;
    private static int delay = 1000;                                     // Ändra delay i millisekunder
    private static final int mapSize = 10;
    private static String verticalHorisontal = "unknown";
    private static boolean locatedShip = false;
    private static boolean checkSunkenShips = true;                    // Slå av/på sökning efter sjunkna skepp i närområdet


    // Objekt
    static Ship ship0 = new Ship("hangarfartyg", 5, "S0");
    static Ship ship1 = new Ship("slagskepp", 4,"S1");
    static Ship ship2 = new Ship("slagskepp", 4,"S2");
    static Ship ship3 = new Ship("kryssare", 3,"S3");
    static Ship ship4 = new Ship("kryssare", 3,"S4");
    static Ship ship5 = new Ship("kryssare", 3,"S5");
    static Ship ship6 = new Ship("ubåt", 2,"S6");
    static Ship ship7 = new Ship("ubåt", 2,"S7");
    static Ship ship8 = new Ship("ubåt", 2,"S8");
    static Ship ship9 = new Ship("ubåt", 2,"S9");

    // Kartor (2D-array)
    static String[][] map = new String[mapSize][mapSize];
    static String[][] enemyMap = new String[mapSize][mapSize];
    static String[][] enemyMap2 = new String[mapSize][mapSize];

    // Skeppslista (ArrayList)
    static List<Ship> shipList = new ArrayList<>();


    public static void main(String[] args) {

        initializeMaps();
        shipPlacement();
        runGame();


    }


    public static void initializeMaps () {

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                map[i][j] = "░";
                enemyMap[i][j] = "░";
                enemyMap2[i][j] = "░";
            }
        }
    }


    public static void shipPlacement() {

        // Variabler
        int placementTries = 0;
        int shipX;
        int shipY;
        int checkXStart;
        int checkXEnd;
        int checkYStart;
        int checkYEnd;

        // Addera objekt till lista
        shipList.add(ship0);
        shipList.add(ship1);
        shipList.add(ship2);
        shipList.add(ship3);
        shipList.add(ship4);
        shipList.add(ship5);
        shipList.add(ship6);
        shipList.add(ship7);
        shipList.add(ship8);
        shipList.add(ship9);

        // Addera antalet skepp till remainingFleet
        remainingFleet =+ shipList.size();

        Random random = new Random();

        // Placera Skepp
        for (int i = 0; i < shipList.size(); i++) {

            // Varibler
            boolean successfulPlacement = false;

            while (!successfulPlacement) {
                int collision = 0;
                boolean horizontalAlignment = random.nextBoolean();
                placementTries++;

                if (placementTries > 200) {
                    i = -1;
                    remainingFleet = 0;
                    placementTries = 0;
                    initializeMaps();
                    break;
                }

                // Kodblock vid horisontell utplacering
                if (horizontalAlignment) {

                    // Slumpmässig placering och grundsökområde
                    shipX = random.nextInt(mapSize - shipList.get(i).getLength() + 1);           // Kan ej placera ett skepp utanför kartan i X-led.
                    shipY = random.nextInt(mapSize);
                    checkXStart = shipX;
                    checkXEnd = shipX + 1 + (shipList.get(i).getLength() - 1);
                    checkYStart = shipY;
                    checkYEnd = shipY + 1;

                    // Eventuell utökning av sökområdet beroende på placering
                    if (shipX > 0) checkXStart--;
                    if (shipX + (shipList.get(i).getLength() - 1) < mapSize - 1) checkXEnd++;
                    if (shipY > 0) checkYStart--;
                    if (shipY < mapSize - 1) checkYEnd++;

                    // Kontrollerar sökområdet
                    for (int j = checkYStart; j < checkYEnd; j++) {
                        for (int k = checkXStart; k < checkXEnd; k++) {
                            if (!Objects.equals(map[j][k], "░")) collision++;
                        }
                    }

                    // Placerar skepp om det inte är någon kollision
                    if (collision == 0) {
                        for (int j = shipY; j < shipY + 1; j++) {
                            for (int k = shipX; k < (shipX + 1) + (shipList.get(i).getLength() - 1); k++) {
                                map[j][k] = String.valueOf(shipList.get(i).getShipIdentity());
                                enemyMap[j][k] = String.valueOf(shipList.get(i).getShipIdentity());
                                enemyMap2[j][k] = String.valueOf(shipList.get(i).getShipIdentity());
                            }
                        }
                        successfulPlacement = true;
                    }

                    // Kodblock vid vertikal utplacering
                } else {

                    // Slumpmässig placering och grundsökområde
                    shipX = random.nextInt(mapSize);
                    shipY = random.nextInt(mapSize - shipList.get(i).getLength() + 1);           // Kan ej placera ett skepp utanför kartan i Y-led.
                    checkXStart = shipX;
                    checkXEnd = shipX + 1;
                    checkYStart = shipY;
                    checkYEnd = shipY + 1 + (shipList.get(i).getLength() - 1);

                    // Eventuell utökning av sökområdet beroende på placering
                    if (shipX > 0) checkXStart--;
                    if (shipX < (mapSize - 1)) checkXEnd++;
                    if (shipY > 0) checkYStart--;
                    if (shipY + (shipList.get(i).getLength() - 1) < mapSize - 1) checkYEnd++;

                    // Kontrollerar sökområdet
                    for (int j = checkYStart; j < checkYEnd; j++) {
                        for (int k = checkXStart; k < checkXEnd; k++) {
                            if (!Objects.equals(map[j][k], "░")) collision++;
                        }
                    }

                    // Placerar skepp om det inte är någon kollision
                    if (collision == 0) {
                        for (int j = shipY; j < shipY + 1 + (shipList.get(i).getLength() - 1); j++) {
                            for (int k = shipX; k < shipX + 1; k++) {
                                map[j][k] = String.valueOf(shipList.get(i).getShipIdentity());
                                enemyMap [j][k] = String.valueOf(shipList.get(i).getShipIdentity());
                                enemyMap2 [j][k] = String.valueOf(shipList.get(i).getShipIdentity());
                            }
                        }
                        successfulPlacement = true;
                    }
                }
            }
        }
    }


    public static void drawMaps() {

        char verticalChar = 65;
        System.out.println("    0 1 2 3 4 5 6 7 8 9                    0 1 2 3 4 5 6 7 8 9");
        System.out.println("    ─ ─ ─ ─ ─ ─ ─ ─ ─ ─                    ─ ─ ─ ─ ─ ─ ─ ─ ─ ─");
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (j == 0) {
                    System.out.print(verticalChar + " | ");
                }
                if (!Objects.equals(map[i][j], "░")) {
                    System.out.print("╬" + " ");
                } else {
                    System.out.print(map[i][j] + " ");
                }
            }
            System.out.print("               ");
            for (int j = 0; j < mapSize; j++) {
                if (j == 0) {
                    System.out.print(verticalChar + " | ");
                    verticalChar++;
                }
                if (!Objects.equals(enemyMap[i][j], "·") && (!Objects.equals(enemyMap[i][j], "¤") && (!Objects.equals(enemyMap[i][j], "ø")))) {
                    System.out.print("░" + " ");
                } else {
                    if (Objects.equals(enemyMap[i][j], enemyMap[fireY][fireX])) {
                        System.out.print(enemyMap[i][j] + " ");
                    }
                    else System.out.print(enemyMap[i][j] + " ");

                }
            }
            System.out.print("               ");
            for (int j = 0; j < mapSize; j++) {
                if (i == 0 && j == 0) System.out.print(" Battle Round : " + battleRounds);
                else if (i == 1 && j == 0) System.out.print(" Round Coords : " + fireYAsString() + fireX);
                else if (i == 2 && j == 0) System.out.print(" Intelligence : " + checkSunkenShips);
                else if (i == 3 && j == 0) System.out.print("┌─────────────────┐");
                else if (i == 4 && j == 0) System.out.print("│ ░ = Water       │");
                else if (i == 5 && j == 0) System.out.print("│ ╬ = Ship        │");
                else if (i == 6 && j == 0) System.out.print("│ ¤ = Shot - Hit  │");
                else if (i == 7 && j == 0) System.out.print("│ · = Shot - Miss │");
                else if (i == 8 && j == 0) System.out.print("│ ø = Sunken Ship │");
                else if (i == 9 && j == 0) System.out.print("└─────────────────┘");
                else System.out.print(" ");

            }
            System.out.println();
        }
        System.out.println("    ─ ─ ─ ─ ─ ─ ─ ─ ─ ─                    ─ ─ ─ ─ ─ ─ ─ ─ ─ ─");
        System.out.println("      Local territory                        Enemy territory");
        System.out.println();
        System.out.println();
    }

    public static void runGame() {

        boolean gameOver = false;
        while (!gameOver) {

            aimFire();
            drawMaps();
            if (remainingFleet == 0) {
                result();
                gameOver = true;
            }

        }
    }

    public static void aimFire() {

        Random random = new Random();

        int fireXStart, fireXEnd, fireYStart, fireYEnd;
        boolean fireSuccess = false;

        while (!fireSuccess) {
            int shotDenied = 0;
            // Kodblock när man vet vart ett skepp är men inte om det är horisontellt/vertikalt
            if (locatedShip && Objects.equals(verticalHorisontal, "unknown")) {

                boolean guessedVertical = random.nextBoolean();     // Slumpar vertikalt/horisontellt
                boolean guessedUp = random.nextBoolean();           // Slumpar upp/ned
                boolean guessedRight = random.nextBoolean();         // Slumpar vänster/höger

                if (guessedVertical) {                                                  // Gissar på vertikalt placerat skepp
                    fireX = lastHitX;
                    if (lastHitY == mapSize) fireY = lastHitY--;                        // Högst upp på kartan, skjut nedåt.
                    else if (lastHitY == 0) fireY = lastHitY++;                         // Längst ner på kartan, skjut uppåt
                    else if (guessedUp) fireY = lastHitY++;                             // Skjuter på koordinaten ovanför senaste träff
                    else fireY = lastHitY--;                                            // Skjuter på koordinaten nedanför senaste träff

                } else {                                                                // Gissar på horisontellt placerat skepp
                    fireY = lastHitY;
                    if (lastHitX == mapSize) fireX = lastHitX--;                        // Längst till höger på kartan, skjut vänster.
                    else if (lastHitX == 0) fireX = lastHitY++;                         // Längst till vänster på kartan, skjut höger
                    else if (guessedRight) fireX = lastHitX++;                          // Skjuter på koordinaten till höger om senaste träff
                    else fireX = lastHitX--;                                            // SSkjuter på koordinaten till vänster om senaste träff
                }
            }
            // Kodblock när man att ett skepp är vertikalt beläget
/*        else if (locatedShip && Objects.equals(verticalHorisontal, "vertical")) {
        }

        // Kodblock när man att ett skepp är horisontellt beläget
        else if (locatedShip && Objects.equals(verticalHorisontal, "horisontal")) {
        }*/

            else {
                fireX = random.nextInt(mapSize);
                fireY = random.nextInt(mapSize);
            }

            // Slumpar koordinat om man inte vet vart ett skepp är beläget
            // Kontrollerar att det inte finns ett sänkt skepp runtom eftersom två skepp inte får vara bredvid varann




            fireXStart = fireX;
            fireXEnd = fireX;
            fireYStart = fireY;
            fireYEnd = fireY;

            // Eventuell utökning av sökområdet beroende på placering gentemot spelets kanter

            if (fireXStart > 0) fireXStart--;
            if (fireXEnd < (mapSize - 1)) fireXEnd++;
            if (fireYStart > 0) fireYStart--;
            if (fireYEnd < (mapSize - 1)) fireYEnd++;

            // Kontrollerar sökområdet runtom skottkoordinat efter sänkta skepp
            // Minskar gissningar med ca 20-25 försök.

            if (checkSunkenShips) {
                for (int i = fireYStart; i <= fireYEnd; i++) {
                    for (int j = fireXStart; j <= fireXEnd; j++) {
                        if (Objects.equals(enemyMap[i][j], "ø")) {
                            shotDenied++;
                        }
                    }
                }
            }

            // Kontrollerar skottkoordinat

            if (Objects.equals(enemyMap[fireY][fireX], "¤") || (Objects.equals(enemyMap[fireY][fireX], "·") || (Objects.equals(enemyMap[fireY][fireX], "ø")))) {
                shotDenied++;
            }

            // Avfyrar om inga kollisioner

            if (shotDenied == 0) {
                battleRounds++;
                if (!Objects.equals(enemyMap2[fireY][fireX], "░")) {
                    if (!locatedShip) {
                        firstHitX = fireX;
                        firstHitY = fireY;
                    }
                    lastHitX = fireX;
                    lastHitY = fireY;
                    if ((firstHitX == lastHitX) && (firstHitY != lastHitY)) verticalHorisontal = "horisontal";
                    if ((firstHitY == lastHitY) && (firstHitX != lastHitX)) verticalHorisontal = "vertical";
                    locatedShip = true;
                    enemyMap[fireY][fireX] = "¤";
                    checkShip();
                } else {
                    enemyMap[fireY][fireX] = "·";
                }
                try{Thread.sleep(delay);}catch (Exception ignore){}
                fireSuccess = true;
            }
        }
    }

    public static void checkShip() {
        if (Objects.equals(enemyMap2[fireY][fireX], shipList.get(0).getShipIdentity())) {
            ship0.setHits(ship0.getHits()+1);
            if (ship0.getHits() == ship0.getLength()) {
                remainingFleet--;
                verticalHorisontal = "unknown";
                locatedShip = false;
                for (int i = 0; i < mapSize; i++) {
                    for (int j = 0; j < mapSize; j++) {
                        if (Objects.equals(enemyMap2[i][j], shipList.get(0).getShipIdentity())) enemyMap[i][j] = "ø";
                    }
                }
            }
        }
        if (Objects.equals(enemyMap2[fireY][fireX], shipList.get(1).getShipIdentity())) {
            ship1.setHits(ship1.getHits()+1);
            if (ship1.getHits() == ship1.getLength()) {
                remainingFleet--;
                verticalHorisontal = "unknown";
                locatedShip = false;
                for (int i = 0; i < mapSize; i++) {
                    for (int j = 0; j < mapSize; j++) {
                        if (Objects.equals(enemyMap2[i][j], shipList.get(1).getShipIdentity())) enemyMap[i][j] = "ø";
                    }
                }
            }
        }
        if (Objects.equals(enemyMap2[fireY][fireX], shipList.get(2).getShipIdentity())) {
            ship2.setHits(ship2.getHits()+1);
            if (ship2.getHits() == ship2.getLength()) {
                remainingFleet--;
                verticalHorisontal = "unknown";
                locatedShip = false;
                for (int i = 0; i < mapSize; i++) {
                    for (int j = 0; j < mapSize; j++) {
                        if (Objects.equals(enemyMap2[i][j], shipList.get(2).getShipIdentity())) enemyMap[i][j] = "ø";
                    }
                }
            }
        }
        if (Objects.equals(enemyMap2[fireY][fireX], shipList.get(3).getShipIdentity())) {
            ship3.setHits(ship3.getHits()+1);
            if (ship3.getHits() == ship3.getLength()) {
                remainingFleet--;
                verticalHorisontal = "unknown";
                locatedShip = false;
                for (int i = 0; i < mapSize; i++) {
                    for (int j = 0; j < mapSize; j++) {
                        if (Objects.equals(enemyMap2[i][j], shipList.get(3).getShipIdentity())) enemyMap[i][j] = "ø";
                    }
                }
            }
        }
        if (Objects.equals(enemyMap2[fireY][fireX], shipList.get(4).getShipIdentity())) {
            ship4.setHits(ship4.getHits()+1);
            if (ship4.getHits() == ship4.getLength()) {
                remainingFleet--;
                verticalHorisontal = "unknown";
                locatedShip = false;
                for (int i = 0; i < mapSize; i++) {
                    for (int j = 0; j < mapSize; j++) {
                        if (Objects.equals(enemyMap2[i][j], shipList.get(4).getShipIdentity())) enemyMap[i][j] = "ø";
                    }
                }
            }
        }
        if (Objects.equals(enemyMap2[fireY][fireX], shipList.get(5).getShipIdentity())) {
            ship5.setHits(ship5.getHits()+1);
            if (ship5.getHits() == ship5.getLength()) {
                remainingFleet--;
                verticalHorisontal = "unknown";
                locatedShip = false;
                for (int i = 0; i < mapSize; i++) {
                    for (int j = 0; j < mapSize; j++) {
                        if (Objects.equals(enemyMap2[i][j], shipList.get(5).getShipIdentity())) enemyMap[i][j] = "ø";
                    }
                }
            }
        }
        if (Objects.equals(enemyMap2[fireY][fireX], shipList.get(6).getShipIdentity())) {
            ship6.setHits(ship6.getHits()+1);
            if (ship6.getHits() == ship6.getLength()) {
                remainingFleet--;
                verticalHorisontal = "unknown";
                locatedShip = false;
                for (int i = 0; i < mapSize; i++) {
                    for (int j = 0; j < mapSize; j++) {
                        if (Objects.equals(enemyMap2[i][j], shipList.get(6).getShipIdentity())) enemyMap[i][j] = "ø";
                    }
                }
            }
        }
        if (Objects.equals(enemyMap2[fireY][fireX], shipList.get(7).getShipIdentity())) {
            ship7.setHits(ship7.getHits()+1);
            if (ship7.getHits() == ship7.getLength()) {
                remainingFleet--;
                verticalHorisontal = "unknown";
                locatedShip = false;
                for (int i = 0; i < mapSize; i++) {
                    for (int j = 0; j < mapSize; j++) {
                        if (Objects.equals(enemyMap2[i][j], shipList.get(7).getShipIdentity())) enemyMap[i][j] = "ø";
                    }
                }
            }
        }
        if (Objects.equals(enemyMap2[fireY][fireX], shipList.get(8).getShipIdentity())) {
            ship8.setHits(ship8.getHits()+1);
            if (ship8.getHits() == ship8.getLength()) {
                remainingFleet--;
                verticalHorisontal = "unknown";
                locatedShip = false;
                for (int i = 0; i < mapSize; i++) {
                    for (int j = 0; j < mapSize; j++) {
                        if (Objects.equals(enemyMap2[i][j], shipList.get(8).getShipIdentity())) enemyMap[i][j] = "ø";
                    }
                }
            }
        }
        if (Objects.equals(enemyMap2[fireY][fireX], shipList.get(9).getShipIdentity())) {
            ship9.setHits(ship9.getHits()+1);
            if (ship9.getHits() == ship9.getLength()) {
                remainingFleet--;
                verticalHorisontal = "unknown";
                locatedShip = false;
                for (int i = 0; i < mapSize; i++) {
                    for (int j = 0; j < mapSize; j++) {
                        if (Objects.equals(enemyMap2[i][j], shipList.get(9).getShipIdentity())) enemyMap[i][j] = "ø";
                    }
                }
            }
        }
    }

    // Skriver ut skeppens status efter spelet är slut
    public static void result() {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("S0, längd / träff: " + ship0.getLength() + " / " + ship0.getHits());
        System.out.println("S1, längd / träff: " + ship1.getLength() + " / " + ship1.getHits());
        System.out.println("S2, längd / träff: " + ship2.getLength() + " / " + ship2.getHits());
        System.out.println("S3, längd / träff: " + ship3.getLength() + " / " + ship3.getHits());
        System.out.println("S4, längd / träff: " + ship4.getLength() + " / " + ship4.getHits());
        System.out.println("S5, längd / träff: " + ship5.getLength() + " / " + ship5.getHits());
        System.out.println("S6, längd / träff: " + ship6.getLength() + " / " + ship6.getHits());
        System.out.println("S7, längd / träff: " + ship7.getLength() + " / " + ship7.getHits());
        System.out.println("S8, längd / träff: " + ship8.getLength() + " / " + ship8.getHits());
        System.out.println("S9, längd / träff: " + ship9.getLength() + " / " + ship9.getHits());
    }

    // Konverterar variabeln FireY från siffra till bokstav och returnerar
    public static String fireYAsString() {
        HashMap<Integer, String> convertNumberLetter = new HashMap<>();
        convertNumberLetter.put(0, "A");
        convertNumberLetter.put(1, "B");
        convertNumberLetter.put(2, "C");
        convertNumberLetter.put(3, "D");
        convertNumberLetter.put(4, "E");
        convertNumberLetter.put(5, "F");
        convertNumberLetter.put(6, "G");
        convertNumberLetter.put(7, "H");
        convertNumberLetter.put(8, "I");
        convertNumberLetter.put(9, "J");

        return convertNumberLetter.get(fireY);
    }

}
