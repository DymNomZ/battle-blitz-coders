package src;
import classes.entities.dummy;
import classes.map.tile;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class map_constructor {
    //Dymes
    //BufferedReader reads data all at once from a stream in this case out map txt.file instead of reading it byte by byte
    //which you can imagine would take too much time
    //more info: https://stackoverflow.com/questions/28929643/understanding-how-bufferedreader-works-in-java
    BufferedReader reader;
    tile tiles[] = new tile[5];
    int map_tiles[][] = new int[50][50];

    //the following variables is temporary as of the moment
    String kind;

    public int[][] getMap_tiles(){
        return map_tiles;
    }

    //Will hold the last coordinates before the camera entered edge mode
    int recent_x = 0, recent_y = 0;
    public map_constructor(String kind, int rows, int cols){
        this.kind = kind;

        initialize_map_tiles(rows, cols);
    }

    void initialize_map_tiles(int rows, int cols){
        System.out.println("Initializing map tiles of: " + kind);
        //We load the textures in to the tiles array where the index will act as a type number
        try {
            tiles[0] = new tile("../../assets/map_tiles/temp_green.png", "temp_green");
            tiles[1] = new tile("../../assets/map_tiles/temp_blue.png", "temp_blue");
            tiles[2] = new tile("../../assets/map_tiles/temp_red.png", "temp_red");
            tiles[3] = new tile("../../assets/map_tiles/temp_orange.png", "temp_orange");
            tiles[4] = new tile("../../assets/map_tiles/temp_tile.png", "temp_tile");
        } catch (Exception e) {
            System.out.println("Error retrieving tile textures for map: " + kind);
        }

        //We use FileReader as we are only rading characters, other readers are too much for our usage
        //More info: https://stackoverflow.com/questions/7991770/inputstreamreader-vs-filereader
        //Encountered an error when reading earlier, this fixed it: https://stackoverflow.com/questions/22585621/the-filereader-cannot-find-my-text-file
        try {
            reader = new BufferedReader(new FileReader("assets/maps/test_pattern_map.txt"));
        } catch (IOException e) {
            System.out.println("Error reading map data for map: " + kind);
        }

        //This array will store the integers per line from the txt file
        String tile_types[] = new String[50];
        int row = 0, col = 0;

        for(; row < rows; row++){
            try {
                //readline returns a string of the first line of text then moves to the next line after it is called.
                tile_types = reader.readLine().split(" ");
            } catch (IOException e) {
                System.out.println("Error reading line " + row + " of " + kind + " map_data");
            }
            for(; col < cols; col++) map_tiles[row][col] = Integer.parseInt(tile_types[col]);
            col = 0;
        }
        System.out.println("Intizializing complete");
    }

    void display_tiles(
        Graphics g, int TILE_SIZE, 
        int map_height, int map_length, 
        dummy d, 
        int SCREEN_HEIGHT, int SCREEN_WIDTH){
        //Dymes - Oct 2, 2024
        //these variables are intended for easier use once we have multiple maps
        //because we will store the map data inside an array, the names explain what they do
        //they get the tile associated with that specific index of the row and col
        int map_tile_row = 0, map_tile_col = 0;

        //Just like the dummy class, these variables will dictate where a specific tile will be drawn to the screen
        //Essentially, tile_pos variables will hold the position of the tile relative to the map data
        //While screen_pos variables will hold the position of the tile relative to the screen
        //So its all illusions? Always has been
        int tile_x, tile_y, screen_x, screen_y;

        while(map_tile_row < map_height){
            //indicates the position of the tile on the map
            tile_y = map_tile_row * TILE_SIZE; //we multiply to "jump" the length of tile size to not overwrite the current pixel displayed
                                            //the idea is similar to accessing an array in C without using index access

            while(map_tile_col < map_length){
                tile_x = map_tile_col * TILE_SIZE;
                
                //Checks if the camera should enter edge mode
                //How it works is that it checks if the dummy's position when rendering the tiles of the map visible to the screen
                //won't go beyond the below the maximum values of our screen height and width which is 640 and 1280 respectively.
                //ie if the dummy is at x position 640, you can think of it that the dummy is 640 pixels away from the border
                //any lower than that it will display the "void" because the screen has to display something to fit the 1280x640 screen
                //maka explain rako guys puhon if mo ask mo AHAHAHAHAHHAH
                if ((d.x_pos >= d.screen_x && d.x_pos <= ((map_length * TILE_SIZE) - (d.screen_x + TILE_SIZE))
                && (d.y_pos >= d.screen_y && d.y_pos <= ((map_height * TILE_SIZE) - (d.screen_y + TILE_SIZE))))) {
                    recent_x = d.x_pos;
                    recent_y = d.y_pos;
                    
                    //The purpose of this if statement is to only display the tiles that we can see in the screen
                    //Not adding this condition will render the entire tiles of the map which would hurt the game's performance once it gets larger
                    //So it checks whether a specific tile is within range of the position of the player +- its x and y on the screen
                    //Basically it checks whether a tile is inside the screen, if not, we do not display it to save resources
                    //We add tile_pos with the tile_size, basically we check a tile extra, because if you notice when you remove the addition
                    //It has a "border" so we check tiles that are just shy of being part of the screen so that it removes that border
                    if ((tile_x + TILE_SIZE > d.x_pos - d.screen_x && tile_x - TILE_SIZE < d.x_pos + d.screen_x) &&
                    (tile_y + TILE_SIZE > d.y_pos - d.screen_y && tile_y - TILE_SIZE < d.y_pos + d.screen_y)) {

                        //This will handle where the tile will be drawn relative to the screen, since it passed the check if a tile is within our screen or not
                        //It works by taking the position of a tile relative to the map and subtract our dummy's position
                        //next we add the dummy's screen position in this case which is at the center so for our 1280x640 screen, the dummy's position is at x y 640 320
                        //for simplicity, let's say our dummy is at the very edge of the map, in our temporary 50x50 map, multiplied by 64 gives us 3200x3200
                        //that means out dummy is standing on a tile at 3200x3200 now we can't just draw the tile to the screen directly at 3200x3200 because rember the screen is only 1280x640
                        //that's why we add the screen values of the dummy which is always 640 and 320 since our dummy sprite is always at the center
                        //adding the screen values display the tile relative to the screen regardless of actual coordinates on the map
                        //so in this case for x, 3200 tile pos - 3200 player pos = 0 + 640 which is 640, meaning the tile will be displayed beneath our dummy
                        //which is correct as we are standing on it in this example
                        screen_x = tile_x - d.x_pos + d.screen_x;    
                        screen_y = tile_y - d.y_pos + d.screen_y; 

                        //Get the tile type of a specific tile of the map
                        g.drawImage(
                            tiles[map_tiles[map_tile_row][map_tile_col]].texture, 
                            screen_x, 
                            screen_y, 
                            TILE_SIZE, TILE_SIZE, 
                            null
                        );
                    }
                } else {
                    //The following if-else if statements is the mechanism for the "dragging" of the camera by the dummy when in edge mode
                    //It check whether the dummy is outside the borders of the screen, if so then it sorts of "pushes" the camera with it
                    //we add 10 as that is our temporary default speed variable of our character, this is will be changed once we have a finalized walk speed value
                    if(d.y_pos < recent_y - (SCREEN_HEIGHT/2)) recent_y -= d.getSpeed();
                    else if (d.y_pos > recent_y + (SCREEN_HEIGHT/2)) recent_y += d.getSpeed();
                    
                    if (d.x_pos < recent_x - (SCREEN_WIDTH/2)) recent_x -= d.getSpeed();
                    else if (d.x_pos > recent_x + (SCREEN_WIDTH/2)) recent_x += d.getSpeed();

                    //same idea of the above comment on the above if statement
                    screen_x = tile_x - recent_x + d.screen_x;    
                    screen_y = tile_y - recent_y + d.screen_y; 
                    g.drawImage(
                        tiles[map_tiles[map_tile_row][map_tile_col]].texture, 
                        screen_x, 
                        screen_y, 
                        TILE_SIZE, TILE_SIZE, 
                        null
                    );
                }
                map_tile_col++;
            }
            map_tile_col = 0;
            map_tile_row++;
        }
    }
}
