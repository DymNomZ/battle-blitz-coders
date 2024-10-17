package src;

import classes.map.Tile;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;

public class MapLoader {

    ArrayList<Tile> loaded_tile_data;
    int[][] loaded_map_indexes, tile_data_indexes;
    int curr_idx;
    
    public MapLoader(){

        loaded_tile_data = new ArrayList<>();
        curr_idx = 0;
    }

    public ArrayList<Tile> getTiles(){
        return loaded_tile_data;
    }

    public int[][] getMapIndexes(){
        return loaded_map_indexes;
    }

    public void loadMapData(String path){

        ZipFile zip_file;
        try {

            zip_file = new ZipFile(path);

            // Get an enumeration of the entries in the zip file
            Enumeration<? extends ZipEntry> entries = zip_file.entries();

            // Iterate over the entries and print their names
            //System.out.println("Zip contents: ");
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                //System.out.println(entry.getName());

                //read operations:
                if(entry.getName().endsWith("data.txt")){
                    read_tile_data(zip_file, entry);
                }
                if(entry.getName().endsWith("$.txt")){
                    read_map(zip_file, entry);
                }
            }

            //load for pngs
            entries = zip_file.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                if(entry.getName().endsWith(".png")){
                    read_images(zip_file, entry);
                }
            }

            //reset traversing index
            curr_idx = 0;

            //Check print
            System.out.println(loaded_tile_data.size());
            for(Tile t : loaded_tile_data){
                System.out.println(t.name + " " + t.index);
            }

            zip_file.close();

            } catch (IOException ex) {
            }
    }

    public void read_images(ZipFile zip, ZipEntry image){

        InputStream image_data_stream;
        BufferedImage tile_image;
        String tile_name = image.getName().substring(0, image.getName().lastIndexOf('.'));

        try {
            image_data_stream = zip.getInputStream(image);
            tile_image = ImageIO.read(image_data_stream);
            image_data_stream.close();

            loaded_tile_data.add(
                new Tile(
                    tile_data_indexes[curr_idx][0], 
                    tile_name, tile_image
                )
            );

            curr_idx++;

        } catch (IOException ex) {
        }

    }

    public void read_tile_data(ZipFile zip, ZipEntry tile_data){
        
        InputStream tile_data_stream;
        BufferedReader reader;
        try {
            tile_data_stream = zip.getInputStream(tile_data);
            reader = new BufferedReader(new InputStreamReader(tile_data_stream));

            int td_h = 0;
            int td_l = 1; //constant 1 cause only indexes for now, no solid data and others

            while ((reader.readLine()) != null){
                td_h++;
            }

            reader.close();

            tile_data_stream = zip.getInputStream(tile_data);
            reader = new BufferedReader(new InputStreamReader(tile_data_stream));

            tile_data_indexes = new int[td_h][td_l];

            for(int i = 0; i < td_h; i++){

                String[] raw_indexes = reader.readLine().split(" ");

                for(int j = 0; j < td_l; j++) {
                    tile_data_indexes[i][j] = Integer.parseInt(raw_indexes[j]);
                }

            }

            reader.close();
            tile_data_stream.close();

        } catch (IOException ex) {
        }
    }

    public void read_map(ZipFile zip, ZipEntry map){

        InputStream map_data_stream;
        BufferedReader reader;
        try {
            map_data_stream = zip.getInputStream(map);
            reader = new BufferedReader(new InputStreamReader(map_data_stream));

            String line = reader.readLine();
            int map_h = 0;
            int map_l = line.length() / 2; //because of spaces

            do{
                map_h++;
            }while ((reader.readLine()) != null);

            reader.close();

            map_data_stream = zip.getInputStream(map);
            reader = new BufferedReader(new InputStreamReader(map_data_stream));

            loaded_map_indexes = new int[map_h][map_l];

            for(int i = 0; i < map_h; i++){

                String[] raw_indexes = reader.readLine().split(" ");

                for(int j = 0; j < map_l; j++) {
                    loaded_map_indexes[i][j] = Integer.parseInt(raw_indexes[j]);
                }

            }

            reader.close();
            map_data_stream.close();

        } catch (IOException ex) {
        }
        
    }
}
