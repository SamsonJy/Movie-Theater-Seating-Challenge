package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TheaterSeating {
    // rows and columns
    int rows = 10;
    int cols = 20;
    // creat a string board which is used to act as seats in theater;
    String[][] seats = new String[rows][cols];
    //the map<Identifier, the occupied seats number for identifier>
    HashMap<String, ArrayList<String>> occupied = new HashMap<>();
    // remaining seat in every row
    int[] remainRowSeat = {20, 20, 20, 20, 20, 20, 20, 20, 20, 20};
    //total available seat
    int totalAvailableSeat = rows * cols;
    // book seats method
     public String bookSeat(String bookingInformation){
        /*
            split bookingInformation into two part:
            1. row
            2. seat amounts
        */
        String[] bookingInfo = bookingInformation.split(" ");
        // the Identifier ID which will be used in result;
        String  identifier = bookingInfo[0];
        // amounts of booking seats, convert string to int
        Integer seatAmount = Integer.parseInt(bookingInfo[1]);
        // when order  0 seat: Promp no seat booking or check the booking for identifier
        if(seatAmount <= 0){
            return "null";
        }
        // booking seats amounts can't exceed the current capacity of whole theater
        if(seatAmount <= totalAvailableSeat ){
            // when seatAmount exceed the available seats in this row, /
             /* when seatAmount exceed the available seats in this row,
              we try to first full the row
             */
            if(seatAmount > 20){
                while (seatAmount > 20){
                    occupySeat(identifier, 20);
                    seatAmount -= 20;
                }
                occupySeat(identifier, seatAmount);
            }else {
                occupySeat(identifier, seatAmount);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(identifier );
            sb.append(" ");
            List<String> cur = occupied.get(identifier);
            int size = cur.size();
            for(int i = 0; i < size - 1; i++){
                sb.append(cur.get(i));
                sb.append(",");
            }
            sb.append(cur.get(size - 1));
            return new String(sb);

        }else{
            return "Exceed capacity";
        }
    }
    private  void occupySeat(String identifier, int seatAmount){
        // first time recommend the middle seats in whole theater(which may be the best position);
        int row = (rows/2) - 1;
        /*
            check is used to check rows nearby: we want to keep the audiences in the middle of theater to
            improve audience satisfaction
        */
        String check = "BackRows";
        /*
            checkStep is used to check the row when this row has been visited
            when check == "BackRows", row + checkStep;
            when check == "frontRows" row - checkStep
         */
        int checkStep = 1;
        /*
         * traverse the whole theater to find enough continues seats
         * */
        while(row >= 0 && row < rows && seatAmount > 0){
            // if there are enough seats in this row, we only occupy this row
            if(remainRowSeat[row] >= seatAmount){
                // traverse the whole row to find the empty seat
                for(int column = 0; column < 20 && seatAmount > 0 ; column ++){
                    // if seat is empty, use identifier ID to occupy it.
                    seats[row][column] = identifier;
                    /*
                    Following is the number of a seat: row information + column information (examples: E8, H10)
                    row information: A's ASCII is 65, so the row information is row + 65
                    column information: Because the index start with 0, while the column in theater start with 1, so we need use
                    column + 1 as column information
                    */
                    String seatNum = (char)(row + 65) + Integer.toString(column + 1);
                    //if we used same identifier before, we just add it to existed array
                    if(occupied.containsKey(identifier)){
                        occupied.get(identifier).add(seatNum);
                    }else{
                        //if we never use this identifier before we need create a new list to help record seat number
                        ArrayList<String> numsForIdentifier = new ArrayList<>();
                        //add seat number to this identifier
                        numsForIdentifier.add(seatNum);
                        // put new identifier and identifier list to occupied map
                        occupied.put(identifier,numsForIdentifier);
                    }
                    //occupied this one seat of this row means available seats in this row will be reduced;
                    remainRowSeat[row]--;
                    // total available seats minus
                    totalAvailableSeat--;
                    // the seat customer needs to book minus 1
                    seatAmount--;
                }
            }if(check .equals( "BackRows" )){
                row += checkStep;
                checkStep++;
                check = "FrontRows";
            }else if(check.equals( "FrontRows")){
                row -= checkStep;
                checkStep++;
                check = "BackRows";
            }
        }
        // when seatAmount == 0 means there is no seats need to be booked, return
        if(seatAmount == 0) {
            return;
        } else{
            /* No row meets the requirement for everyone to sit in the same row;
             *   Try to seat everyone near the middle, book the rest seats in the middle
             */
            //start from the middle row again
            row = (rows/2) - 1;
            check ="BackRows";
            checkStep = 1;
            while(row >= 0 && row < rows ){
                if(remainRowSeat[row] > 0){
                    // start from the end of the row, because we start booking at the front of row

                    for(int column = 19;column >= 0 && seats[row][column] == null &&seatAmount > 0  ; column--){
                        seats[row][column] = identifier;
                        String seatNum = (char)(row + 65) + Integer.toString(column + 1);
                        //if we used same identifier before, we just add it to existed array
                        if(occupied.containsKey(identifier)){
                            occupied.get(identifier).add(seatNum);
                        }else{
                            //if we never use this identifier before we need create a new list to help record seat number
                            ArrayList<String> numsForIdentifier = new ArrayList<>();
                            //add seat number to this identifier
                            numsForIdentifier.add(seatNum);
                            // put new identifier and identifier list to occupied map
                            occupied.put(identifier,numsForIdentifier);
                        }
                        //occupied this one seat of this row means available seats in this row will be reduced;
                        remainRowSeat[row]--;
                        // total available seats minus
                        totalAvailableSeat--;
                        // the seat customer needs to book minus 1
                        seatAmount--;
                    }
                }if(check.equals( "BackRows" )){
                    row += checkStep;
                    checkStep++;
                    check = "FrontRows";
                }else if(check.equals( "FrontRows")){
                    row -= checkStep;
                    checkStep++;
                    check = "BackRows";
                }
            }
        }
    }

}
