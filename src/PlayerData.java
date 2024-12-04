package src;

public class PlayerData {
    
    public static int kill_count, required_kills, check_pointer;
    public static boolean received_initial_weapon, trigger_spawning, trigger_range_info, range_info_shown;
    public static boolean is_alive, cleared_stage;
    public static boolean[] stage_spawns;
    public static int[] position_checks = {1820, 4000, 8050, 10115};
    public static int[] offsets = {35, 60, 125, 250};

    public static void initializeValues(){
        kill_count = 0;
        required_kills = 10;
        stage_spawns = new boolean[]{false, false, false, false};
        received_initial_weapon = false;
        trigger_spawning = false;
        trigger_range_info = false;
        range_info_shown = false;
        is_alive = true;
        cleared_stage = false;
        check_pointer = 0;
    }

}
