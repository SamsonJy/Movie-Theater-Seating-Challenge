package src;

import java.util.ArrayList;

public class TestTheaterSeating {
    TheaterSeating testTheater;

    public void testMe(TheaterSeating movieTheater) {
        System.out.println("___Test___");
        testTheater = movieTheater;
        // test zero ticket: boundary condition
        checkZeroTickets();
        // when the amount of need tickets exceed the available seats
        checkInsufficientSeats();
        // the first time when the tickets smaller than a row capacity
        checkFirstCustomerSeat();
        // check whether the seats are continuous
        checkContinuance();

        /* when the few seats in the middle of theater has been occupied, and there are not enough seat whether it will
            go to next line
        * */
        checkSecondStart();
        // check when exceed one row capacity
        checkExceedRowCapacity();
    }

    private void checkZeroTickets(){
        if(testTheater.bookSeat("R001 0").equals( "null")){
            System.out.println("Test 1 Passed : No seat booked for R001.");
        }else{
            System.out.println("Test 1 Failed :  R001 with 0 tickets need booked seats .");
        }
    }
    public void checkInsufficientSeats() {
        if(testTheater.bookSeat("R003 210").equals( "Exceed capacity")) {
            System.out.println("Test 2 Passed : Exceed available seat.");
        } else {
            System.out.println(" Test 2 Failed : Allocated as many seats as possible.");
        }
    }
    private void checkFirstCustomerSeat() {
        // testTheater.printLayout();
        testTheater.bookSeat("R002 3");
        ArrayList<String> list = new ArrayList<>();
        list.add("E1");
        list.add("E2");
        list.add("E3");
        if (testTheater.occupied.get("R002").equals(list)) {
            System.out.println("Test 3 Passed : Successfully booked two consecutive seats in the middle.");
        } else {
            System.out.println("Test 3 Failed: Failed to reserve seats at the middle row.");
        }
    }
    private void checkContinuance(){
        if(testTheater.seats[4][0] .equals( "R002") &&testTheater.seats[4][1] .equals( "R002")&&testTheater.seats[4][2] .equals( "R002" )){
            System.out.println("Test 4 Passed : Successfully booked two consecutive seats in the middle.");
        }else{
            System.out.println("Test 4 Failed: Failed to reserve seats at the middle row.");
        }
    }


    public void checkSecondStart() {
        testTheater.bookSeat("R003 19" );
        for(int i = 18; i >= 0; i--){
            if(testTheater.seats[5][i] == null){
                System.out.println("Test 5 Failed : not successfully occupied the next row.");
                return;
            }
        }
        System.out.println("Test 5 Passed : Successfully occupied the next row.");
    }

    public void checkExceedRowCapacity() {
        testTheater.bookSeat("R004 24");
        ArrayList<String> cur = testTheater.occupied.get("R004");
        if (cur.size() > 0) {
            System.out.println("Test 6 Passed : Successfully allocated seats which exceed one row capacity.");
        } else {
            System.out.println("Test 6 Failed : Failed to allocate seats which exceed one row capacity.");
        }
    }

}
