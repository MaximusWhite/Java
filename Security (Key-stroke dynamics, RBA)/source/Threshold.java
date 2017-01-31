

/**
 * Created by mikef on 30-Oct-2016.
 *
 * Contains thresholds corresponding to different users.
 * Thresholds calculated through trial and error method to achieve FAR=FRR
 */
public class Threshold {

    double frr_thresh;
    double far_thresh;

    public Threshold(String name){

        switch(name){

            case "User1":
                frr_thresh = 80.0;
                far_thresh = 68.0;
                break;
            case "User2":
                frr_thresh = 67.0;
                far_thresh = 78.0;
                break;
            case "User3":
                frr_thresh = 76.0;
                far_thresh = 66.0;
                break;
            case "User4":
                frr_thresh = 80.0;
                far_thresh = 67.0;
                break;
            case "User5":
                frr_thresh = 84.0;
                far_thresh = 70.0;
                break;

        }

    }

}
