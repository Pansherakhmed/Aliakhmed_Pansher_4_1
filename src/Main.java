import java.util.Random;

public class Main {
    public static int bossHealth = 7000;
    public static int bossDamage = 50;
    public static String bossDefenceType;
    public static int[] heroesHealth = {250, 260, 170, 340,500, 250, 300, 270};
    public static int[] heroesDamage = {25, 20, 15, 0, 6, 13, 25, 17};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Thor", "Berserk", "Lucky", "Golem"};
    public static int roundNumber = 0;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            playRound();
        }

    }
    public static void thor() {
        Random thorEffect = new Random();
        boolean stop = thorEffect.nextBoolean();
        if (heroesHealth[7] > 0 && stop){
            bossDamage = 0;
        }
    }

    public static void blocking() {
        Random randomBlocking = new Random();
        int blocking = randomBlocking.nextInt(22);
        if (heroesHealth[6] > 0){
            heroesDamage[6] += blocking;
            heroesHealth[6] += blocking;
        }
    }

    public static void lucky() {
        Random randomLucky = new Random();
        boolean randomEvade = randomLucky.nextBoolean();
        if (randomEvade) {
            heroesHealth[5] += bossDamage;
        }
    }


    public static void golem() {
        bossDamage -= bossDamage / 5;
    }

    public static boolean treatMedic() {
        boolean treat = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[3] < 100 == false) {
                if (heroesHealth[i] < 100) {
                    heroesHealth[i] += 100;
                    treat = false;
                    break;
                }
            }
        }
        return treat;
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        printStatistics();
        treatMedic();
        thor();
        blocking();
        lucky();
        golem();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefenceType = heroesAttackType[randomIndex];
    }

    public static void bossHits() {

        for (int i = 0; i < heroesHealth.length; i++) {

            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage; // heroesHealth[i] -= bossDamage
                }
            }

        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int hit = heroesDamage[i];
                if (bossDefenceType == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(7) + 2; // 2,3,4,5,6,7,8
                    hit = coeff * heroesDamage[i];
                    System.out.println("Critical Damage: " + hit);
                }
                if (bossHealth - hit < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - hit; // bossHealth -= heroesDamage[i]
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " --------------");
        /*String defence;
        if (bossDefenceType == null) {
            defence = "No defence";
        } else {
            defence = bossDefenceType;
        }*/
        System.out.println("Boss health: " + bossHealth + "; damage: "
                + bossDamage + "; defence: " + (bossDefenceType == null ? "No defence" : bossDefenceType));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + "; damage: "
                    + heroesDamage[i]);
        }
    }
}