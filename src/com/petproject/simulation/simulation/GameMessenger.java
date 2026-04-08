package com.petproject.simulation.simulation;

public final class GameMessenger {
    private GameMessenger() {
    }

    private final static String START_BANNER = """
                \u001B[36m╔═════════════════════════════════════════════════════════════════════════════╗\u001B[0m
                \u001B[36m║\u001B[33m                                  СИМУЛЯЦИЯ                                  \u001B[36m║\u001B[0m
                \u001B[36m╠═════════════════════════════════════════════════════════════════════════════╣\u001B[0m
                \u001B[36m║ Это симуляция 2D‑мира, где травоядные ищут траву, а хищники охотятся на     ║\u001B[0m
                \u001B[36m║ травоядных. Каждый ход существа принимают решения: двигаться, питаться или  ║\u001B[0m
                \u001B[36m║ атаковать. Ваша задача — наблюдать за динамикой экосистемы и пробовать      ║\u001B[0m
                \u001B[36m║ находить баланс между видами.                                               ║\u001B[0m
                \u001B[36m╚═════════════════════════════════════════════════════════════════════════════╝\u001B[0m
            """;

    private final static String SELECT_MODE_TEXT = """
                \u001B[36m╔══════════════════════════════════════════════╗\u001B[0m
                \u001B[36m║\u001B[33m            ВЫБОР РЕЖИМА СИМУЛЯЦИИ            \u001B[36m║\u001B[0m
                \u001B[36m╠══════════════════════════════════════════════╣\u001B[0m
                \u001B[36m║  \u001B[34m1.\u001B[0m \u001B[36mПошаговый режим (ручное управление)      ║\u001B[0m
                \u001B[36m║  \u001B[32m2.\u001B[0m \u001B[36mАвтоматический режим (бесконечный цикл)  ║\u001B[0m
                \u001B[36m╚══════════════════════════════════════════════╝\u001B[0m
            Укажите режим (\u001B[34m1\u001B[0m или \u001B[32m2\u001B[0m): 
            """;

    private final static String AUTO_MENU = """
                        \u001B[36m╔═══════════════════════════════════╗\u001B[0m
                        \u001B[36m║\u001B[33m     УПРАВЛЕНИЕ СИМУЛЯЦИЕЙ         \u001B[36m║\u001B[0m
                        \u001B[36m╠═══════════════════════════════════╣\u001B[0m
                        \u001B[36m║\u001B[32m  run/r       Старт симуляции      \u001B[36m║\u001B[0m
                        \u001B[36m║\u001B[34m  pause/p    Пауза                 \u001B[36m║\u001B[0m
                        \u001B[36m║\u001B[33m  continue/c Продолжить            \u001B[36m║\u001B[0m
                        \u001B[36m║\u001B[36m  step/s     Сделать шаг вручную   \u001B[36m║\u001B[0m  
                        \u001B[36m║\u001B[34m  menu/m     Показать меню         \u001B[36m║\u001B[0m  
                        \u001B[36m║\u001B[31m  exit/e     Выход                 \u001B[36m║\u001B[0m  
                        \u001B[36m╚═══════════════════════════════════╝\u001B[0m
                """;

    private final static String STEP_BY_STEP_MENU = """
       \u001B[36m╔════════════════════════════════════════════╗\u001B[0m
       \u001B[36m║\u001B[33m         СИСТЕМА УПРАВЛЕНИЯ СИМУЛЯЦИЕЙ      \u001B[36m║\u001B[0m
       \u001B[36m╠════════════════════════════════════════════╣\u001B[0m
       \u001B[36m║\u001B[34m  step/s          \u001B[37m▶  Сделать ход            \u001B[36m║\u001B[0m
       \u001B[36m║\u001B[32m  reproduce/r     \u001B[37m⏩  Попытка размножения    \u001B[36m║\u001B[0m
       \u001B[36m║\u001B[33m  +grass/ag       \u001B[37m+  Добавить травы         \u001B[36m║\u001B[0m
       \u001B[36m║\u001B[36m  menu/m          \u001B[37m📋 Показать меню          \u001B[36m║\u001B[0m
       \u001B[36m║\u001B[31m  exit/e          \u001B[37m⏹  Выход                  \u001B[36m║\u001B[0m
       \u001B[36m╚════════════════════════════════════════════╝\u001B[0m
    """;

    public static void displayIntro() {
        System.out.println("\n".repeat(3) + START_BANNER);
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

    public static void showStatus(GameStats stats) {

        System.out.printf("""
                          \u001B[33m Состояние игры:\u001B[0m
              ⏱️ Ход номер: \u001B[32m%-2d\u001B[0m     🌱 Травы:      \u001B[32m%-2d\u001B[0m
              🦖 Хищников:  \u001B[32m%-2d\u001B[0m     🦌 Травоядных: \u001B[32m%-2d\u001B[0m
        """, stats.getTurn(), stats.getGrass(), stats.getPredators(), stats.getHerbivore());
        System.out.println("\n".repeat(3));
    }

}
