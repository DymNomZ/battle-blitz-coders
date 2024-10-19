package src;
import classes.entities.CameraEntity;
import classes.entities.Dummy;
import classes.entities.PanelEntity;
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
                        if(td.tile_type == map_indexes[i][j]){
                            tiles[i][j] = td;
                            break;
                        }
                    }
                }else{
                    tiles[i][j] = new Tile(
                        "../../assets/map_tiles/void.png", "void",
                        0, false
                    );
                }
                    
            }
        }
    }

    // Will not support speed that is == to tileSize or higher
    public void verifyEntityPosition(PanelEntity e){
        
        /* "Quick" summary of this bulshet below
         * 
         * Collisions are check sequentially... x dimension first and then y dimension
         * As trying to move an entity diagonally will have a high chance of having this entity
         * unable to check tiles in corners
         * 
         * This function will also check for map border collisions... If u want to move an entity
         * without the restrictions of the collisions, use the PanelEntity.moveAbsolute or change
         * the x and y coordinates directly without setting the deltaX and deltaY variables.
         * Collision will only verify those in deltaX and deltaY
         * 
         * Two major variables are present in both dimensions namely: pixelsOnTile and offTile
         * 
         * pixelsOnTile: represents the number of pixels of the entity's current position to the
         *               base point (0, 0) position of the current tile. This can also be known
         *               as the offset of the entity from current's tile top left corner
         *          
         *               Example each tile of the map have 32x32 dimension, and the position of the entity
         *               in relation to the pixels is (64, 33). The value for pixelsOnTile on both
         *               dimensions is (0, 1).
         * 
         * offTile:      This is the main factor of detecting whether we should check the next tile or not
         *               This is the sum of pixelsOnTile and the speed/movement of an entity. Why you ask?
         *               If the value of this variable a negative value, this represents that we must check
         *               the tile before it. If the value is greater than the tile width, then we must check
         *               the tile after entity or the current tile. Any value that does not apply to the
         *               conditions above are ignored as they are expected to not collide a new tile.
         *               However, if the position of the entity is sitting at the base point (0, 0) of the
         *               current tile and this variable is greater than 0, we must still check the tile next
         *               of the current tile.
         * 
         * 
         * Since the tiles we used are placed in a 2D array, variables offXTile and offYTile are utitlize to
         * get the index of the tile to check. In any possibilty that an Entity is moving between two tiles,
         * we must check two tiles opposite to the current dimension we are calculating. If the first tile
         * is a solid, there is no need to check for the second tile since it is already given the entity
         * must not move there.
         * "two tiles opposite to the current diemension" means that if we are curently checking for x dimension,
         * we must check for tile[offYTile][offXTile] and tile[offYTile + 1][offXTile]. To check whether we have
         * to check two tiles in this situation, y % tileSize > 0 will tell us just that.. also opposite to the
         * current dimension
         * 
         */


        if (e.deltaX != 0) {
            int pixelsOnTile = e.x % tileSize;
            int offTile = pixelsOnTile + e.deltaX;

            if (e.x + e.deltaX < 0) {
                e.x = 0;
            } else if (e.x + e.width + e.deltaX >= (map_length * tileSize)) {
                e.x = (map_length * tileSize) - e.width;
            } else if (pixelsOnTile != 0 && offTile >= 0 && offTile <= tileSize) {
                e.x += e.deltaX;
            } else {
                int offXTile;

                if (offTile > tileSize || (pixelsOnTile == 0 && offTile > 0)) {
                    offXTile = (e.x + e.deltaX + e.width) / tileSize;
                } else {
                    offXTile = (e.x + e.deltaX) / tileSize;
                }

                int offYTile = e.y / tileSize;
                e.x += e.deltaX;

                if (tiles[offYTile][offXTile].is_solid) {
                    if (offTile < 0) {
                        e.x -= offTile;
                    } else {
                        e.x -= pixelsOnTile != 0 ? offTile - tileSize : e.deltaX;
                    }
                } else if (e.y % tileSize > 0 && (tiles[offYTile + 1][offXTile].is_solid)) {
                    if (offTile < 0) {
                        e.x -= offTile;
                    } else {
                        e.x -= pixelsOnTile != 0 ? offTile - tileSize : e.deltaX;
                    }
                }
            }

            e.deltaX = 0;
        }

        if (e.deltaY != 0) {
            int pixelsOnTile = e.y % tileSize;
            int offTile = pixelsOnTile + e.deltaY;

            if (e.y + e.deltaY < 0) {
                e.y = 0;
            } else if (e.y + e.height + e.deltaY >= (map_height * tileSize)) {
                e.y = (map_height * tileSize) - e.height;
            } else if (pixelsOnTile != 0 && offTile >= 0 && offTile <= tileSize) {
                e.y += e.deltaY;
            } else {
                int offYTile;

                if (offTile > tileSize || (pixelsOnTile == 0 && offTile > 0)) {
                    offYTile = (e.y + e.deltaY + e.height) / tileSize;
                } else {
                    offYTile = (e.y + e.deltaY) / tileSize;
                }

                int offXTile = e.x / tileSize;
                e.y += e.deltaY;

                if (tiles[offYTile][offXTile].is_solid) {
                    if (offTile < 0) {
                        e.y -= offTile;
                    } else {
                        e.y -= pixelsOnTile != 0 ? offTile - tileSize : e.deltaY;
                    }
                } else if (e.x % tileSize > 0 && (tiles[offYTile][offXTile + 1].is_solid)) {
                    if (offTile < 0) {
                        e.y -= offTile;
                    } else {
                        e.y -= pixelsOnTile != 0 ? offTile - tileSize : e.deltaY;
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

    @Deprecated
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
