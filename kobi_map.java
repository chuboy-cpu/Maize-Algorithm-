import java.util.Scanner;
import java.io.File;

public class kobi_map{
    static int row = 0, col = 0;
    static boolean active = true, stop_moving_right, stop_moving_up = true, stop_moving_down = true;
    static boolean right = false, left = false;
    static boolean up_section = false, down_section = false;
    
    public static void main(String[] args){
        int [] split = new int [2];
        Scanner input = new Scanner(System.in);

        File map = new File ("C:\\PROGRAMMING\\Java\\kobi-map.csv");
    
        try {
            Scanner reader = new Scanner(map);
            
            for (int i = 0; i < 2; ++i){
                split[i] = Integer.parseInt(reader.nextLine().split(",")[0]);
            }
            row = split[0]; 
            col = split[1];

            reader.close();

        } catch (Exception e) {
            System.out.println("what's poppin");
        }

        String [][] map_array = new String [row][col];

        try {
            Scanner reader = new Scanner(map);

            for (int i = 0; i < 2; ++i){ reader.nextLine();}

            for (int count = 0; count < row; ++count ){
                String [] hold = reader.nextLine().split(",");
                for(int count_2 = 0; count_2 < col; ++ count_2){
                    map_array[count][count_2] = hold[count_2];
                }
            }

            reader.close();

        } catch (Exception e) {

        }
    
        System.out.println("Enter Rainfall Severity Level _____ ");
        int rain_severity = input.nextInt();

        fill(map_array, rain_severity, row - 1, col - 1, 'R');
        fill(map_array, rain_severity, row - 1, col - 1, 'L');

        for (int count = 0; count < row; ++count ){
            for(int count_2 = 0; count_2 < col; ++ count_2){
                System.out.print(map_array[count][count_2] + " ");
            }
            System.out.print("\n");
        }

        boolean [][]pstn_checker = new boolean [row][col];

        System.out.println("Enter the Emergency Vehicle y-axis location ::");
        int vehicle_row = input.nextInt();

        System.out.println("Enter the Emergency Vehicle x-axis location ::");
        int vehicle_col = input.nextInt();

        System.out.println("Enter the Emergency Occurance y-axis location ::");
        int occurance_row = input.nextInt();

        System.out.println("Enter the Emergency Occurance x-axis location ::");
        int occurance_col = input.nextInt();

        emergency(map_array, pstn_checker,vehicle_row,vehicle_col,occurance_row,occurance_col);

        if(pstn_checker[vehicle_row][vehicle_col] && pstn_checker[occurance_row][occurance_col]){
            System.out.println("\nThere does exist a land path to the emergency");
        }else{
            System.out.println("\nNo land path to the emergency exists");
        }

        input.close();
    }



    public static void fill(String [][]map, int rsl, int fill_row, int fill_col, char water_body){
        int hold_row = fill_row, hold_col = fill_col, hold_col2 = fill_col; 
        if(water_body == 'L'){
            if(rsl % 2 != 0){
                rsl -= 1;
            }
        }
        
        while(fill_col >= 0){
            hold_col = hold_col2 = fill_col;

            if (map[fill_row][fill_col].charAt(0) == water_body){
                active = true;
                stop_moving_right = true;

                while(active){    

                    //fills up
                    while(hold_row > 0){
                        hold_row -= 1;
                        if((isNumeric(map[hold_row][hold_col])) && (Integer.parseInt(map[hold_row][hold_col]) <= rsl)){
                            map[hold_row][hold_col] = String.valueOf(water_body);
                        }
                    else{ hold_row = 0; }
                    }

                    hold_row = fill_row;

                    // fills down
                    while(hold_row < row - 1){
                        hold_row += 1;
                        if((isNumeric(map[hold_row][hold_col])) && (Integer.parseInt(map[hold_row][hold_col]) <= rsl)){
                            map[hold_row][hold_col] = String.valueOf(water_body);
                        }
                        else{ hold_row = row - 1; }
                    }

                    hold_row = fill_row;

                    //fills right
                    if (hold_col < col - 1 && stop_moving_right){
                        hold_col += 1;
                        if((isNumeric(map[hold_row][hold_col])) && (Integer.parseInt(map[hold_row][hold_col]) <= rsl)){                        
                            map[hold_row][hold_col] = String.valueOf(water_body);
                        }
                        else{ stop_moving_right = false; }
                    }
                    //fills left
                    else if (hold_col2 > 1){
                        hold_col2 -= 1;
                        if((isNumeric(map[hold_row][hold_col2])) && (Integer.parseInt(map[hold_row][hold_col2]) <= rsl)){                        
                            map[hold_row][hold_col2] = String.valueOf(water_body);
                        }
                        else{
                            active = false;
                        }
                    }else{ active = false;}
                }
            }
        
            fill_col -= 1;
        }

        if (fill_row >= 1){
            fill_col = col - 1;
            fill(map, rsl, fill_row - 1, fill_col, water_body);
        }       
    }
 


