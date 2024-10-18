package src;
import classes.entities.Dummy;
import classes.entities.PanelEntity;
import classes.map.Tile;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.util.ArrayList;

import classes.entities.CameraEntity;

public class MapConstructor {

    MapLoader map_loader;
    BufferedReader reader;
    ArrayList<Tile> tile_data;
    Tile tiles[][];
    int map_indexes[][];
    int map_height, map_length;
    int recent_x = 0, recent_y = 0;

    public CameraEntity camera;
    String map_path;
    int tileSize;

    public MapConstructor(String path, int screenWidth, int screenHeight, int tileSize){
        this.tileSize = tileSize;
        map_path = path;

        map_loader = new MapLoader();
        map_loader.loadMapData(map_path);

        tile_data = map_loader.getTiles();
        map_indexes = map_loader.getMapIndexes();

        map_height = map_indexes.length;
        map_length = map_indexes[0].length;

        initializeTiles();
        camera = new CameraEntity(screenWidth, screenHeight);
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
                        if(td.tileType == map_indexes[i][j]){
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

    // Will not support speed that is == to tileSize or higher
    public void verifyEntityPosition(PanelEntity e){
        if (e.deltaX != 0) {
            int offTile = (e.x % tileSize) + e.deltaX;

            if (e.x + e.deltaX < 0) {
                e.x = 0;
            } else if (e.x + e.width + e.deltaX > (map_length * tileSize)) {
                e.x = (map_length * tileSize) - e.width;
            } else if (offTile > 0 && offTile < tileSize) {
                e.x += e.deltaX;
            } else {
                int offXTile;

                if (offTile >= tileSize) {
                    offXTile = (e.x + e.deltaX + e.width) / tileSize;
                } else {
                    offXTile = (e.x + e.deltaX) / tileSize;
                }

                int offYTile = e.y / tileSize;

                System.out.println(map_indexes[offYTile][offXTile] + " ");

                if (map_indexes[offYTile][offXTile] == 4 || map_indexes[offYTile][offXTile] == 5) {
                    if (offTile < 0) {
                        e.x += e.deltaX - offTile;
                    } else {
                        e.x += e.deltaX - tileSize + (e.x % tileSize);
                    }
                } else if (e.y % tileSize > 0 && (map_indexes[offYTile + 1][offXTile] == 4 || map_indexes[offYTile + 1][offXTile] == 5)) {
                    if (offTile < 0) {
                        e.x += e.deltaX - offTile;
                    } else {
                        e.x += e.deltaX - tileSize + (e.x % tileSize);
                    }
                } else {
                    e.x += e.deltaX;
                }
            }

            e.deltaX = 0;
        }

        if (e.deltaY != 0) {
            int offTile = (e.y % tileSize) + e.deltaY;

            if (e.y + e.deltaY < 0) {
                e.y = 0;
            } else if (e.y + e.height + e.deltaY > (map_height * tileSize)) {
                e.y = (map_height * tileSize) - e.height;
            } else if (offTile >= 0 && offTile <= tileSize) {
                e.y += e.deltaY;
            } else {
                int offYTile;

                if (offTile >= tileSize) {
                    offYTile = (e.y + e.deltaY + e.height) / tileSize;
                } else {
                    offYTile = (e.y + e.deltaY) / tileSize;
                }

                int offXTile = e.x / tileSize;
                e.y += e.deltaY;

                if (map_indexes[offYTile][offXTile] == 4 || map_indexes[offYTile][offXTile] == 5) {
                    if (offTile < 0) {
                        e.y -= offTile;
                    } else {
                        e.y -= e.y % tileSize;
                    }
                } else if (e.y % tileSize > 0 && (map_indexes[offYTile][offXTile + 1] == 4 || map_indexes[offYTile][offXTile + 1] == 5)) {
                    if (offTile < 0) {
                        e.y -= offTile;
                    } else {
                        e.y -= e.y % tileSize;
                    }
                }
            }

            e.deltaY = 0;
        }
    }

    // Note: x and y not centered
    public void view(int x, int y) {
        camera.x = Math.max(x, 0);
        camera.x = Math.min(camera.x, (map_length * tileSize) - camera.width);

        camera.y = Math.max(y, 0);
        camera.y = Math.min(camera.y, (map_height * tileSize) - camera.height);
    }

    // Note: center to entity
    public void view(PanelEntity e) {
        camera.x = Math.max(0, e.x - ((camera.width / 2) - (e.width / 2) - 2));
        camera.x = Math.min(camera.x, (map_length * tileSize) - camera.width);
    
        camera.y = Math.max(0, e.y - ((camera.height / 2) - (e.height / 2)));
        camera.y = Math.min(camera.y, (map_height * tileSize) - camera.height);
    }

    public void displayEntity(PanelEntity e, Graphics g) {
        if (e.x >= camera.x - e.width && e.x <= camera.x + camera.width) {
            if (e.y >= camera.y - e.height && e.y <= camera.y + camera.height) {
                e.display(g, camera);
            }
        }
    }

    public void displayTiles(Graphics g) {
        int leftStart = Math.max((camera.x / tileSize) - 1, 0);
        int topStart = Math.max((camera.y / tileSize) - 1, 0);
        int rightEnd = Math.min(((camera.x + camera.width) / tileSize) + 1, map_length);
        int bottomEnd = Math.min(((camera.y + camera.height) / tileSize) + 1, map_height);

        while (topStart < bottomEnd) {
            for (int i = leftStart; i < rightEnd; i++) {
                int tileX = (i * tileSize) - camera.x;
                int tileY = (topStart * tileSize) - camera.y;

                g.drawImage(tiles[topStart][i].image, tileX, tileY, tileSize, tileSize, null);
            }
            topStart++;
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
