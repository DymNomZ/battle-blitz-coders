package src;
import classes.entities.Dummy;
import classes.map.Tile;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.util.ArrayList;

public class MapConstructor {

    MapLoader map_loader;
    BufferedReader reader;
    ArrayList<Tile> tile_data;
    Tile tiles[][];
    int map_indexes[][];
    int map_height, map_length;
    int recent_x = 0, recent_y = 0;

    String map_path;

    public MapConstructor(String path){

        map_path = path;

        map_loader = new MapLoader();
        map_loader.loadMapData(map_path);

        tile_data = map_loader.getTiles();
        map_indexes = map_loader.getMapIndexes();

        map_height = map_indexes.length;
        map_length = map_indexes[0].length;

        initializeTiles();
    }

    public int[][] getMap_indexes(){
        return map_indexes;
    }

    public int getMap_height(){
        return map_height;
    }

    public int getMap_length(){
        return map_length;
    }

    void initializeTiles(){

        tiles = new Tile[map_height][map_length];
        for(int i = 0; i < map_height; i++){
            for(int j = 0; j < map_length; j++){
                
                //check which tile in tile data matches index
                if(map_indexes[i][j] != 0){
                    for(Tile td : tile_data){
                        if(td.index == map_indexes[i][j]){
                            tiles[i][j] = td;
                            break;
                        }
                    }
                }else{
                    tiles[i][j] = new Tile("../../assets/map_tiles/void.png", 0, "void");
                }
                    
            }
        }
    }

    void displayTiles(
        Graphics g, int TILE_SIZE, Dummy d, 
        int SCREEN_HEIGHT, int SCREEN_WIDTH){

        int map_tile_row = 0, map_tile_col = 0;
        int tile_x, tile_y, screen_x, screen_y;

        while(map_tile_row < map_height){

            tile_y = map_tile_row * TILE_SIZE; 

            while(map_tile_col < map_length){

                tile_x = map_tile_col * TILE_SIZE;

                if ((d.x_pos >= d.screen_x && d.x_pos <= ((map_length * TILE_SIZE) - (d.screen_x + TILE_SIZE))
                && (d.y_pos >= d.screen_y && d.y_pos <= ((map_height * TILE_SIZE) - (d.screen_y + TILE_SIZE))))) {
                    recent_x = d.x_pos;
                    recent_y = d.y_pos;
                    
                    if ((tile_x + TILE_SIZE > d.x_pos - d.screen_x && tile_x - TILE_SIZE < d.x_pos + d.screen_x) &&
                    (tile_y + TILE_SIZE > d.y_pos - d.screen_y && tile_y - TILE_SIZE < d.y_pos + d.screen_y)) {

                        screen_x = tile_x - d.x_pos + d.screen_x;    
                        screen_y = tile_y - d.y_pos + d.screen_y; 

                        g.drawImage(
                            tiles[map_tile_row][map_tile_col].image, 
                            screen_x, 
                            screen_y, 
                            TILE_SIZE, TILE_SIZE, 
                            null
                        );
                    }
                } else {

                    if(d.y_pos < recent_y - (SCREEN_HEIGHT/2)) recent_y -= d.getSpeed();
                    else if (d.y_pos > recent_y + (SCREEN_HEIGHT/2)) recent_y += d.getSpeed();
                    
                    if (d.x_pos < recent_x - (SCREEN_WIDTH/2)) recent_x -= d.getSpeed();
                    else if (d.x_pos > recent_x + (SCREEN_WIDTH/2)) recent_x += d.getSpeed();

                    screen_x = tile_x - recent_x + d.screen_x;    
                    screen_y = tile_y - recent_y + d.screen_y; 
                    g.drawImage(
                        tiles[map_tile_row][map_tile_col].image, 
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