    public static Boolean isNumeric(String s){

        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) { 

        }
        return false;
    }




    public static void emergency (String [][]map, boolean [][]pstn_checker,
     int vehicle_row, int vehicle_col, int occurance_row, int occurance_col){

        int v_row = vehicle_row, v_col = vehicle_col, v_col2 = vehicle_col;
        active = true;
        stop_moving_right = true;

        while(active){    

            //fills up
            while(v_row > 0){
                v_row -= 1;
                if((isNumeric(map[v_row][v_col]))){
                    pstn_checker[v_row][v_col] = true;
                }
                else{ v_row = 0; }
            }

            v_row = vehicle_row;

            // fills down
            while(v_row < row - 1){
                v_row += 1;
                if((isNumeric(map[v_row][v_col]))){
                    pstn_checker[v_row][v_col] = true;
                }
                else{ v_row = row - 1; }
            }

            v_row = vehicle_row;

            //fills right
            if (v_col < col-1 && stop_moving_right){
                if((isNumeric(map[v_row][v_col]))){                        
                    pstn_checker[v_row][v_col] = true;
                }
                else{ stop_moving_right = false; }

                v_col += 1;
            }
            //fills left
            else if (v_col2 > 0){
                v_col2 -= 1;
                if((isNumeric(map[v_row][v_col2]))){                        
                    pstn_checker[v_row][v_col2] = true;
                }
                else{
                    active = false;
                }
            }else{ active = false;}
        }
        

        if(vehicle_row >= occurance_row && stop_moving_up){
            up_section = true;
            stop_moving_down = false;
        }
        else if (vehicle_row <= occurance_row && stop_moving_down){
            down_section = true;
            stop_moving_up = false;
        }

        if(vehicle_col > occurance_col){
            left = true;
        }else{
            right = true;
        }


        if(up_section && vehicle_row > 0){
            active = true;
            while(active){
                if(isNumeric(map[vehicle_row - 1][vehicle_col])){
                    vehicle_row -= 1;
                    active = false;
                }else{
                    if(right){
                        if(vehicle_col < col - 1){
                            vehicle_col += 1;
                        }else{ up_section = false; active = false;}
                    }
                    else if(left){ 
                        if(vehicle_col > 0){
                            vehicle_col -= 1;
                        }else{ up_section = false; active = false;}
                    }
                }
            }
            
        }
        else if(down_section && vehicle_row < row - 1){
            active = true;
            while(active){
                if(isNumeric(map[vehicle_row + 1][vehicle_col])){
                    vehicle_row += 1;
                    active = false;
                }else{
                    if(right){
                        if(vehicle_col < col - 1){
                            vehicle_col += 1;
                        }else{ down_section = false; active = false;}
                    }
                    else if(left){ 
                        if(vehicle_col > 0){
                            vehicle_col -= 1;
                        }else{ down_section = false; active = false;}
                    }

                }
            }
           
        }else{ up_section = false; down_section = false;}
        

        if(up_section || down_section){
            emergency(map, pstn_checker, vehicle_row, vehicle_col, occurance_row, occurance_col);
        }

    }

}