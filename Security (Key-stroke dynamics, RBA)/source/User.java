

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 * Created by mikef on 27-Oct-2016.
 *
 * User class containing the monitored Sample and 5 Reference Samples
 * as well as user's name (id)
 *
 * In the scope of this lab user name can only belong to the set {User1,User2,User3,User4,User5}
 *
 */
public class User {

    String name;
    Sample monitored = new Sample();
    Sample target[] = new Sample[5];

    /**
     * Instantiates the user
     *
     * @param name - the user name(id)
     * @throws IOException
     */
    public User(String name) throws IOException {

        this.name = name;

        BufferedReader br = new BufferedReader(new FileReader(new File(name+".txt")));  // open file to read

        for(int i =0; i<5; i++){        // instantiate target samples to empty samples
            target[i] = new Sample();
        }

        readSample(br);   // read samples corresponding to current user

        br.close();

    }

    /**
     * This function reads monitored sample set and reference sample sets corresponding to current user
     *
     * @param br - BufferReader that opened the file to read
     * @throws IOException
     */
    private void readSample(BufferedReader br) throws IOException {

        int i;
        String tmp;

        //first 500 entries go to monitored sample

        for (i = 0; i < 500; i++) {

            if ((tmp = br.readLine()) != null) {

                if (i == 0) continue;   // skip table headers

                monitored.addEntry(tmp, i);

            }

        }

        // the rest of entries goes into 5 separate target samples

        for (int j = 0; j < 5; j++) {

            for (i = 0; i < 500; i++) {

                if ((tmp = br.readLine()) != null) {

                    target[j].addEntry(tmp, i);

                }

            }

        }


    }
}
