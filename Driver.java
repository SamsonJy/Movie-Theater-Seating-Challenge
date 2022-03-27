package src;

import java.io.*;


public class Driver {
    public static void main(String[] args) {
        if (args.length > 0) {
            FileProcessor fileProcessor = new FileProcessor(args[0]);
            TheaterSeating movieTheater = new TheaterSeating();

            try {
                File file = new File(args[0]);
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                /* Read and process the file */
                String newEntry = bufferedReader.readLine();

                while (newEntry != null) {
                    String output = movieTheater.bookSeat(newEntry);
                    if (output .equals( "null")) {
                        System.out.println("Invalid number of Seats");
                    }
                    if (output .equals( "Exceed capacity")) {
                        System.out.println("Can't process because of Insufficient seats");
                    }

                    newEntry = bufferedReader.readLine();
                }
                /* Writing to File */
                fileProcessor.writeToFile(movieTheater.occupied);

                /* Print Layout of the theater */
                System.out.println("_____Booking Information___");
                    for (int r = 0; r < 10; r++) {
                        System.out.print((char) (r + 65) + " ");
                        for (int c = 0; c < 20; c++) {
                            System.out.print(" " + movieTheater.seats[r][c]);
                        }
                        System.out.println();
                    }
                /* Calling the Test method */
                TestTheaterSeating test = new TestTheaterSeating();
                TheaterSeating testObject = new TheaterSeating();
                test.testMe(testObject);

            } catch (FileNotFoundException ex) {
                System.out.println("Input file not Found.");
                ex.printStackTrace();
                System.exit(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
