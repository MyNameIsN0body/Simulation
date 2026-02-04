package com.petproject.simulation.simulation;

import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.world.WorldMap;

public final class GameMessenger {
    private GameMessenger() {
    }

    private final static String START_BANNER =
            "╔" + "═".repeat(77) + "╗\n" +
                    "║                                 СИМУЛЯЦИЯ                                   ║\n" +
                    "╠" + "═".repeat(77) + "╣\n" +
                    "║ Это симуляция 2D‑мира, где травоядные ищут траву, а хищники охотятся на     ║\n" +
                    "║ травоядных. Каждый ход существа принимают решения: двигаться, питаться или  ║\n" +
                    "║ атаковать. Ваша задача — наблюдать за динамикой экосистемы и пробовать      ║\n" +
                    "║ находить баланс между видами.                                               ║\n" +
                    "╚" + "═".repeat(77) + "╝";

    private final static String SELECT_MODE_TEXT = """
            ╔══════════════════════════════════════════════╗
            ║         ВЫБОР РЕЖИМА СИМУЛЯЦИИ               ║
            ╠══════════════════════════════════════════════╣
            ║  1. Пошаговый режим (ручное управление)      ║
            ║  2. Автоматический режим (бесконечный цикл)  ║
            ╚══════════════════════════════════════════════╝
            Укажите режим (1 или 2): 
            """;

    private final static String AUTO_MENU = """
                        ╔══════════════════════════════╗
                        ║     УПРАВЛЕНИЕ СИМУЛЯЦИЕЙ    ║
                        ╚══════════════════════════════╝
                          run/r      Старт симуляции      
                          pause/p    Пауза
                          continue/c Продолжить        
                          step/s     Сделать шаг вручную     
                          + n        Увеличить скорость      
                          - n        Уменьшить скорость      
                          m          Показать меню           
                          exit/e     Выход                   
                        ════════════════════════════════
                """;

    private final static String STEP_BY_STEP_MENU = """
                        ╔═══════════════════════════════════════════╗
                        ║           УПРАВЛЕНИЕ СИМУЛЯЦИЕЙ           ║
                        ╚═══════════════════════════════════════════╝
                          step/st      Сделать шаг вручную
                          +grass/ag    Добавить растений   
                          reproduce/r  Шаг и попытка воспроизводства 
                                       у травоядных
                          exit/e       Выход                   
                        ═════════════════════════════════════════════
                """;

    public static void displayIntro() {
        System.out.println(START_BANNER);
    }

    public static void displaySelectMode() {
        System.out.println(SELECT_MODE_TEXT);
    }

    public static void showStepByStepModeMenu() {
        System.out.println(STEP_BY_STEP_MENU);
    }

    public static void showAutoModeMenu() {
        System.out.println(AUTO_MENU);
    }

    public static void showStatus(WorldMap worldMap, int turn) {
        int predatorCount = 0;
        int herbivoreCount = 0;
        int grassCount = 0;
        for(Entity entity: worldMap.getAllEntities()) {
            EntityType entityType = entity.getType();
            if (entityType == EntityType.PREDATOR) {
                predatorCount++;
            } else if (entityType == EntityType.HERBIVORE) {
                herbivoreCount++;
            } else if (entityType == EntityType.GRASS) {
                grassCount++;
            }
        }
        System.out.printf("Состояние игры:%n  Ход номер: %d%n  Хищников: %d%n  Травоядных: %d%n  Травы: %d%n",
                turn, predatorCount, herbivoreCount, grassCount);
    }

}
