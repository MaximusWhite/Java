

/**
 * Created by mikef on 27-Oct-2016.
 *
 * Sample class containing the data for 500 entries chunks in form of a table
 */
public class Sample {

    double[][] table = new double[500][5];

    // recieve a line from the sample file and extract data

    public void addEntry(String entry, int count) {

        String[] values = entry.split("[\t]+", 5);

        for (int i = 0; i < 5; i++) {   // for each column (Key1 , Key2, Fly time, Dwell 1, Dwell 2)

            table[count][i] = Integer.parseInt(values[i]);   // store valuse in table

        }
    }
}


